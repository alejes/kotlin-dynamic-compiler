// TODO: muted automatically, investigate should it be ran for JS or not
// IGNORE_BACKEND: JS
// JVM_TARGET: 1.8

class A {
    var x: Long = 55L
}

fun box(): String {
    var a: dynamic = A()
    a.x += 17L
    return if (a.x == 72L) "OK" else a.x.toString()
}
