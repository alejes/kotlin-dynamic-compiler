// TODO: muted automatically, investigate should it be ran for JS or not
// IGNORE_BACKEND: JS
// JVM_TARGET: 1.8

class A {
    fun method(x: Int?, y: String?) = y ?: "OK"
}

fun box(): String {
    val a: dynamic = A()
    return a.method(null, null)
}