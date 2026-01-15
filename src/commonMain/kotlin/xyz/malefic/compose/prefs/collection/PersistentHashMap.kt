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

    override fun put(
        key: K,
        value: V,
    ): V? {
        val result = backingMap.put(key, value)
        saveToPreferences()
        return result
    }

    override fun putAll(from: Map<out K, V>) {
        backingMap.putAll(from)
        saveToPreferences()
    }

    override fun remove(key: K): V? {
        val result = backingMap.remove(key)
        saveToPreferences()
        return result
    }

    override fun clear() {
        backingMap.clear()
        saveToPreferences()
    }

    fun reset() {
        clear()
        prefs.remove(key)
    }

    override fun containsKey(key: K): Boolean = backingMap.containsKey(key)

    override fun containsValue(value: V): Boolean = backingMap.containsValue(value)

    override fun get(key: K): V? = backingMap[key]

    override fun isEmpty(): Boolean = backingMap.isEmpty()
}
