// TODO: muted automatically, investigate should it be ran for JS or not
// IGNORE_BACKEND: JS
// JVM_TARGET: 1.8

class A {
    fun method(x: String) = "K"
    fun method(x: Int) = "O"
    fun method(x: Double) = "Z"
    fun method(x: Boolean) = "F"

    fun call(a: dynamic, x: dynamic) = a.method(x)
}



fun box(): String {
    val x: dynamic = A()
    return x.call(x, 5) + x.call(x, "A")
}
