// TODO: muted automatically, investigate should it be ran for JS or not
// IGNORE_BACKEND: JS
// JVM_TARGET: 1.8

// FILE: A.java
class A {
    public String field1 = "OK";
    public String getField1() {
        return "GETTER";
    }
}

// FILE: B.kt

fun box(): String {
    val a: dynamic = A()
    return a.field1
}