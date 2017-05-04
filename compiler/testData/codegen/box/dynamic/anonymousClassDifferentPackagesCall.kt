// TODO: muted automatically, investigate should it be ran for JS or not
// IGNORE_BACKEND: JS
// JVM_TARGET: 1.8

//FILE:file1.kt

package a;

interface MyInterface {
    fun method1(): String
}

//FILE:file3.kt
package b;

fun interfaceRunner(instance: dynamic) = instance.method1()

//FILE:file2.kt

import a.*
import b.*

fun box(): String {
    val x: dynamic = object : MyInterface {
        override fun method1() = "OK"
    }
    return interfaceRunner(x)
}