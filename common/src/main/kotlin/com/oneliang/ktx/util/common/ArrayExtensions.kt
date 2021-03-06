package com.oneliang.ktx.util.common

inline fun <T, K> Array<T>.toMapBy(keySelector: (t: T) -> K): Map<K, T> = this.associateBy { keySelector(it) }

inline fun <T, K, V> Array<T>.toMap(transform: (t: T) -> Pair<K, V>): Map<K, V> = this.associate(transform)

inline fun <T, K, V> Array<out T>.toMapWithIndex(transform: (index: Int, t: T) -> Pair<K, V>): Map<K, V> {
    val map = mutableMapOf<K, V>()
    this.forEachIndexed { index, t ->
        map += transform(index, t)
    }
    return map
}

/**
 * find the different value which in this but not in compare array
 */
fun <T> Array<T>.differs(compareArray: Array<T>, valueComparator: (value: T, compareValue: T) -> Boolean = { value, compareValue -> value == compareValue }): List<T> {
    val map = this.toMap { it to it }
    val compareMap = compareArray.toMap { it to it }
    return map.differs(compareMap) { _: T, value: T, mapValue: T ->
        valueComparator(value, mapValue)
    }
}

fun <T> Array<T>.sameAs(array: Array<T>): Boolean = this.size == array.size && this.differs(array).isEmpty()

fun <T> Array<T>.includes(array: Array<T>): Boolean = array.differs(this).isEmpty()

fun <T> Array<T>.matches(array: Array<T>): Boolean = this.includes(array)

inline fun <T, reified R> Array<T>.toNewArrayWithIndex(transform: (index: Int, t: T) -> R): Array<R> {
    return Array(this.size) { index -> transform(index, this[index]) }
}

inline fun <T, reified R> Array<T>.toNewArray(transform: (T) -> R): Array<R> {
    return this.toNewArrayWithIndex { _, t -> transform(t) }
}