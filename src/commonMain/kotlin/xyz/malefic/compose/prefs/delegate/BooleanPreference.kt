package xyz.malefic.compose.prefs.delegate

import xyz.malefic.compose.prefs.Common
import xyz.malefic.compose.prefs.Preferences
import kotlin.reflect.KProperty

/**
 * A class that provides a delegate for storing and retrieving boolean preferences.
 *
 * @property key The key for the preference.
 * @property defaultValue The default value for the preference.
 * @property prefs The Preferences instance used to store the preference.
 */
class BooleanPreference(
    private val key: String,
    private val defaultValue: Boolean = false,
    private val prefs: Preferences = Common.prefs,
) : PreferenceDelegate<Boolean> {
    override operator fun getValue(
        thisRef: Any?,
        property: KProperty<*>,
    ): Boolean = prefs.getBoolean(key, defaultValue)

    override operator fun setValue(
        thisRef: Any?,
        property: KProperty<*>,
        value: Boolean,
    ) {
        prefs.putBoolean(key, value)
    }
}
