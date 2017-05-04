// TODO: muted automatically, investigate should it be ran for JS or not
// IGNORE_BACKEND: JS
// JVM_TARGET: 1.8
// WITH_RUNTIME

class A {
    fun doWork(vararg x: Int) = x.fold(0, { a, b -> a + b })
}

fun box(): String {
    val a: dynamic = A()
    val res = a.doWork(1, 2, 3, 4, 5)
    return if (res == 15) "OK" else res.toString()
}