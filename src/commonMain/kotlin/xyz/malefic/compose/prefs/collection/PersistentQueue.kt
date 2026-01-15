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

    fun add(element: T): Boolean {
        val result = backingList.add(element)
        saveToPreferences()
        return result
    }

    fun addFirst(element: T) {
        backingList.add(0, element)
        saveToPreferences()
    }

    fun addLast(element: T) {
        backingList.add(element)
        saveToPreferences()
    }

    fun offer(element: T): Boolean = add(element)

    fun remove(): T {
        val result = backingList.removeAt(0)
        saveToPreferences()
        return result
    }

    fun removeFirst(): T = remove()

    fun removeLast(): T {
        val result = backingList.removeAt(backingList.lastIndex)
        saveToPreferences()
        return result
    }

    fun poll(): T? {
        if (backingList.isEmpty()) return null
        val result = backingList.removeAt(0)
        saveToPreferences()
        return result
    }

    fun pollFirst(): T? = poll()

    fun pollLast(): T? {
        if (backingList.isEmpty()) return null
        val result = backingList.removeAt(backingList.lastIndex)
        saveToPreferences()
        return result
    }

    fun peek(): T? = backingList.firstOrNull()

    fun peekFirst(): T? = peek()

    fun peekLast(): T? = backingList.lastOrNull()

    fun element(): T = backingList.first()

    fun clear() {
        backingList.clear()
    }

    fun reset() {
        clear()
        prefs.remove(key)
    }

    fun isEmpty(): Boolean = backingList.isEmpty()

    fun contains(element: T): Boolean = backingList.contains(element)

    fun toList(): List<T> = backingList.toList()
}
