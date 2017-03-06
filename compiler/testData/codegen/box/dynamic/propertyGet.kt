// TODO: muted automatically, investigate should it be ran for JS or not
// IGNORE_BACKEND: JS

class A {
    val property = "OK"
}

fun box() {
    val x: dynamic = A()
    return x.property
}