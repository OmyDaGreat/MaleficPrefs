package xyz.malefic.compose.prefs.delegate

import xyz.malefic.compose.prefs.Common
import xyz.malefic.compose.prefs.Preferences
import kotlin.reflect.KProperty

/**
 * A class that provides a delegate for storing and retrieving long preferences.
 *
 * @property key The key for the preference.
 * @property defaultValue The default value for the preference.
 * @property prefs The Preferences instance used to store the preference.
 */
class LongPreference(
    private val key: String,
    private val defaultValue: Long = 0L,
    private val prefs: Preferences = Common.prefs,
) : PreferenceDelegate<Long> {
    override operator fun getValue(
        thisRef: Any?,
        property: KProperty<*>,
    ): Long = prefs.getLong(key, defaultValue)

    override operator fun setValue(
        thisRef: Any?,
        property: KProperty<*>,
        value: Long,
    ) {
        prefs.putLong(key, value)
    }
}
