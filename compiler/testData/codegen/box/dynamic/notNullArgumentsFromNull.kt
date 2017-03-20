// TODO: muted automatically, investigate should it be ran for JS or not
// IGNORE_BACKEND: JS
// JVM_TARGET: 1.8

class A {
    fun method(y: String?) = y ?: "O"
}

fun runner (x: dynamic, argument: Any?): String {
    return x.method(argument)
}

fun box(): String {
    val a: dynamic = A()
    return runner(a, null) + runner(a, "O")
}