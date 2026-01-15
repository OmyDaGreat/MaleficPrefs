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

    override fun add(element: T): Boolean {
        val result = backingList.add(element)
        saveToPreferences()
        return result
    }

    override fun add(
        index: Int,
        element: T,
    ) {
        backingList.add(index, element)
        saveToPreferences()
    }

    override fun addAll(elements: Collection<T>): Boolean {
        val result = backingList.addAll(elements)
        saveToPreferences()
        return result
    }

    override fun addAll(
        index: Int,
        elements: Collection<T>,
    ): Boolean {
        val result = backingList.addAll(index, elements)
        saveToPreferences()
        return result
    }

    override fun set(
        index: Int,
        element: T,
    ): T {
        val result = backingList.set(index, element)
        saveToPreferences()
        return result
    }

    override fun remove(element: T): Boolean {
        val result = backingList.remove(element)
        saveToPreferences()
        return result
    }

    override fun removeAt(index: Int): T {
        val result = backingList.removeAt(index)
        saveToPreferences()
        return result
    }

    override fun removeAll(elements: Collection<T>): Boolean {
        val result = backingList.removeAll(elements.toSet())
        saveToPreferences()
        return result
    }

    override fun retainAll(elements: Collection<T>): Boolean {
        val result = backingList.retainAll(elements.toSet())
        saveToPreferences()
        return result
    }

    override fun clear() {
        backingList.clear()
    }

    fun reset() {
        clear()
        prefs.remove(key)
    }

    override fun contains(element: T): Boolean = backingList.contains(element)

    override fun containsAll(elements: Collection<T>): Boolean = backingList.containsAll(elements)

    override fun get(index: Int): T = backingList.get(index)

    override fun indexOf(element: T): Int = backingList.indexOf(element)

    override fun isEmpty(): Boolean = backingList.isEmpty()

    override fun iterator(): MutableIterator<T> = backingList.iterator()

    override fun lastIndexOf(element: T): Int = backingList.lastIndexOf(element)

    override fun listIterator(): MutableListIterator<T> = backingList.listIterator()

    override fun listIterator(index: Int): MutableListIterator<T> = backingList.listIterator(index)

    override fun subList(
        fromIndex: Int,
        toIndex: Int,
    ): MutableList<T> = backingList.subList(fromIndex, toIndex)
}
