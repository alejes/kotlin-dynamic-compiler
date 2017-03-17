// TODO: muted automatically, investigate should it be ran for JS or not
// IGNORE_BACKEND: JS
// JVM_TARGET: 1.8

class A {
    fun function1(x: Int, y: Int = 2, z: Int = 8): Int {
        return x + 10 * y + 100 * z
    }
}

fun box(): String {
    val a: dynamic = A()
    return if (a.function1(10).toString() == "830") "OK" else "FAIL"
}
