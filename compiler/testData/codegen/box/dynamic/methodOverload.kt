// TODO: muted automatically, investigate should it be ran for JS or not
// IGNORE_BACKEND: JS
// JVM_TARGET: 1.8

class A {
    fun method(x: String) = "FAIL"
    fun method(x: Int) = "OK"
}

fun box(): String {
    val x: dynamic = A()
    return x.method(5)
}
