// TODO: muted automatically, investigate should it be ran for JS or not
// IGNORE_BACKEND: JS
// JVM_TARGET: 1.8
// WITH_RUNTIME


class A {
    fun doWork(vararg x: Int) = x.fold(0, { a, b -> a + b })

    fun doWork(x: Int, y: Int) = 55515
}

fun box(): String {
    val a: dynamic = A()
    val res = a.doWork(11, 24)
    return if (res == 55515) "OK" else res.toString()
}