// TODO: muted automatically, investigate should it be ran for JS or not
// IGNORE_BACKEND: JS

fun method(x: String): dynamic = "K"
fun method(x: Double): dynamic = "Z"
fun method(x: Int): dynamic = "O"
fun method(x: Boolean): dynamic = "F"

fun box(): String {
    val x1: dynamic = 5;
    val x2: dynamic = "A";

    return method(x1) + method(x2)
}