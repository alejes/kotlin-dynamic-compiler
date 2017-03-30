// TODO: muted automatically, investigate should it be ran for JS or not
// IGNORE_BACKEND: JS
// JVM_TARGET: 1.8

class A {
    val mof = { -> "FAIL" }
    fun mof() = "OK"
}

fun box(): String {
    val a: dynamic = A()
    return a.mof()
}