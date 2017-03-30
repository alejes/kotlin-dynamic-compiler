// TODO: muted automatically, investigate should it be ran for JS or not
// IGNORE_BACKEND: JS
// JVM_TARGET: 1.8


class SomeException(x: String) : java.lang.Exception(x)

class A {
    fun method() {
        throw SomeException("OK")
    }
}

fun box(): String {
    val a: dynamic = A()

    try {
        a.method()
    }
    catch (e: SomeException) {
        if (e::class != SomeException::class) {
            return "FAIL_WRONG-CLASS"
        }
        return e.message ?: "FAIL_NO-MESSAGE"
    }
    return "FAIL"
}