// TODO: muted automatically, investigate should it be ran for JS or not
// IGNORE_BACKEND: JS
// JVM_TARGET: 1.8

// FILE: A.java

public class A {
    public static String getString() {
        return null;
    }
}


// FILE: B.kt

import A.*;

fun box(): String {
    val a = A.getString()
    try {
        val e = a.length
    }
    catch (e: NullPointerException) {
        return "OK"
    }
    return "FAIL"
}