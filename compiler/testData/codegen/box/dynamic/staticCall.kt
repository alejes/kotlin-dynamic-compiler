// TODO: muted automatically, investigate should it be ran for JS or not
// IGNORE_BACKEND: JS

class A {
    fun method() = "OK"
}

fun box(): String {
    val x: dynamic = "OK"
    return String.valueof(x)
}