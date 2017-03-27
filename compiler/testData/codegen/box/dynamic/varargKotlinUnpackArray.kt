// TODO: muted automatically, investigate should it be ran for JS or not
// IGNORE_BACKEND: JS
// JVM_TARGET: 1.8
// WITH_RUNTIME

class A {
    fun doWork(x: Int, vararg y: Int) = x.toString() + y.fold("", { a, b -> a + b.toString() })
}

fun box(): String {
    val a: dynamic = A()
    val res = a.doWork(17, *IntArray(3, { _ -> 5 }))

    return if (res == "17555") "OK" else res
}