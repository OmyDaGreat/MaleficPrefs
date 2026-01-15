package xyz.malefic.compose.prefs.collection

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import xyz.malefic.compose.prefs.Common
import xyz.malefic.compose.prefs.Preferences

/**
 * A persistent implementation of an ArrayList that saves its state to preferences.
 *
 * @param T the type of elements in this list
 * @param key the key used to store the list in preferences
 * @param serializer the KSerializer for type T
 * @param prefs the preferences instance used to store the list
 */
class PersistentArrayList<T>(
    private val key: String,
    private val serializer: KSerializer<T>,
    private val prefs: Preferences = Common.prefs,
) : MutableList<T> {
    private val json = Json { ignoreUnknownKeys = true }
    private val listSerializer = ListSerializer(serializer)
    private val backingList = mutableListOf<T>()

    override val size: Int get() = backingList.size

    init {
        loadFromPreferences()
    }

    private fun loadFromPreferences() {
        backingList.clear()
        val jsonString = prefs.getString(key, null) ?: return
        try {
            val list = json.decodeFromString(listSerializer, jsonString)
            backingList.addAll(list)
        } catch (e: Exception) {
            // Ignore deserialization errors
        }
    }

    private fun saveToPreferences() {
        val jsonString = json.encodeToString(listSerializer, backingList.toList())
        prefs.putString(key, jsonString)
    }

    /**
     * Adds the specified element to the end of this list and saves to preferences.
     * @param element the element to add
     * @return true if the element was added
     */
    override fun add(element: T): Boolean {
        val result = backingList.add(element)
        saveToPreferences()
        return result
    }

    /**
     * Inserts the specified element at the specified position in this list and saves to preferences.
     * @param index the index at which to insert the element
     * @param element the element to insert
     */
    override fun add(
        index: Int,
        element: T,
    ) {
        backingList.add(index, element)
        saveToPreferences()
    }

    /**
     * Adds all elements from the specified collection to the end of this list and saves to preferences.
     * @param elements the collection of elements to add
     * @return true if the list was modified
     */
    override fun addAll(elements: Collection<T>): Boolean {
        val result = backingList.addAll(elements)
        saveToPreferences()
        return result
    }

    /**
     * Inserts all elements from the specified collection at the specified position and saves to preferences.
     * @param index the index at which to insert the elements
     * @param elements the collection of elements to insert
     * @return true if the list was modified
     */
    override fun addAll(
        index: Int,
        elements: Collection<T>,
    ): Boolean {
        val result = backingList.addAll(index, elements)
        saveToPreferences()
        return result
    }

    /**
     * Replaces the element at the specified position with the specified element and saves to preferences.
     * @param index the index of the element to replace
     * @param element the new element
     * @return the element previously at the specified position
     */
    override fun set(
        index: Int,
        element: T,
    ): T {
        val result = backingList.set(index, element)
        saveToPreferences()
        return result
    }

    /**
     * Removes the first occurrence of the specified element from this list and saves to preferences.
     * @param element the element to remove
     * @return true if the element was removed
     */
    override fun remove(element: T): Boolean {
        val result = backingList.remove(element)
        saveToPreferences()
        return result
    }

    /**
     * Removes the element at the specified position in this list and saves to preferences.
     * @param index the index of the element to remove
     * @return the element that was removed
     */
    override fun removeAt(index: Int): T {
        val result = backingList.removeAt(index)
        saveToPreferences()
        return result
    }

    /**
     * Removes all elements from this list that are contained in the specified collection and saves to preferences.
     * @param elements the collection of elements to remove
     * @return true if the list was modified
     */
    override fun removeAll(elements: Collection<T>): Boolean {
        val result = backingList.removeAll(elements.toSet())
        saveToPreferences()
        return result
    }

    /**
     * Retains only the elements in this list that are contained in the specified collection and saves to preferences.
     * @param elements the collection of elements to retain
     * @return true if the list was modified
     */
    override fun retainAll(elements: Collection<T>): Boolean {
        val result = backingList.retainAll(elements.toSet())
        saveToPreferences()
        return result
    }

    /**
     * Removes all elements from this list and saves the empty state to preferences.
     */
    override fun clear() {
        backingList.clear()
        saveToPreferences()
    }

    /**
     * Clears the list and removes it from preferences entirely.
     */
    fun reset() {
        clear()
        prefs.remove(key)
    }

    /**
     * Checks if this list contains the specified element.
     * @param element the element to check for
     * @return true if the element is in the list
     */
    override fun contains(element: T): Boolean = backingList.contains(element)

    /**
     * Checks if this list contains all elements from the specified collection.
     * @param elements the collection of elements to check for
     * @return true if all elements are in the list
     */
    override fun containsAll(elements: Collection<T>): Boolean = backingList.containsAll(elements)

    /**
     * Returns the element at the specified position in this list.
     * @param index the index of the element to return
     * @return the element at the specified position
     */
    override fun get(index: Int): T = backingList[index]

    /**
     * Returns the index of the first occurrence of the specified element, or -1 if not found.
     * @param element the element to search for
     * @return the index of the element or -1
     */
    override fun indexOf(element: T): Int = backingList.indexOf(element)

    /**
     * Returns true if this list contains no elements.
     * @return true if the list is empty
     */
    override fun isEmpty(): Boolean = backingList.isEmpty()

    /**
     * Returns an iterator over the elements in this list.
     * @return an iterator
     */
    override fun iterator(): MutableIterator<T> = backingList.iterator()

    /**
     * Returns the index of the last occurrence of the specified element, or -1 if not found.
     * @param element the element to search for
     * @return the last index of the element or -1
     */
    override fun lastIndexOf(element: T): Int = backingList.lastIndexOf(element)

    /**
     * Returns a list iterator over the elements in this list.
     * @return a list iterator
     */
    override fun listIterator(): MutableListIterator<T> = backingList.listIterator()

    /**
     * Returns a list iterator starting at the specified position.
     * @param index the starting position
     * @return a list iterator
     */
    override fun listIterator(index: Int): MutableListIterator<T> = backingList.listIterator(index)

    /**
     * Returns a view of the portion of this list between the specified indices.
     * @param fromIndex the start index (inclusive)
     * @param toIndex the end index (exclusive)
     * @return a sublist view
     */
    override fun subList(
        fromIndex: Int,
        toIndex: Int,
    ): MutableList<T> = backingList.subList(fromIndex, toIndex)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false
        other as PersistentArrayList<*>
        return backingList == other.backingList
    }

    override fun hashCode(): Int = backingList.hashCode()
}
