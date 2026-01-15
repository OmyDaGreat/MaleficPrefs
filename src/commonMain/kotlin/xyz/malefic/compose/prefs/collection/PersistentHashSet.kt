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

    /**
     * Adds the specified element to the set if it is not already present and saves to preferences.
     * @param element the element to add
     * @return true if the element was added
     */
    override fun add(element: T): Boolean {
        val result = backingSet.add(element)
        saveToPreferences()
        return result
    }

    /**
     * Adds all elements from the specified collection to this set and saves to preferences.
     * @param elements the collection of elements to add
     * @return true if the set was modified
     */
    override fun addAll(elements: Collection<T>): Boolean {
        val result = backingSet.addAll(elements)
        saveToPreferences()
        return result
    }

    /**
     * Removes the specified element from the set and saves to preferences.
     * @param element the element to remove
     * @return true if the element was removed
     */
    override fun remove(element: T): Boolean {
        val result = backingSet.remove(element)
        saveToPreferences()
        return result
    }

    /**
     * Removes all elements from this set that are contained in the specified collection and saves to preferences.
     * @param elements the collection of elements to remove
     * @return true if the set was modified
     */
    override fun removeAll(elements: Collection<T>): Boolean {
        val result = backingSet.removeAll(elements.toSet())
        saveToPreferences()
        return result
    }

    /**
     * Retains only the elements in this set that are contained in the specified collection and saves to preferences.
     * @param elements the collection of elements to retain
     * @return true if the set was modified
     */
    override fun retainAll(elements: Collection<T>): Boolean {
        val result = backingSet.retainAll(elements.toSet())
        saveToPreferences()
        return result
    }

    /**
     * Removes all elements from this set and saves the empty state to preferences.
     */
    override fun clear() {
        backingSet.clear()
        saveToPreferences()
    }

    /**
     * Clears the set and removes it from preferences entirely.
     */
    fun reset() {
        clear()
        prefs.remove(key)
    }

    /**
     * Checks if this set contains the specified element.
     * @param element the element to check for
     * @return true if the element is in the set
     */
    override fun contains(element: T): Boolean = backingSet.contains(element)

    /**
     * Checks if this set contains all elements from the specified collection.
     * @param elements the collection of elements to check for
     * @return true if all elements are in the set
     */
    override fun containsAll(elements: Collection<T>): Boolean = backingSet.containsAll(elements)

    /**
     * Returns true if this set contains no elements.
     * @return true if the set is empty
     */
    override fun isEmpty(): Boolean = backingSet.isEmpty()

    /**
     * Returns an iterator over the elements in this set.
     * @return an iterator
     */
    override fun iterator(): MutableIterator<T> = backingSet.iterator()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false
        other as PersistentHashSet<*>
        return backingSet == other.backingSet
    }

    override fun hashCode(): Int = backingSet.hashCode()
}
