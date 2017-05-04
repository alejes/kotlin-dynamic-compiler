// TODO: muted automatically, investigate should it be ran for JS or not
// IGNORE_BACKEND: JS
// JVM_TARGET: 1.8
// WITH_RUNTIME

class A {
    fun method1() {}
}

fun box(): String {
    val a: dynamic = A()
    return a.method1().toString().replace("kotlin.Unit", "OK")
}