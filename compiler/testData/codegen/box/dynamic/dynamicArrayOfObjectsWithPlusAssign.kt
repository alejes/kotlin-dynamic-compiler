// TODO: muted automatically, investigate should it be ran for JS or not
// IGNORE_BACKEND: JS
// JVM_TARGET: 1.8
// WITH_RUNTIME

var operationsCount = 0

class ListenerList<T> : AbstractMutableList<T>() {
    private val list = mutableListOf<T>()
    override fun add(index: Int, element: T) {
        list.add(index, element)
    }

    override fun removeAt(index: Int): T
            = list.removeAt(index)


    override fun set(index: Int, element: T): T {
        ++operationsCount
        return list.set(index, element);
    }

    override val size: Int
        get() = list.size

    override fun get(index: Int): T {
        ++operationsCount
        return list.get(index)
    }
}


class MyObject (var value: Int) {
    operator fun plusAssign(other: MyObject) {
        value += other.value
    }
}




fun box(): String {
    val z: dynamic = ListenerList<MyObject>()
    z.add(MyObject(5))
    z[0] += MyObject(18)
    val result1 = if (z[0].value == 23) "O" else "FAIL"
    val result2 = if (operationsCount == 2) "K" else "FAIL"
    return result1 + result2
}