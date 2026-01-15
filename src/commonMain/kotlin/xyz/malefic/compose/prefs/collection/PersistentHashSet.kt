package xyz.malefic.compose.prefs.collection

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.SetSerializer
import kotlinx.serialization.json.Json
import xyz.malefic.compose.prefs.Common
import xyz.malefic.compose.prefs.Preferences

/**
 * A persistent implementation of a HashSet that saves its state to preferences.
 *
 * @param T the type of elements in this set
 * @param key the key used to store the set in preferences
 * @param serializer the KSerializer for type T
 * @param prefs the preferences instance used to store the set
 */
class PersistentHashSet<T>(
    private val key: String,
    private val serializer: KSerializer<T>,
    private val prefs: Preferences = Common.prefs,
) : MutableSet<T> {
    private val json = Json { ignoreUnknownKeys = true }
    private val setSerializer = SetSerializer(serializer)
    private val backingSet = mutableSetOf<T>()

    override val size: Int get() = backingSet.size

    init {
        loadFromPreferences()
    }

    private fun loadFromPreferences() {
        backingSet.clear()
        val jsonString = prefs.getString(key, null) ?: return
        try {
            val set = json.decodeFromString(setSerializer, jsonString)
            backingSet.addAll(set)
        } catch (e: Exception) {
            // Ignore deserialization errors
        }
    }

    private fun saveToPreferences() {
        val jsonString = json.encodeToString(setSerializer, backingSet.toSet())
        prefs.putString(key, jsonString)
    }

    override fun add(element: T): Boolean {
        val result = backingSet.add(element)
        saveToPreferences()
        return result
    }

    override fun addAll(elements: Collection<T>): Boolean {
        val result = backingSet.addAll(elements)
        saveToPreferences()
        return result
    }

    override fun remove(element: T): Boolean {
        val result = backingSet.remove(element)
        saveToPreferences()
        return result
    }

    override fun removeAll(elements: Collection<T>): Boolean {
        val result = backingSet.removeAll(elements.toSet())
        saveToPreferences()
        return result
    }

    override fun retainAll(elements: Collection<T>): Boolean {
        val result = backingSet.retainAll(elements.toSet())
        saveToPreferences()
        return result
    }

    override fun clear() {
        backingSet.clear()
        saveToPreferences()
    }

    fun reset() {
        clear()
        prefs.remove(key)
    }

    override fun contains(element: T): Boolean = backingSet.contains(element)

    override fun containsAll(elements: Collection<T>): Boolean = backingSet.containsAll(elements)

    override fun isEmpty(): Boolean = backingSet.isEmpty()

    override fun iterator(): MutableIterator<T> = backingSet.iterator()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false
        other as PersistentHashSet<*>
        return backingSet == other.backingSet
    }

    override fun hashCode(): Int = backingSet.hashCode()
}
