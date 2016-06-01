/*
 * Copyright 2010-2016 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jetbrains.kotlin.script

import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import kotlin.concurrent.read
import kotlin.concurrent.write

class KotlinScriptDefinitionProvider {

    private val definitions: MutableList<KotlinScriptDefinition> = arrayListOf(StandardScriptDefinition)

    private val definitionsLock = java.util.concurrent.locks.ReentrantReadWriteLock()

    var scriptDefinitions: List<KotlinScriptDefinition>
        get() = definitionsLock.read { definitions } // TODO: remove as unsafe with locking, replace with particular data extractors
        set(definitions: List<KotlinScriptDefinition>) {
            definitionsLock.write {
                this.definitions.clear()
                this.definitions.addAll(definitions)
            }
        }

    fun findScriptDefinition(file: VirtualFile?): KotlinScriptDefinition? = definitionsLock.read {
        file?.let { file -> definitions.firstOrNull { it.isScript(file) } }
    }

    fun isScript(file: VirtualFile?): Boolean = findScriptDefinition(file) != null

    fun addScriptDefinition(scriptDefinition: KotlinScriptDefinition) {
        definitionsLock.write {
            definitions.add(0, scriptDefinition)
        }
    }

    fun removeScriptDefinition(scriptDefinition: KotlinScriptDefinition) {
        definitions.remove(scriptDefinition)
    }

    companion object {
        @JvmStatic
        fun getInstance(project: Project): KotlinScriptDefinitionProvider =
                ServiceManager.getService(project, KotlinScriptDefinitionProvider::class.java)
    }
}