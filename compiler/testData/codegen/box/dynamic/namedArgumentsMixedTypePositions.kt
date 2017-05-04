// TODO: muted automatically, investigate should it be ran for JS or not
// IGNORE_BACKEND: JS
// JVM_TARGET: 1.8
// WITH_REFLECT

class A {
    fun function1(x: Int, y: Int = 2, z: String = "ABA", t: Double = 54.43, u: Int = 45, v: Int = -28, r: Double = 32.2): String {
        return z + (x + 10 * y +  913 * u + 843 * v) + r + t
    }
}

fun box(): String {
    val a: dynamic = A()
    return if (a.function1(10, v = 54, z = "EVA", t = 3.14271).toString() == "EVA8663732.23.14271") "OK" else "FAIL"
}
