// TODO: muted automatically, investigate should it be ran for JS or not
// IGNORE_BACKEND: JS
// JVM_TARGET: 1.8

class A {
    val field = "O"
}

class B {
    val field = "K"
}

class Caller {
    fun call(x: dynamic): String = x.field
}



fun box(): String {
    val a: dynamic = A()
    val b: dynamic = B()
    val caller: dynamic = Caller()
    return caller.call(a)  + caller.call(b)
}
