// TODO: muted automatically, investigate should it be ran for JS or not
// IGNORE_BACKEND: JS
// JVM_TARGET: 1.8
// WITH_RUNTIME


fun box(): String {
    val z: dynamic = listOf(1, 2, 3, 4, 5)
    var max = z[0]
    for (element in z) {
        max = maxOf<dynamic>(max, element)
    }
    return if (max == 5) "OK" else max.toString()
}