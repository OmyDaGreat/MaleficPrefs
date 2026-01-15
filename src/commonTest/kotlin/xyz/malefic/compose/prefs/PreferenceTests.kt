package xyz.malefic.compose.prefs

import xyz.malefic.compose.prefs.delegate.BooleanPreference
import xyz.malefic.compose.prefs.delegate.ByteArrayPreference
import xyz.malefic.compose.prefs.delegate.DoublePreference
import xyz.malefic.compose.prefs.delegate.FloatPreference
import xyz.malefic.compose.prefs.delegate.IntPreference
import xyz.malefic.compose.prefs.delegate.LongPreference
import xyz.malefic.compose.prefs.delegate.SerializablePreference
import xyz.malefic.compose.prefs.delegate.StringPreference
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PreferenceTests {
    private lateinit var prefs: Preferences

    @BeforeTest
    fun setup() {
        prefs = Common.prefs
        prefs.clear()
    }

    @Test
    fun `BooleanPreference should return default value if not set`() {
        val testObject by BooleanPreference("booleanKey", defaultValue = true)
        assertTrue(testObject, "Expected default value to be returned when not set")
    }

    @Test
    fun `BooleanPreference should store and retrieve value`() {
        var testObject by BooleanPreference("booleanKey", defaultValue = false)
        testObject = true
        assertTrue(testObject, "Expected value to be stored and retrieved correctly")
    }

    @Test
    fun `IntPreference should return default value if not set`() {
        val testObject by IntPreference("intKey", defaultValue = 42)
        assertEquals(42, testObject, "Expected default value to be returned when not set")
    }

    @Test
    fun `IntPreference should store and retrieve value`() {
        var testObject by IntPreference("intKey", defaultValue = 0)
        testObject = 100
        assertEquals(100, testObject, "Expected value to be stored and retrieved correctly")
    }

    @Test
    fun `StringPreference should return default value if not set`() {
        val testObject by StringPreference("stringKey", defaultValue = "default")
        assertEquals("default", testObject, "Expected default value to be returned when not set")
    }

    @Test
    fun `StringPreference should store and retrieve value`() {
        var testObject by StringPreference("stringKey", defaultValue = null.toString())
        testObject = "Test String"
        assertEquals("Test String", testObject, "Expected value to be stored and retrieved correctly")
    }

    @Test
    fun `ByteArrayPreference should return default value if not set`() {
        val testObject by ByteArrayPreference("byteArrayKey", defaultValue = byteArrayOf(1, 2, 3))
        assertContentEquals(
            byteArrayOf(1, 2, 3),
            testObject,
            "Expected default value to be returned when not set",
        )
    }

    @Test
    fun `ByteArrayPreference should store and retrieve value`() {
        var testObject by ByteArrayPreference("byteArrayKey", defaultValue = byteArrayOf())
        testObject = byteArrayOf(4, 5, 6)
        assertContentEquals(
            byteArrayOf(4, 5, 6),
            testObject,
            "Expected value to be stored and retrieved correctly",
        )
    }

    @Test
    fun `DoublePreference should return default value if not set`() {
        val testObject by DoublePreference("doubleKey", defaultValue = 42.0)
        assertEquals(42.0, testObject, "Expected default value to be returned when not set")
    }

    @Test
    fun `DoublePreference should store and retrieve value`() {
        var testObject by DoublePreference("doubleKey", defaultValue = 0.0)
        testObject = 100.0
        assertEquals(100.0, testObject, "Expected value to be stored and retrieved correctly")
    }

    @Test
    fun `FloatPreference should return default value if not set`() {
        val testObject by FloatPreference("floatKey", defaultValue = 42.0f)
        assertEquals(42.0f, testObject, "Expected default value to be returned when not set")
    }

    @Test
    fun `FloatPreference should store and retrieve value`() {
        var testObject by FloatPreference("floatKey", defaultValue = 0.0f)
        testObject = 100.0f
        assertEquals(100.0f, testObject, "Expected value to be stored and retrieved correctly")
    }

    @Test
    fun `LongPreference should return default value if not set`() {
        val testObject by LongPreference("longKey", defaultValue = 42L)
        assertEquals(42L, testObject, "Expected default value to be returned when not set")
    }

    @Test
    fun `LongPreference should store and retrieve value`() {
        var testObject by LongPreference("longKey", defaultValue = 0L)
        testObject = 100L
        assertEquals(100L, testObject, "Expected value to be stored and retrieved correctly")
    }

    /**
     * A data class used exclusively for testing the GenericPreference class.
     *
     * @property name The name of the person.
     * @property age The age of the person.
     */
    @kotlinx.serialization.Serializable
    internal data class Person(
        val name: String,
        val age: Int,
    )

    @Test
    fun `GenericPreference should return default value if not set`() {
        val defaultValue = Person("John Doe", 30)
        val testObject by SerializablePreference("genericKey", Person.serializer(), defaultValue)
        assertEquals(defaultValue, testObject, "Expected default value to be returned when not set")
    }

    @Test
    fun `GenericPreference should store and retrieve value`() {
        val defaultValue = Person("Jane Doe", 25)
        var testObject by SerializablePreference("genericKey", Person.serializer(), defaultValue)
        val newValue = Person("Alice", 28)
        testObject = newValue
        assertEquals(newValue, testObject, "Expected value to be stored and retrieved correctly")
    }
}
