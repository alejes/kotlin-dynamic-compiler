// TODO: muted automatically, investigate should it be ran for JS or not
// IGNORE_BACKEND: JS
// JVM_TARGET: 1.8

// FILE: A.java

public class A {
    public String field = "FAIL";
}


// FILE: B.kt

import A.*;

fun box(): String {
    val a: dynamic = A()
    a.field = "OK"
    return a.field
}