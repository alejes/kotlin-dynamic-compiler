/*
 * Copyright 2010-2017 JetBrains s.r.o.
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

package org.jetbrains.kotlin.generators.builtins.generateBuiltIns

import org.jetbrains.kotlin.generators.builtins.arrayIterators.GenerateArrayIterators
import org.jetbrains.kotlin.generators.builtins.arrays.GenerateArrays
import org.jetbrains.kotlin.generators.builtins.functions.GenerateFunctions
import org.jetbrains.kotlin.generators.builtins.iterators.GenerateIterators
import org.jetbrains.kotlin.generators.builtins.progressionIterators.GenerateProgressionIterators
import org.jetbrains.kotlin.generators.builtins.progressions.GenerateProgressions
import org.jetbrains.kotlin.generators.builtins.ranges.GeneratePrimitives
import org.jetbrains.kotlin.generators.builtins.ranges.GenerateRanges
import java.io.File
import java.io.PrintWriter



fun generateRuntimeBuiltIns(generate: (File, (PrintWriter) -> BuiltInsSourceGenerator) -> Unit) {
    assertExists(BUILT_INS_NATIVE_DIR)
    assertExists(BUILT_INS_SRC_DIR)
    assertExists(RUNTIME_JVM_DIR)

    generate(File(RUNTIME_JVM_DIR, "kotlin/builtins/Primitives.kt")) { GeneratePrimitives(it, sources = true) }
}

fun main(args: Array<String>) {
    generateRuntimeBuiltIns { file, generator ->
        println("generating $file")
        file.parentFile?.mkdirs()
        PrintWriter(file).use {
            generator(it).generate()
        }
    }
}
