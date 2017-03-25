// TODO: muted automatically, investigate should it be ran for JS or not
// IGNORE_BACKEND: JS
// JVM_TARGET: 1.8
// WITH_RUNTIME

class A {
    fun doWork(vararg x: Int) = x.fold(28, { a, b -> a + b })
}

fun box(): String {
    val a = A()
    val res = a.doWork()
    return if (res == 28) "OK" else res.toString()
}