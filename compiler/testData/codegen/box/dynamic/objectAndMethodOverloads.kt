// TODO: muted automatically, investigate should it be ran for JS or not
// IGNORE_BACKEND: JS
// JVM_TARGET: 1.8

open class A
class B : A()

class Call {
    operator fun invoke(x: B) = "B"
}

class MyClass {
    val field = Call()
    fun field(x: A) = "A"
}

fun box(): String {
    val a: dynamic = MyClass()
    val res = a.field(B())
    return if (res.toString() == "A") "OK" else res.toString()
}