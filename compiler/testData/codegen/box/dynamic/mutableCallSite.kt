// TODO: muted automatically, investigate should it be ran for JS or not
// IGNORE_BACKEND: JS

class A {
    fun method(x: String) = "K"
    fun method(x: Int) = "O"
    fun method(x: Double) = "Z"
    fun method(x: Boolean) = "F"

    fun call(x: dynamic) = method(x)
}

fun box(): String {
    val x: dynamic = A()
    return x.call(5) + x.call("A")
}
