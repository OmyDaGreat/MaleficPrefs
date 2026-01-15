package xyz.malefic.compose.prefs

import xyz.malefic.compose.prefs.delegate.nullable.NullableDoublePreference
import xyz.malefic.compose.prefs.delegate.nullable.NullableFloatPreference
import xyz.malefic.compose.prefs.delegate.nullable.NullableIntPreference
import xyz.malefic.compose.prefs.delegate.nullable.NullableLongPreference
import xyz.malefic.compose.prefs.delegate.nullable.NullableStringPreference
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class NullablePreferenceTests {
    private lateinit var prefs: Preferences

    @BeforeTest
    fun setup() {
        prefs = Common.prefs
        prefs.clear()
    }

    @Test
    fun `NullableIntPreference should return default value if not set`() {
        val testObject by NullableIntPreference("intKey", defaultValue = 42)
        assertEquals(42, testObject, "Expected default value to be returned when not set")
    }

    @Test
    fun `NullableIntPreference should store and retrieve value`() {
        var testObject by NullableIntPreference("intKey", defaultValue = 0)
        testObject = 100
        assertEquals(100, testObject, "Expected value to be stored and retrieved correctly")
    }

    @Test
    fun `NullableIntPreference should handle null value`() {
        var testObject by NullableIntPreference("intKey", defaultValue = 42)
        testObject = null
        assertEquals(42, testObject, "Expected value to be default value")
    }

    @Test
    fun `NullableDoublePreference should return default value if not set`() {
        val testObject by NullableDoublePreference("doubleKey", defaultValue = 42.0)
        assertEquals(42.0, testObject, "Expected default value to be returned when not set")
    }

    @Test
    fun `NullableDoublePreference should store and retrieve value`() {
        var testObject by NullableDoublePreference("doubleKey", defaultValue = 0.0)
        testObject = 100.0
        assertEquals(100.0, testObject, "Expected value to be stored and retrieved correctly")
    }

    @Test
    fun `NullableDoublePreference should handle null value`() {
        var testObject by NullableDoublePreference("doubleKey", defaultValue = 42.0)
        testObject = null
        assertEquals(42.0, testObject, "Expected value to be default value")
    }

    @Test
    fun `NullableStringPreference should return default value if not set`() {
        val testObject by NullableStringPreference("stringKey", defaultValue = "default")
        assertEquals("default", testObject, "Expected default value to be returned when not set")
    }

    @Test
    fun `NullableStringPreference should store and retrieve value`() {
        var testObject by NullableStringPreference("stringKey", defaultValue = null)
        testObject = "Test String"
        assertEquals("Test String", testObject, "Expected value to be stored and retrieved correctly")
    }

    @Test
    fun `NullableStringPreference should handle null value`() {
        var testObject by NullableStringPreference("stringKey", defaultValue = "default")
        testObject = null
        assertEquals("default", testObject, "Expected value to be default value")
    }

    @Test
    fun `NullableStringPreference should handle null value and default`() {
        var testObject by NullableStringPreference("stringKey", defaultValue = null)
        testObject = null
        assertEquals(null, testObject, "Expected value to be default value")
    }

    @Test
    fun `NullableFloatPreference should return default value if not set`() {
        val testObject by NullableFloatPreference("floatKey", defaultValue = 42.0f)
        assertEquals(42.0f, testObject, "Expected default value to be returned when not set")
    }

    @Test
    fun `NullableFloatPreference should store and retrieve value`() {
        var testObject by NullableFloatPreference("floatKey", defaultValue = 0.0f)
        testObject = 100.0f
        assertEquals(100.0f, testObject, "Expected value to be stored and retrieved correctly")
    }

    @Test
    fun `NullableFloatPreference should handle null value`() {
        var testObject by NullableFloatPreference("floatKey", defaultValue = 42.0f)
        testObject = null
        assertEquals(42f, testObject, "Expected value to be default value")
    }

    @Test
    fun `NullableLongPreference should return default value if not set`() {
        val testObject by NullableLongPreference("longKey", defaultValue = 42L)
        assertEquals(42L, testObject, "Expected default value to be returned when not set")
    }

    @Test
    fun `NullableLongPreference should store and retrieve value`() {
        var testObject by NullableLongPreference("longKey", defaultValue = 0L)
        testObject = 100L
        assertEquals(100L, testObject, "Expected value to be stored and retrieved correctly")
    }

    @Test
    fun `NullableLongPreference should handle null value`() {
        var testObject by NullableLongPreference("longKey", defaultValue = 42L)
        testObject = null
        assertEquals(42L, testObject, "Expected value to be default value")
    }
}
