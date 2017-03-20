// TODO: muted automatically, investigate should it be ran for JS or not
// IGNORE_BACKEND: JS
// JVM_TARGET: 1.8

fun box(): String {
    var x: dynamic = 6L
    x += 17L
    return if (x == 23L) "OK" else x.toString()
}
