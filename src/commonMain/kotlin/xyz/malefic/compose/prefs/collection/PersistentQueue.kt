package xyz.malefic.compose.prefs.collection

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import xyz.malefic.compose.prefs.Common
import xyz.malefic.compose.prefs.Preferences

/**
 * A persistent implementation of a Queue that saves its state to preferences.
 *
 * @param T the type of elements in this queue
 * @param key the key used to store the queue in preferences
 * @param serializer the KSerializer for type T
 * @param prefs the preferences instance used to store the queue
 */
class PersistentQueue<T>(
    private val key: String,
    private val serializer: KSerializer<T>,
    private val prefs: Preferences = Common.prefs,
) {
    private val json = Json { ignoreUnknownKeys = true }
    private val listSerializer = ListSerializer(serializer)
    private val backingList = mutableListOf<T>()

    val size: Int get() = backingList.size

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
     * Adds the specified element to the end of this queue and saves to preferences.
     * @param element the element to add
     * @return true if the element was added
     */
    fun add(element: T): Boolean {
        val result = backingList.add(element)
        saveToPreferences()
        return result
    }

    /**
     * Inserts the specified element at the front of this queue and saves to preferences.
     * @param element the element to add
     */
    fun addFirst(element: T) {
        backingList.add(0, element)
        saveToPreferences()
    }

    /**
     * Adds the specified element to the end of this queue and saves to preferences.
     * @param element the element to add
     */
    fun addLast(element: T) {
        backingList.add(element)
        saveToPreferences()
    }

    /**
     * Inserts the specified element into this queue and saves to preferences.
     * @param element the element to add
     * @return true if the element was added
     */
    fun offer(element: T): Boolean = add(element)

    /**
     * Retrieves and removes the head of this queue and saves to preferences.
     * @return the head of the queue
     * @throws NoSuchElementException if the queue is empty
     */
    fun remove(): T {
        val result = backingList.removeAt(0)
        saveToPreferences()
        return result
    }

    /**
     * Retrieves and removes the first element of this queue and saves to preferences.
     * @return the first element
     * @throws NoSuchElementException if the queue is empty
     */
    fun removeFirst(): T = remove()

    /**
     * Retrieves and removes the last element of this queue and saves to preferences.
     * @return the last element
     * @throws NoSuchElementException if the queue is empty
     */
    fun removeLast(): T {
        val result = backingList.removeAt(backingList.lastIndex)
        saveToPreferences()
        return result
    }

    /**
     * Retrieves and removes the head of this queue, or returns null if empty, and saves to preferences.
     * @return the head of the queue, or null if empty
     */
    fun poll(): T? {
        if (backingList.isEmpty()) return null
        val result = backingList.removeAt(0)
        saveToPreferences()
        return result
    }

    /**
     * Retrieves and removes the first element of this queue, or returns null if empty, and saves to preferences.
     * @return the first element, or null if empty
     */
    fun pollFirst(): T? = poll()

    /**
     * Retrieves and removes the last element of this queue, or returns null if empty, and saves to preferences.
     * @return the last element, or null if empty
     */
    fun pollLast(): T? {
        if (backingList.isEmpty()) return null
        val result = backingList.removeAt(backingList.lastIndex)
        saveToPreferences()
        return result
    }

    /**
     * Retrieves, but does not remove, the head of this queue, or returns null if empty.
     * @return the head of the queue, or null if empty
     */
    fun peek(): T? = backingList.firstOrNull()

    /**
     * Retrieves, but does not remove, the first element of this queue, or returns null if empty.
     * @return the first element, or null if empty
     */
    fun peekFirst(): T? = peek()

    /**
     * Retrieves, but does not remove, the last element of this queue, or returns null if empty.
     * @return the last element, or null if empty
     */
    fun peekLast(): T? = backingList.lastOrNull()

    /**
     * Retrieves, but does not remove, the head of this queue.
     * @return the head of the queue
     * @throws NoSuchElementException if the queue is empty
     */
    fun element(): T = backingList.first()

    /**
     * Removes all elements from this queue and saves the empty state to preferences.
     */
    fun clear() {
        backingList.clear()
        saveToPreferences()
    }

    /**
     * Clears the queue and removes it from preferences entirely.
     */
    fun reset() {
        clear()
        prefs.remove(key)
    }

    /**
     * Returns true if this queue contains no elements.
     * @return true if the queue is empty
     */
    fun isEmpty(): Boolean = backingList.isEmpty()

    /**
     * Checks if this queue contains the specified element.
     * @param element the element to check for
     * @return true if the element is in the queue
     */
    fun contains(element: T): Boolean = backingList.contains(element)

    /**
     * Returns a list containing all elements in this queue.
     * @return a list of elements
     */
    fun toList(): List<T> = backingList.toList()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false
        other as PersistentQueue<*>
        return backingList == other.backingList
    }

    override fun hashCode(): Int = backingList.hashCode()
}
