package xyz.malefic.compose.prefs.delegate.nullable

import xyz.malefic.compose.prefs.Common
import xyz.malefic.compose.prefs.Preferences
import xyz.malefic.compose.prefs.delegate.PreferenceDelegate
import kotlin.reflect.KProperty

/**
 * A class that provides a delegate for storing and retrieving nullable int preferences.
 *
 * @property key The key for the preference.
 * @property defaultValue The default value for the preference.
 * @property prefs The Preferences instance used to store the preference.
 */
class NullableIntPreference(
    private val key: String,
    private val defaultValue: Int? = null,
    private val prefs: Preferences = Common.prefs,
) : PreferenceDelegate<Int?> {
    override operator fun getValue(
        thisRef: Any?,
        property: KProperty<*>,
    ): Int? {
        val stored = prefs.getString(key, null)
        return stored?.toIntOrNull() ?: defaultValue
    }

    override operator fun setValue(
        thisRef: Any?,
        property: KProperty<*>,
        value: Int?,
    ) {
        if (value == null) {
            prefs.remove(key)
        } else {
            prefs.putInt(key, value)
        }
    }
}
