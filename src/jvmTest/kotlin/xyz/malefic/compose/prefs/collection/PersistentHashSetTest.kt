package xyz.malefic.compose.prefs.collection

import kotlinx.serialization.builtins.serializer
import xyz.malefic.compose.prefs.Common.prefs
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PersistentHashSetTest {
    @BeforeTest
    fun setUp() {
        prefs.clear()
    }

    @Test
    fun `should load from preferences`() {
        val set = PersistentHashSet("testKey", String.serializer())
        set.add("item1")
        set.add("item2")

        val newSet = PersistentHashSet("testKey", String.serializer())
        assertEquals(set, newSet, "Expected set to be loaded from preferences")
    }

    @Test
    fun `should save to preferences on add`() {
        val set = PersistentHashSet("testKey", String.serializer())
        set.add("item1")

        val newSet = PersistentHashSet("testKey", String.serializer())
        assertTrue(newSet.contains("item1"), "Expected item to be saved to preferences")
    }

    @Test
    fun `should save to preferences on remove`() {
        val set = PersistentHashSet("testKey", String.serializer())
        set.add("item1")
        set.remove("item1")

        val newSet = PersistentHashSet("testKey", String.serializer())
        assertTrue(!newSet.contains("item1"), "Expected item to be removed from preferences")
    }

    @Test
    fun `should reset preferences`() {
        val set = PersistentHashSet("testKey", String.serializer())
        set.add("item1")
        set.reset()

        val newSet = PersistentHashSet("testKey", String.serializer())
        assertTrue(newSet.isEmpty(), "Expected set to be reset in preferences")
    }
}
