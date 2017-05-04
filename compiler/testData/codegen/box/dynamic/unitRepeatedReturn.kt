// TODO: muted automatically, investigate should it be ran for JS or not
// IGNORE_BACKEND: JS
// JVM_TARGET: 1.8
// WITH_RUNTIME

class A {
    fun method1() {}
}

fun call(x: dynamic) = x.method1()

fun box(): String {
    val a: dynamic = A()

    if (call(a).toString() != "kotlin.Unit")
        return "FAIL1"

    if (call(a).toString() != "kotlin.Unit")
        return "FAIL2"

    if (call(a).toString() != "kotlin.Unit")
        return "FAIL3"

    return call(a).toString().replace("kotlin.Unit", "OK")
}