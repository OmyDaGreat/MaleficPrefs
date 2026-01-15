package xyz.malefic.compose.prefs.delegate

import xyz.malefic.compose.prefs.Common
import xyz.malefic.compose.prefs.Preferences
import kotlin.reflect.KProperty

/**
 * A class that provides a delegate for storing and retrieving string preferences.
 *
 * @property key The key for the preference.
 * @property defaultValue The default value for the preference.
 * @property prefs The Preferences instance used to store the preference.
 */
class StringPreference(
    private val key: String,
    private val defaultValue: String = "",
    private val prefs: Preferences = Common.prefs,
) : PreferenceDelegate<String> {
    override operator fun getValue(
        thisRef: Any?,
        property: KProperty<*>,
    ): String = prefs.getString(key, defaultValue) ?: defaultValue

    override operator fun setValue(
        thisRef: Any?,
        property: KProperty<*>,
        value: String,
    ) {
        prefs.putString(key, value)
    }
}
