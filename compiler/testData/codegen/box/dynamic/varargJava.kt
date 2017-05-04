// TODO: muted automatically, investigate should it be ran for JS or not
// IGNORE_BACKEND: JS
// JVM_TARGET: 1.8
// WITH_RUNTIME

// FILE: A.java
class A {
    public String doWork(String ... x) {
        return x[0] + x[5] + x.length;
    }
}

// FILE: B.kt

fun box(): String {
    val a: dynamic = A()
    val res = a.doWork("1", "2", "3", "4", "5", "6")
    return if (res == "166") "OK" else res.toString()
}