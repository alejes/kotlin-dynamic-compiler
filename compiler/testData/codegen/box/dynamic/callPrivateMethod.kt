// TODO: muted automatically, investigate should it be ran for JS or not
// IGNORE_BACKEND: JS
// JVM_TARGET: 1.8
// WITH_RUNTIME

class A {
    private fun method() = "FAIL"
}

fun box(): String {
    val x: dynamic = A()
    try {
        val result = x.method()
    }
    catch (e: kotlin.DynamicBindException) {
        return "OK"
    }
    return "FAIL"
}