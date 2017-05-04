// TODO: muted automatically, investigate should it be ran for JS or not
// IGNORE_BACKEND: JS
// JVM_TARGET: 1.8

fun box(): String {
    var a: dynamic = 0

    for (i in 1..5) {
        a += 6
    }

    return if (a == 30) "OK" else a.toString()
}