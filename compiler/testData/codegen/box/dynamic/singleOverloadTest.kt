// TODO: muted automatically, investigate should it be ran for JS or not
// IGNORE_BACKEND: JS
// JVM_TARGET: 1.8


fun box(): String {
    val x: dynamic = "K"
    return "O".plus(x)
}