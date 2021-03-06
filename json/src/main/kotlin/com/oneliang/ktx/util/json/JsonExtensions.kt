package com.oneliang.ktx.util.json

import com.oneliang.ktx.Constants
import com.oneliang.ktx.util.common.nullToBlank

fun BooleanArray.toJson() = joinToString(prefix = Constants.Symbol.MIDDLE_BRACKET_LEFT, postfix = Constants.Symbol.MIDDLE_BRACKET_RIGHT, separator = Constants.Symbol.COMMA) {
    it.toString()
}

fun ByteArray.toJson() = joinToString(prefix = Constants.Symbol.MIDDLE_BRACKET_LEFT, postfix = Constants.Symbol.MIDDLE_BRACKET_RIGHT, separator = Constants.Symbol.COMMA) {
    it.toString()
}

fun CharArray.toJson() = joinToString(prefix = Constants.Symbol.MIDDLE_BRACKET_LEFT, postfix = Constants.Symbol.MIDDLE_BRACKET_RIGHT, separator = Constants.Symbol.COMMA) {
    it.toString()
}

fun ShortArray.toJson() = joinToString(prefix = Constants.Symbol.MIDDLE_BRACKET_LEFT, postfix = Constants.Symbol.MIDDLE_BRACKET_RIGHT, separator = Constants.Symbol.COMMA) {
    it.toString()
}

fun IntArray.toJson() = joinToString(prefix = Constants.Symbol.MIDDLE_BRACKET_LEFT, postfix = Constants.Symbol.MIDDLE_BRACKET_RIGHT, separator = Constants.Symbol.COMMA) {
    it.toString()
}

fun LongArray.toJson() = joinToString(prefix = Constants.Symbol.MIDDLE_BRACKET_LEFT, postfix = Constants.Symbol.MIDDLE_BRACKET_RIGHT, separator = Constants.Symbol.COMMA) {
    it.toString()
}

fun FloatArray.toJson() = joinToString(prefix = Constants.Symbol.MIDDLE_BRACKET_LEFT, postfix = Constants.Symbol.MIDDLE_BRACKET_RIGHT, separator = Constants.Symbol.COMMA) {
    it.toString()
}

fun DoubleArray.toJson() = joinToString(prefix = Constants.Symbol.MIDDLE_BRACKET_LEFT, postfix = Constants.Symbol.MIDDLE_BRACKET_RIGHT, separator = Constants.Symbol.COMMA) {
    it.toString()
}

fun String.jsonMatches(map: Map<String, String>): Boolean {
    if (this.isBlank()) {
        return false
    }
    this.toJsonObject().forEach { key, value ->
        val inputValue = map[key].nullToBlank()
        val conditionValue = value.toString()
        if (inputValue == conditionValue) {
            return@forEach//continue
        }
        //not equal
        return false
    }
    return true
}

fun String.jsonToMap(): Map<String, String> {
    return this.jsonToMap { _, value -> value }
}

inline fun <R> String.jsonToMap(transform: (key: String, value: String) -> R): Map<String, R> {
    if (this.isBlank()) {
        return emptyMap()
    }
    return this.toJsonObject().toMap(transform)
}

fun String.toJsonObject(): JsonObject {
    if (this.isBlank()) {
        return JsonObject()
    }
    return JsonObject(this)
}

fun String.toJsonArray(): JsonArray {
    if (this.isBlank()) {
        return JsonArray()
    }
    return JsonArray(this)
}

fun JsonObject.toMap(): Map<String, String> {
    return this.toMap { _, value -> value }
}

inline fun <R> JsonObject.toMap(transform: (key: String, value: String) -> R): Map<String, R> {
    val map = mutableMapOf<String, R>()
    this.forEach { key, value ->
        map[key] = transform(key, value.toString())
    }
    return map
}

inline fun JsonObject.forEach(block: (key: String, value: Any) -> Unit) {
    this.keys().forEach {
        block(it, this.opt(it))
    }
}

inline fun JsonArray.forEach(block: (item: Any) -> Unit) = this.forEachWithIndex { _, any -> block(any) }

inline fun JsonArray.forEachWithIndex(block: (index: Int, item: Any) -> Unit) {
    val length = this.length()
    for (i in 0 until length) {
        block(i, this.get(i))
    }
}