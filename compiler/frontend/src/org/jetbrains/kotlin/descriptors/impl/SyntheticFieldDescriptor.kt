/*
 * Copyright 2010-2015 JetBrains s.r.o.
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

package org.jetbrains.kotlin.descriptors.impl

import org.jetbrains.kotlin.descriptors.DeclarationDescriptor
import org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor
import org.jetbrains.kotlin.descriptors.PropertyDescriptor
import org.jetbrains.kotlin.descriptors.annotations.Annotations
import org.jetbrains.kotlin.name.Name
import org.jetbrains.kotlin.psi.KtProperty
import org.jetbrains.kotlin.resolve.source.toSourceElement

class SyntheticFieldDescriptor private constructor(
        val propertyDescriptor: PropertyDescriptor,
        accessorDescriptor: PropertyAccessorDescriptor,
        property: KtProperty
): LocalVariableDescriptor(accessorDescriptor, Annotations.EMPTY, SyntheticFieldDescriptor.NAME,
                           propertyDescriptor.type, propertyDescriptor.isVar, false, property.toSourceElement()) {

    constructor(accessorDescriptor: PropertyAccessorDescriptor,
                property: KtProperty): this(accessorDescriptor.correspondingProperty, accessorDescriptor, property)

    override fun getDispatchReceiverParameter() = null

    fun getDispatchReceiverForBackend() = getDispatchReceiverParameterForBackend()?.value

    fun getDispatchReceiverParameterForBackend() = propertyDescriptor.dispatchReceiverParameter

    override fun isDynamic(): Boolean = propertyDescriptor.isDynamic

    companion object {
        @JvmField
        val NAME = Name.identifier("field")
    }
}

val DeclarationDescriptor.referencedProperty: PropertyDescriptor?
    get() = if (this is SyntheticFieldDescriptor) this.propertyDescriptor else if (this is PropertyDescriptor) this else null
