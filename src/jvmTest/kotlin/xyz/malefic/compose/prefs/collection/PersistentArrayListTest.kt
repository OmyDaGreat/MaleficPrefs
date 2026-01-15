package xyz.malefic.compose.prefs.collection

import kotlinx.serialization.builtins.serializer
import xyz.malefic.compose.prefs.Common.prefs
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PersistentArrayListTest {
    @BeforeTest
    fun setUp() {
        prefs.clear()
    }

    @Test
    fun `should load from preferences`() {
        val list = PersistentArrayList("testKey", String.serializer())
        list.add("item1")
        list.add("item2")

        val newList = PersistentArrayList("testKey", String.serializer())
        assertEquals(list, newList, "Expected list to be loaded from preferences")
    }

    @Test
    fun `should save to preferences on add`() {
        val list = PersistentArrayList("testKey", String.serializer())
        list.add("item1")

        val newList = PersistentArrayList("testKey", String.serializer())
        assertTrue(newList.contains("item1"), "Expected item to be saved to preferences")
    }

    @Test
    fun `should save to preferences on remove`() {
        val list = PersistentArrayList("testKey", String.serializer())
        list.add("item1")
        list.remove("item1")

        val newList = PersistentArrayList("testKey", String.serializer())
        assertTrue(!newList.contains("item1"), "Expected item to be removed from preferences")
    }

    @Test
    fun `should reset preferences`() {
        val list = PersistentArrayList("testKey", String.serializer())
        list.add("item1")
        list.reset()

        val newList = PersistentArrayList("testKey", String.serializer())
        assertTrue(newList.isEmpty(), "Expected list to be reset in preferences")
    }
}
