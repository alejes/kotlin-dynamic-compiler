// TODO: muted automatically, investigate should it be ran for JS or not
// IGNORE_BACKEND: JS
// JVM_TARGET: 1.8
// WITH_RUNTIME


fun box(): String {
    val m = mutableMapOf(1 to 1, 2 to 4, 3 to 9)
    m.put(4, 16)
    val res = m.size + m[4]!!
    return if (res == 20) "OK" else res.toString()
}


