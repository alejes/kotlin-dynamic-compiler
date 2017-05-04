// TODO: muted automatically, investigate should it be ran for JS or not
// IGNORE_BACKEND: JS
// JVM_TARGET: 1.8
// WITH_RUNTIME

class A {
    fun doWork(s1: String, x5: Int, vararg x: Int) = s1 + x5 + x.fold(0, { a, b -> a + b })
}

fun box(): String {
    val a: dynamic = A()
    val res = a.doWork("QQQ", 555, 1, 2, 3, 4, 5)
    return if (res == "QQQ55515") "OK" else res
}