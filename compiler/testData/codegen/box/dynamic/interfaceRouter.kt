// TODO: muted automatically, investigate should it be ran for JS or not
// IGNORE_BACKEND: JS
// JVM_TARGET: 1.8
// WITH_RUNTIME

interface I1;
interface I2;
class A : I1, I2;

class Call {
    fun method(x: Int) = "FAIL1"
    fun method(x: I1) = "FAIL-AMB-1"
    fun method(x: I2) = "FAIL-AMB-2"
    fun method(x: String) = "FAIL2"
}


fun box(): String {
    val a: dynamic = A()
    val call: dynamic = Call()
    try {
        return call.method(a)
    }
    catch (e: kotlin.DynamicBindException) {
        return "OK"
    }
}