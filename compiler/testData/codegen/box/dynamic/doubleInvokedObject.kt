// TODO: muted automatically, investigate should it be ran for JS or not
// IGNORE_BACKEND: JS
// JVM_TARGET: 1.8

class Invoked {
    operator fun invoke() = { it: String -> "OK" }
}

fun box(): String {
    val io: dynamic = Invoked()
    return io()("ARGS")
}