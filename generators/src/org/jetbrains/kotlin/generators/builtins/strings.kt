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
import kotlin.test.assertTrue

class GenerateStrings(out: PrintWriter, private val sources: Boolean = false) : BuiltInsSourceGenerator(out) {
    override fun getPackage() = "kotlin.builtins"
    override fun generateBody() {
            assertTrue { sources }

            out.println("public class StringBuiltins internal constructor() {")
            out.println("    companion object {")

            out.println("        @JvmStatic")
            out.println("        public fun plus(_this: String, other: Any?): String")
            out.println("            = _this.plus(other)")
            out.println()

            out.println("        @JvmStatic")
            out.println("        public fun getLength(_this: String): Int")
            out.println("            = _this.length")
            out.println()

            out.println("        @JvmStatic")
            out.println("        public fun get(_this: String, index: Int): Char")
            out.println("            = _this.get(index)")
            out.println()

            out.println("        @JvmStatic")
            out.println("        public fun subSequence(_this: String, startIndex: Int, endIndex: Int): CharSequence")
            out.println("            = _this.subSequence(startIndex, endIndex)")
            out.println()

            out.println("        @JvmStatic")
            out.println("        public fun compareTo(_this: String, other: String): Int")
            out.println("            = _this.compareTo(other)")
            out.println()

            out.println("    }")
            out.println("}")
    }
}





