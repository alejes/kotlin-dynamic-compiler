// TODO: muted automatically, investigate should it be ran for JS or not
// IGNORE_BACKEND: JS
// JVM_TARGET: 1.8
// WITH_REFLECT

class A {
    fun function1(x: Int, y: Int = 2, z: Int = 8, u: Int = 45, v: Int = -28): Int {
        return x + 10 * y + 100 * z + 913 * u + 843 * v
    }
}

fun box(): String {
    val a: dynamic = A()
    return if (a.function1(10, v = 54, z = 11).toString() == "87737") "OK" else "FAIL"
}
