// TODO: muted automatically, investigate should it be ran for JS or not
// IGNORE_BACKEND: JS
// JVM_TARGET: 1.8


// FILE: A.java

public class A {
    private static class A1 extends A2 implements I1 {
        public String method1(Base b) {
            return "BASE";
        }
    }

    private static class A2 extends A3 {
    }

    public static class A3  {
        public String method1(Derived d) {
            return "OK";
        }
    }

    public A3 getA() {
        return new A1 ();
    }
}

// FILE: Base.java

public class Base {
}

// FILE: Derived.java

public class Derived extends Base {
}


// FILE: I1.java

public interface I1 {
    public String method1(Base b);
}


// FILE: B.kt

fun box(): String {
    val obj = A().a
    return obj.method1(Derived())
}