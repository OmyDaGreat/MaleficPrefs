package xyz.malefic.compose.prefs

/**
 * WASM-JS implementation using singleton in-memory storage.
 * For browser environments with localStorage available, this could be enhanced with JS interop.
 */
actual class Preferences private constructor(
    private val nodeName: String,
) {
    private val storage: MutableMap<String, String>
        get() = storageMap.getOrPut(nodeName) { mutableMapOf() }

    private fun getKey(key: String) = "$nodeName.$key"

    actual fun getInt(
        key: String,
        defaultValue: Int,
    ): Int = storage[getKey(key)]?.toIntOrNull() ?: defaultValue

    actual fun putInt(
        key: String,
        value: Int,
    ) {
        storage[getKey(key)] = value.toString()
    }

    actual fun getLong(
        key: String,
        defaultValue: Long,
    ): Long = storage[getKey(key)]?.toLongOrNull() ?: defaultValue

    actual fun putLong(
        key: String,
        value: Long,
    ) {
        storage[getKey(key)] = value.toString()
    }

    actual fun getFloat(
        key: String,
        defaultValue: Float,
    ): Float = storage[getKey(key)]?.toFloatOrNull() ?: defaultValue

    actual fun putFloat(
        key: String,
        value: Float,
    ) {
        storage[getKey(key)] = value.toString()
    }

    actual fun getDouble(
        key: String,
        defaultValue: Double,
    ): Double = storage[getKey(key)]?.toDoubleOrNull() ?: defaultValue

    actual fun putDouble(
        key: String,
        value: Double,
    ) {
        storage[getKey(key)] = value.toString()
    }

    actual fun getBoolean(
        key: String,
        defaultValue: Boolean,
    ): Boolean = storage[getKey(key)]?.toBoolean() ?: defaultValue

    actual fun putBoolean(
        key: String,
        value: Boolean,
    ) {
        storage[getKey(key)] = value.toString()
    }

    actual fun getString(
        key: String,
        defaultValue: String?,
    ): String? = storage[getKey(key)] ?: defaultValue

    actual fun putString(
        key: String,
        value: String,
    ) {
        storage[getKey(key)] = value
    }

    actual fun getByteArray(
        key: String,
        defaultValue: ByteArray?,
    ): ByteArray? {
        val base64 = storage[getKey(key)] ?: return defaultValue
        return try {
            base64.decodeBase64()
        } catch (e: Exception) {
            defaultValue
        }
    }

    actual fun putByteArray(
        key: String,
        value: ByteArray,
    ) {
        storage[getKey(key)] = value.encodeBase64()
    }

    actual fun remove(key: String) {
        storage.remove(getKey(key))
    }

    actual fun clear() {
        val prefix = "$nodeName."
        storage.keys.filter { it.startsWith(prefix) }.forEach { storage.remove(it) }
    }

    actual companion object {
        private val instances = mutableMapOf<String, Preferences>()
        private val storageMap = mutableMapOf<String, MutableMap<String, String>>()

        actual fun userNode(nodeName: String): Preferences = instances.getOrPut(nodeName) { Preferences(nodeName) }
    }
}

private fun ByteArray.encodeBase64(): String {
    val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
    var output = ""
    var padding = 0
    var position = 0

    while (position < size) {
        val b1 = this[position++].toInt() and 0xFF
        val b2 = if (position < size) this[position++].toInt() and 0xFF else 0.also { padding++ }
        val b3 = if (position < size) this[position++].toInt() and 0xFF else 0.also { padding++ }

        val triple = (b1 shl 16) or (b2 shl 8) or b3

        output += chars[(triple shr 18) and 0x3F]
        output += chars[(triple shr 12) and 0x3F]
        output += if (padding >= 2) '=' else chars[(triple shr 6) and 0x3F]
        output += if (padding >= 1) '=' else chars[triple and 0x3F]
    }

    return output
}

private fun String.decodeBase64(): ByteArray {
    val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
    val input = this.replace("=", "")
    val output = mutableListOf<Byte>()

    var position = 0
    while (position < input.length) {
        val c1 = chars.indexOf(input[position++])
        val c2 = if (position < input.length) chars.indexOf(input[position++]) else 0
        val c3 = if (position < input.length) chars.indexOf(input[position++]) else 0
        val c4 = if (position < input.length) chars.indexOf(input[position++]) else 0

        val triple = (c1 shl 18) or (c2 shl 12) or (c3 shl 6) or c4

        output.add((triple shr 16).toByte())
        if (position - 2 < length || this[length - 2] != '=') {
            output.add((triple shr 8).toByte())
        }
        if (position - 1 < length || this[length - 1] != '=') {
            output.add(triple.toByte())
        }
    }

    return output.toByteArray()
}
