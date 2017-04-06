// TODO: muted automatically, investigate should it be ran for JS or not
// IGNORE_BACKEND: JS
// JVM_TARGET: 1.8

interface I;
class A : I;

class Call {
    fun method(x: Int) = "FAIL1"
    fun method(x: I) = "OK"
    fun method(x: String) = "FAIL2"
}


fun box(): String {
    val a: dynamic = A()
    val c: dynamic = Call()
    return c.method(a)
}