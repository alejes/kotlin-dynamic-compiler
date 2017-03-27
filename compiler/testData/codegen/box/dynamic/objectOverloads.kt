// TODO: muted automatically, investigate should it be ran for JS or not
// IGNORE_BACKEND: JS
// JVM_TARGET: 1.8

class InvokeObject {
    operator fun invoke(x: Int, y: Int) = 24
    operator fun invoke(x: Int, y: String) = 25
}

fun call(x: dynamic, y: dynamic) = x.invoke(5, y)

fun box(): String {
    val a: dynamic = InvokeObject()
    val res1 = call(a, 241)
    val res2 = call(a, "STR")
    val res = res1 + res2
    return if (res == 49) "OK" else res.toString()
}
