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
    operator fun plus(other: MyObject): MyObject {
        value += other.value
        return this
    }
}




fun box(): String {
    val z: dynamic = ListenerMap<Long, MyObject>()
    z.put(5L, MyObject(15))
    try {
        z[5L]!! += MyObject(11)
    }
    catch (e: kotlin.DynamicBindException) {
        return "OK"
    }
    return "FAIL"
}