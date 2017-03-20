// TODO: muted automatically, investigate should it be ran for JS or not
// IGNORE_BACKEND: JS
// JVM_TARGET: 1.8
// WITH_RUNTIME

var operationsCount = 0

class ListenerMap<K, V> : AbstractMutableMap<K, V>() {
    private val map = mutableMapOf<K, V>()

    override val size: Int
        get() = map.size

    override fun put(key: K, value: V): V? {
        operationsCount += 1
        return map.put(key, value)
    }

    override val entries: MutableSet<MutableMap.MutableEntry<K, V>>
        get() = map.entries

    override fun get(key: K): V? {
        operationsCount += 100
        return map.get(key)
    }
}


class MyObject (var value: Int) {
    operator fun plusAssign(other: MyObject) {
        value += other.value
    }
}




fun box(): String {
    val z: dynamic = ListenerMap<Long, MyObject>()
    z[5L] = MyObject(15)
    z[5L]!! += MyObject(11)
    val result1 = if (z[5L]!!.value == 26) "O" else "FAIL"
    val result2 = if (operationsCount == 201) "K" else "FAIL"
    return result1 + result2
}