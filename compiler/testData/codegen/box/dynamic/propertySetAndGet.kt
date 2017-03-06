// TODO: muted automatically, investigate should it be ran for JS or not
// IGNORE_BACKEND: JS

class A {
    var property = "FAIL"
}

fun box() {
    val x: dynamic = A()
    x.property = "OK"
    return x.property
}