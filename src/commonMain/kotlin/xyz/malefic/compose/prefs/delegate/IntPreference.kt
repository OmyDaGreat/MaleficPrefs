package xyz.malefic.compose.prefs.delegate

import xyz.malefic.compose.prefs.Common
import xyz.malefic.compose.prefs.Preferences
import kotlin.reflect.KProperty

/**
 * A class that provides a delegate for storing and retrieving int preferences.
 *
 * @property key The key for the preference.
 * @property defaultValue The default value for the preference.
 * @property prefs The Preferences instance used to store the preference.
 */
class IntPreference(
    private val key: String,
    private val defaultValue: Int = 0,
    private val prefs: Preferences = Common.prefs,
) : PreferenceDelegate<Int> {
    override operator fun getValue(
        thisRef: Any?,
        property: KProperty<*>,
    ): Int = prefs.getInt(key, defaultValue)

    override operator fun setValue(
        thisRef: Any?,
        property: KProperty<*>,
        value: Int,
    ) {
        prefs.putInt(key, value)
    }
}
