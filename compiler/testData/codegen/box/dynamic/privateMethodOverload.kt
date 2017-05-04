// TODO: muted automatically, investigate should it be ran for JS or not
// IGNORE_BACKEND: JS
// JVM_TARGET: 1.8

open class Base
class Derived : Base()

class A {
    fun method1(b: Base) = "OK"
    private fun method1(b: Derived) = "DERIVED"
}

fun box(): String {
    val a = A()
    return a.method1(Derived())
}