// TODO: muted automatically, investigate should it be ran for JS or not
// IGNORE_BACKEND: JS
// JVM_TARGET: 1.8
// WITH_RUNTIME

class A {
    fun doWork(vararg x: Int) = x.fold(1101, { a, b -> a + b })

    fun doWork() = -1
}

fun box(): String {
    val a = A()
    val res = a.doWork()
    return if (res == -1) "OK" else res.toString()
}