package xyz.malefic.compose.prefs.collection

import kotlinx.serialization.builtins.serializer
import xyz.malefic.compose.prefs.Common.prefs
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class PersistentHashMapTest {
    private lateinit var map: PersistentHashMap<String, String>

    @BeforeTest
    fun setUp() {
        prefs.clear()
        map = PersistentHashMap("testMap", String.serializer(), String.serializer())
    }

    @Test
    fun putAddsEntryToMapAndSavesToPreferences() {
        map["key1"] = "value1"
        assertEquals("value1", map["key1"])
    }

    @Test
    fun removeDeletesEntryFromMapAndSavesToPreferences() {
        map["key1"] = "value1"
        map.remove("key1")
        assertNull(map["key1"])
    }

    @Test
    fun resetClearsMapAndRemovesFromPreferences() {
        map["key1"] = "value1"
        map.reset()
        assertTrue(map.isEmpty())
    }

    @Test
    fun putAllAddsAllEntriesToMapAndSavesToPreferences() {
        val entries = mapOf("key1" to "value1", "key2" to "value2")
        map.putAll(entries)
        assertEquals("value1", map["key1"])
        assertEquals("value2", map["key2"])
    }
}
