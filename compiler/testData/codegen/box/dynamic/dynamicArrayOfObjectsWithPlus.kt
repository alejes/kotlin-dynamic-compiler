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

fun box(): String {
    val z: dynamic = ListenerList<Int>()
    z.add(5)
    z[0] += 15
    val result1 = if (z[0] == 20) "O" else "FAIL"
    val result2 = if (operationsCount == 3) "K" else "FAIL"
    return result1 + result2
}