package xyz.malefic.compose.prefs.collection

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.json.Json
import xyz.malefic.compose.prefs.Common
import xyz.malefic.compose.prefs.Preferences

/**
 * A persistent implementation of a HashMap that saves its state to preferences.
 *
 * @param K the type of keys maintained by this map
 * @param V the type of mapped values
 * @param key the key used to store the map in preferences
 * @param keySerializer the KSerializer for type K
 * @param valueSerializer the KSerializer for type V
 * @param prefs the preferences instance used to store the map
 */
class PersistentHashMap<K, V>(
    private val key: String,
    private val keySerializer: KSerializer<K>,
    private val valueSerializer: KSerializer<V>,
    private val prefs: Preferences = Common.prefs,
) : MutableMap<K, V> {
    private val json = Json { ignoreUnknownKeys = true }
    private val mapSerializer = MapSerializer(keySerializer, valueSerializer)
    private val backingMap = mutableMapOf<K, V>()

    override val size: Int get() = backingMap.size
    override val entries: MutableSet<MutableMap.MutableEntry<K, V>> get() = backingMap.entries
    override val keys: MutableSet<K> get() = backingMap.keys
    override val values: MutableCollection<V> get() = backingMap.values

    init {
        loadFromPreferences()
    }

    private fun loadFromPreferences() {
        backingMap.clear()
        val jsonString = prefs.getString(key, null) ?: return
        try {
            val map = json.decodeFromString(mapSerializer, jsonString)
            backingMap.putAll(map)
        } catch (e: Exception) {
            // Ignore deserialization errors
        }
    }

    private fun saveToPreferences() {
        val jsonString = json.encodeToString(mapSerializer, backingMap.toMap())
        prefs.putString(key, jsonString)
    }

    /**
     * Associates the specified value with the specified key in this map and saves to preferences.
     * @param key the key with which the specified value is to be associated
     * @param value the value to be associated with the specified key
     * @return the previous value associated with the key, or null if there was no mapping
     */
    override fun put(
        key: K,
        value: V,
    ): V? {
        val result = backingMap.put(key, value)
        saveToPreferences()
        return result
    }

    /**
     * Copies all mappings from the specified map to this map and saves to preferences.
     * @param from the map whose mappings are to be copied
     */
    override fun putAll(from: Map<out K, V>) {
        backingMap.putAll(from)
        saveToPreferences()
    }

    /**
     * Removes the mapping for the specified key from this map and saves to preferences.
     * @param key the key whose mapping is to be removed
     * @return the previous value associated with the key, or null if there was no mapping
     */
    override fun remove(key: K): V? {
        val result = backingMap.remove(key)
        saveToPreferences()
        return result
    }

    /**
     * Removes all mappings from this map and saves the empty state to preferences.
     */
    override fun clear() {
        backingMap.clear()
        saveToPreferences()
    }

    /**
     * Clears the map and removes it from preferences entirely.
     */
    fun reset() {
        clear()
        prefs.remove(key)
    }

    /**
     * Returns true if this map contains a mapping for the specified key.
     * @param key the key to check for
     * @return true if the map contains the key
     */
    override fun containsKey(key: K): Boolean = backingMap.containsKey(key)

    /**
     * Returns true if this map maps one or more keys to the specified value.
     * @param value the value to check for
     * @return true if the map contains the value
     */
    override fun containsValue(value: V): Boolean = backingMap.containsValue(value)

    /**
     * Returns the value to which the specified key is mapped, or null if this map contains no mapping for the key.
     * @param key the key whose associated value is to be returned
     * @return the value associated with the key, or null
     */
    override fun get(key: K): V? = backingMap[key]

    /**
     * Returns true if this map contains no key-value mappings.
     * @return true if the map is empty
     */
    override fun isEmpty(): Boolean = backingMap.isEmpty()
}
