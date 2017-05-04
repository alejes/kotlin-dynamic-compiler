// TODO: muted automatically, investigate should it be ran for JS or not
// IGNORE_BACKEND: JS
// JVM_TARGET: 1.8

class MyObject (var value: Int) {
    operator fun plusAssign(other: MyObject) {
        value += other.value
    }
}

fun box(): String {
    val a: dynamic = MyObject(0)
    val b: dynamic = MyObject(7)

    for (i in 1..5) {
        a += b
    }
    
    return if (a.value == 35) "OK" else a.value.toString()
}