// TODO: muted automatically, investigate should it be ran for JS or not
// IGNORE_BACKEND: JS
// JVM_TARGET: 1.8

// FILE: A.java
class A {
    public String field1 = "FAIL";
    public String getField1() {
        return "GETTER";
    }
    public String setField1(String str) {
        return str + "SETTER";
    }
}

// FILE: B.kt

fun box(): String {
    val a: dynamic = A()
    a.field1 = "OK"
    return a.field1
}