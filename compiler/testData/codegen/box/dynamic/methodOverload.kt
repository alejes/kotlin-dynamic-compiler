// TODO: muted automatically, investigate should it be ran for JS or not
// IGNORE_BACKEND: JS

class A {
    fun method(x: String) = "FAIL"
    fun method(x: Int) = "OK"
}

fun box(): String {
    val x: dynamic = A()
    return x.method(5)
}
