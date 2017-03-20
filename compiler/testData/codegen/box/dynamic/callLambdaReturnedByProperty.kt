class A {
    public val field1 = { "OK" }
}

fun box(): String {
    val x = A()
    return x.field1()
}