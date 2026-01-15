package xyz.malefic.compose.prefs.delegate.nullable

import xyz.malefic.compose.prefs.Common
import xyz.malefic.compose.prefs.Preferences
import xyz.malefic.compose.prefs.delegate.PreferenceDelegate
import kotlin.reflect.KProperty

/**
 * A class that provides a delegate for storing and retrieving nullable float preferences.
 *
 * @property key The key for the preference.
 * @property defaultValue The default value for the preference.
 * @property prefs The Preferences instance used to store the preference.
 */
class NullableFloatPreference(
    private val key: String,
    private val defaultValue: Float? = null,
    private val prefs: Preferences = Common.prefs,
) : PreferenceDelegate<Float?> {
    override operator fun getValue(
        thisRef: Any?,
        property: KProperty<*>,
    ): Float? {
        val stored = prefs.getString(key, null)
        return stored?.toFloatOrNull() ?: defaultValue
    }

    override operator fun setValue(
        thisRef: Any?,
        property: KProperty<*>,
        value: Float?,
    ) {
        if (value == null) {
            prefs.remove(key)
        } else {
            prefs.putFloat(key, value)
        }
    }
}
