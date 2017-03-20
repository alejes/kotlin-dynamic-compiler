// TODO: muted automatically, investigate should it be ran for JS or not
// IGNORE_BACKEND: JS
// JVM_TARGET: 1.8

interface MyInterface {
    fun method1(): String
}

fun box(): String {
    val x: dynamic = object : MyInterface {
        override fun method1() = "OK"
    }
    return x.method1()
}