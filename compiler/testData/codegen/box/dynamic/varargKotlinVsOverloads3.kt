// TODO: muted automatically, investigate should it be ran for JS or not
// IGNORE_BACKEND: JS
// JVM_TARGET: 1.8
// WITH_RUNTIME

class A {
    fun doWork(x: Int, vararg y: Int) = x.toString() + y.fold("", { a, b -> a + b.toString() })

    fun doWork(x: Int, vararg y: String) = x.toString() + y.fold("", { a, b -> a + b })
}

fun call(a: dynamic, y: dynamic) = a.doWork(5, y, y)

fun box(): String {
    val a: dynamic = A()
    val res = call(a, 1) + call(a, "I")

    return if (res == "5115II") "OK" else res.toString()
}