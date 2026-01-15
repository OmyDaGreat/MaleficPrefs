package xyz.malefic.compose.prefs

/**
 * Multiplatform abstraction for persistent key-value storage.
 *
 * This interface provides a platform-agnostic API for storing and retrieving
 * preference data across different platforms (JVM, JS, Native, WASM).
 */
expect class Preferences {
    /**
     * Gets an integer value from preferences.
     *
     * @param key The preference key
     * @param defaultValue The default value if key doesn't exist
     * @return The stored value or default
     */
    fun getInt(
        key: String,
        defaultValue: Int,
    ): Int

    /**
     * Stores an integer value in preferences.
     *
     * @param key The preference key
     * @param value The value to store
     */
    fun putInt(
        key: String,
        value: Int,
    )

    /**
     * Gets a long value from preferences.
     *
     * @param key The preference key
     * @param defaultValue The default value if key doesn't exist
     * @return The stored value or default
     */
    fun getLong(
        key: String,
        defaultValue: Long,
    ): Long

    /**
     * Stores a long value in preferences.
     *
     * @param key The preference key
     * @param value The value to store
     */
    fun putLong(
        key: String,
        value: Long,
    )

    /**
     * Gets a float value from preferences.
     *
     * @param key The preference key
     * @param defaultValue The default value if key doesn't exist
     * @return The stored value or default
     */
    fun getFloat(
        key: String,
        defaultValue: Float,
    ): Float

    /**
     * Stores a float value in preferences.
     *
     * @param key The preference key
     * @param value The value to store
     */
    fun putFloat(
        key: String,
        value: Float,
    )

    /**
     * Gets a double value from preferences.
     *
     * @param key The preference key
     * @param defaultValue The default value if key doesn't exist
     * @return The stored value or default
     */
    fun getDouble(
        key: String,
        defaultValue: Double,
    ): Double

    /**
     * Stores a double value in preferences.
     *
     * @param key The preference key
     * @param value The value to store
     */
    fun putDouble(
        key: String,
        value: Double,
    )

    /**
     * Gets a boolean value from preferences.
     *
     * @param key The preference key
     * @param defaultValue The default value if key doesn't exist
     * @return The stored value or default
     */
    fun getBoolean(
        key: String,
        defaultValue: Boolean,
    ): Boolean

    /**
     * Stores a boolean value in preferences.
     *
     * @param key The preference key
     * @param value The value to store
     */
    fun putBoolean(
        key: String,
        value: Boolean,
    )

    /**
     * Gets a string value from preferences.
     *
     * @param key The preference key
     * @param defaultValue The default value if key doesn't exist
     * @return The stored value or default
     */
    fun getString(
        key: String,
        defaultValue: String?,
    ): String?

    /**
     * Stores a string value in preferences.
     *
     * @param key The preference key
     * @param value The value to store
     */
    fun putString(
        key: String,
        value: String,
    )

    /**
     * Gets a byte array from preferences.
     *
     * @param key The preference key
     * @param defaultValue The default value if key doesn't exist
     * @return The stored value or default
     */
    fun getByteArray(
        key: String,
        defaultValue: ByteArray?,
    ): ByteArray?

    /**
     * Stores a byte array in preferences.
     *
     * @param key The preference key
     * @param value The value to store
     */
    fun putByteArray(
        key: String,
        value: ByteArray,
    )

    /**
     * Removes a preference by key.
     *
     * @param key The preference key to remove
     */
    fun remove(key: String)

    /**
     * Clears all preferences.
     */
    fun clear()

    companion object {
        /**
         * Creates a preferences instance with the given node name.
         *
         * @param nodeName The name of the preference node
         * @return A new Preferences instance
         */
        fun userNode(nodeName: String): Preferences
    }
}
