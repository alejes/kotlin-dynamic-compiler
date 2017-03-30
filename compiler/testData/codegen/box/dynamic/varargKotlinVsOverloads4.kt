// TODO: muted automatically, investigate should it be ran for JS or not
// IGNORE_BACKEND: JS
// JVM_TARGET: 1.8
// WITH_RUNTIME

class A {
    fun doWork(x: Int, z: String, vararg y: Int) = x.toString() + z + y.fold("", { a, b -> a + b.toString() })

    fun doWork(x: Int, vararg y: String) = x.toString() + y.fold("", { a, b -> a + b })
}

fun box(): String {
    val a: dynamic = A()
    try {
        return a.doWork(5, "STR")
    }
    catch (e: kotlin.DynamicBindException) {
        return "OK"
    }
}