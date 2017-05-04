// TODO: muted automatically, investigate should it be ran for JS or not
// IGNORE_BACKEND: JS
// JVM_TARGET: 1.8

class B {
    operator fun invoke(x: Int, y: Int) = 24
    operator fun invoke(x: Int, y: String) = 25
}

class A {
    public val field1 = B()
}

fun call(x: dynamic, y: dynamic) = x.field1(5, y)

fun box(): String {
    val x: dynamic = A()
    val res1 = call(x, 5)
    val res2 = call(x, "ww2")
    val res = res1.toString() + res2.toString()
    return if (res == "2425") "OK" else res
}