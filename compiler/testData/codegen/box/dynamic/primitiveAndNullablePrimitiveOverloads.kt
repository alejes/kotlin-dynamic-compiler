// TODO: muted automatically, investigate should it be ran for JS or not
// IGNORE_BACKEND: JS
// JVM_TARGET: 1.8

class A {
    fun doWork(x: Int) = 76
    fun doWork(x: Int?) = 79
}

fun box(): String {
    val a: dynamic = A()
    val res = a.doWork(17 as Int?)

    return if (res == 79) "OK" else res.toString()
}