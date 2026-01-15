package xyz.malefic.compose.prefs

import java.util.prefs.Preferences as JavaPreferences

/**
 * JVM implementation of Preferences using Java's built-in Preferences API.
 */
actual class Preferences(
    private val javaPrefs: JavaPreferences,
) {
    actual fun getInt(
        key: String,
        defaultValue: Int,
    ): Int = javaPrefs.getInt(key, defaultValue)

    actual fun putInt(
        key: String,
        value: Int,
    ) {
        javaPrefs.putInt(key, value)
    }

    actual fun getLong(
        key: String,
        defaultValue: Long,
    ): Long = javaPrefs.getLong(key, defaultValue)

    actual fun putLong(
        key: String,
        value: Long,
    ) {
        javaPrefs.putLong(key, value)
    }

    actual fun getFloat(
        key: String,
        defaultValue: Float,
    ): Float = javaPrefs.getFloat(key, defaultValue)

    actual fun putFloat(
        key: String,
        value: Float,
    ) {
        javaPrefs.putFloat(key, value)
    }

    actual fun getDouble(
        key: String,
        defaultValue: Double,
    ): Double = javaPrefs.getDouble(key, defaultValue)

    actual fun putDouble(
        key: String,
        value: Double,
    ) {
        javaPrefs.putDouble(key, value)
    }

    actual fun getBoolean(
        key: String,
        defaultValue: Boolean,
    ): Boolean = javaPrefs.getBoolean(key, defaultValue)

    actual fun putBoolean(
        key: String,
        value: Boolean,
    ) {
        javaPrefs.putBoolean(key, value)
    }

    actual fun getString(
        key: String,
        defaultValue: String?,
    ): String? = javaPrefs.get(key, defaultValue)

    actual fun putString(
        key: String,
        value: String,
    ) {
        javaPrefs.put(key, value)
    }

    actual fun getByteArray(
        key: String,
        defaultValue: ByteArray?,
    ): ByteArray? = javaPrefs.getByteArray(key, defaultValue)

    actual fun putByteArray(
        key: String,
        value: ByteArray,
    ) {
        javaPrefs.putByteArray(key, value)
    }

    actual fun remove(key: String) {
        javaPrefs.remove(key)
    }

    actual fun clear() {
        javaPrefs.clear()
    }

    actual companion object {
        actual fun userNode(nodeName: String): Preferences = Preferences(JavaPreferences.userRoot().node(nodeName))
    }
}
