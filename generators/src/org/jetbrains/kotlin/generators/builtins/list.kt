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

package org.jetbrains.kotlin.generators.builtins

import org.jetbrains.kotlin.generators.builtins.generateBuiltIns.BuiltInsSourceGenerator
import java.io.PrintWriter


class GenerateList(out: PrintWriter, private val sources: Boolean = false) : BuiltInsSourceGenerator(out) {
    override fun getPackage() = super.getPackage() + if (sources) ".builtins" else ""
    override fun generateBody() {
        if (sources) {
            out.println("""
class ListBuiltins {
    companion object {
        @JvmStatic
        public fun <E> getSize(thisValue: List<E>): Int
                = thisValue.size

        @JvmStatic
        public fun <E> get(thisValue: List<E>, index: Int): E
                = thisValue.get(index)

        @JvmStatic
        public fun <E> isEmpty(thisValue: List<E>): Boolean
                = thisValue.isEmpty()

        @JvmStatic
        public fun <E> contains(thisValue: List<E>, element: @UnsafeVariance E): Boolean
                = thisValue.contains(element)

        @JvmStatic
        public fun <E> iterator(thisValue: List<E>): Iterator<E>
                = thisValue.iterator()

        @JvmStatic
        public fun <E> containsAll(thisValue: List<E>, elements: Collection<@UnsafeVariance E>): Boolean
                = thisValue.containsAll(elements)

        @JvmStatic
        public fun <E> indexOf(thisValue: List<E>, element: @UnsafeVariance E): Int
                = thisValue.indexOf(element)

        @JvmStatic
        public fun <E> lastIndexOf(thisValue: List<E>, element: @UnsafeVariance E): Int
                = thisValue.lastIndexOf(element)

        @JvmStatic
        public fun <E> listIterator(thisValue: List<E>): ListIterator<E>
                = thisValue.listIterator()

        @JvmStatic
        public fun <E> listIterator(thisValue: List<E>, index: Int): ListIterator<E>
                = thisValue.listIterator(index)

        @JvmStatic
        public fun <E> subList(thisValue: List<E>, fromIndex: Int, toIndex: Int): List<E>
                = thisValue.subList(fromIndex, toIndex)
    }
}
""")
        }
    }
}
