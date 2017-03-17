// TODO: muted automatically, investigate should it be ran for JS or not
// IGNORE_BACKEND: JS
// JVM_TARGET: 1.8

class A {
    var property = "FAIL"
}

fun box(): String {
    val x: dynamic = A()
    x.property = "OK"
    return "OK"
}