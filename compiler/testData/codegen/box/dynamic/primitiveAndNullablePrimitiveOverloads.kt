// TODO: muted automatically, investigate should it be ran for JS or not
// IGNORE_BACKEND: JS
// JVM_TARGET: 1.8
// WITH_RUNTIME

class A {
    fun doWork(x: Int) = 76
    fun doWork(x: Int?) = 79
}

fun box(): String {
    val a: dynamic = A()

    var res = 0
    try {
        res = a.doWork(17 as Int?)
    }
    catch (e: kotlin.DynamicBindException) {
        return "OK"
    }

    return res.toString()
}