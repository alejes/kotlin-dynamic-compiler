// TODO: muted automatically, investigate should it be ran for JS or not
// IGNORE_BACKEND: JS
// JVM_TARGET: 1.8

fun box(): String {
    var x: dynamic = 6
    x += 17
    return if (x == 23) "OK" else x.toString()
}
