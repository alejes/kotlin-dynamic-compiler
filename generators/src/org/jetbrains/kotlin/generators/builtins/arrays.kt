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

package org.jetbrains.kotlin.generators.builtins.arrays

import org.jetbrains.kotlin.generators.builtins.PrimitiveType
import org.jetbrains.kotlin.generators.builtins.generateBuiltIns.BuiltInsSourceGenerator
import java.io.PrintWriter

class GenerateArrays(out: PrintWriter, private val sources: Boolean = false) : BuiltInsSourceGenerator(out) {
    override fun getPackage() = super.getPackage() + if (sources) ".builtins" else ""

    override fun generateBody() {
        for (kind in PrimitiveType.values()) {
            val typeLower = kind.name.toLowerCase()
            val s = kind.capitalized
            val defaultValue = if (kind == PrimitiveType.BOOLEAN) "false" else "zero"
            out.println("/**")
            out.println(" * An array of ${typeLower}s. When targeting the JVM, instances of this class are represented as `$typeLower[]`.")
            out.println(" * @constructor Creates a new array of the specified [size], with all elements initialized to $defaultValue.")
            out.println(" */")
            if (sources) {
                out.println("public class ${s}ArrayBuiltins {")
                out.println("    companion object {")
            }
            else {
                out.println("public class ${s}Array(size: Int) {")
            }

            if (!sources) {
                out.println("    /**")
                out.println("     * Creates a new array of the specified [size], where each element is calculated by calling the specified")
                out.println("     * [init] function. The [init] function returns an array element given its index.")
                out.println("     */")
                out.println("    public inline constructor(size: Int, init: (Int) -> $s)")
                out.println()
            }

            out.println("    /** Returns the array element at the given [index]. This method can be called using the index operator. */")
            if (sources) {
                out.println("    @JvmStatic")
                out.println("    public fun get(thisValue: ${s}Array, index: Int): $s")
                out.println("        = thisValue.get(index)")
            }
            else {
                out.println("    public operator fun get(index: Int): $s")
            }
            out.println("    /** Sets the element at the given [index] to the given [value]. This method can be called using the index operator. */")
            if (sources){
                out.println("    @JvmStatic")
                out.println("    public fun set(thisValue: ${s}Array, index: Int, value: $s): Unit")
                out.println("        = thisValue.set(index, value)")
            }
            else {
                out.println("    public operator fun set(index: Int, value: $s): Unit")
            }
            out.println()
            out.println("    /** Returns the number of elements in the array. */")
            if (sources) {
                out.println("    @JvmStatic")
                out.println("    public fun getSize(thisValue: ${s}Array): Int")
                out.println("        = thisValue.size")
            }
            else {
                out.println("    public val size: Int")
            }
            out.println()
            out.println("    /** Creates an iterator over the elements of the array. */")
            if (sources) {
                out.println("    @JvmStatic")
                out.println("    public fun iterator(thisValue: ${s}Array): ${s}Iterator")
                out.println("        = thisValue.iterator()")
            }
            else {
                out.println("    public operator fun iterator(): ${s}Iterator")
            }
            if (sources) {
                out.println("}")
            }
            out.println("}")
            out.println()
        }
    }
}
