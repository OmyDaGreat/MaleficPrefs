package xyz.malefic.compose.prefs.collection

import kotlinx.serialization.builtins.serializer
import xyz.malefic.compose.prefs.Common.prefs
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PersistentQueueTest {
    @BeforeTest
    fun setUp() {
        prefs.clear()
    }

    @Test
    fun `should load from preferences`() {
        val queue = PersistentQueue("testKey", String.serializer())
        queue.offer("item1")
        queue.offer("item2")

        val newQueue = PersistentQueue("testKey", String.serializer())
        assertEquals(queue, newQueue, "Expected queue to be loaded from preferences")
    }

    @Test
    fun `should save to preferences on offer`() {
        val queue = PersistentQueue("testKey", String.serializer())
        queue.offer("item1")

        val newQueue = PersistentQueue("testKey", String.serializer())
        assertTrue(newQueue.contains("item1"), "Expected item to be saved to preferences")
    }

    @Test
    fun `should save to preferences on poll`() {
        val queue = PersistentQueue("testKey", String.serializer())
        queue.offer("item1")
        queue.poll()

        val newQueue = PersistentQueue("testKey", String.serializer())
        assertTrue(!newQueue.contains("item1"), "Expected item to be removed from preferences")
    }

    @Test
    fun `should reset preferences`() {
        val queue = PersistentQueue("testKey", String.serializer())
        queue.offer("item1")
        queue.reset()

        val newQueue = PersistentQueue("testKey", String.serializer())
        assertTrue(newQueue.isEmpty(), "Expected queue to be reset in preferences")
    }
}
