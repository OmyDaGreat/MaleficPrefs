package xyz.malefic.compose.prefs.delegate.nullable

import xyz.malefic.compose.prefs.Common
import xyz.malefic.compose.prefs.Preferences
import xyz.malefic.compose.prefs.delegate.PreferenceDelegate
import kotlin.reflect.KProperty

/**
 * A class that provides a delegate for storing and retrieving nullable long preferences.
 *
 * @property key The key for the preference.
 * @property defaultValue The default value for the preference.
 * @property prefs The Preferences instance used to store the preference.
 */
class NullableLongPreference(
    private val key: String,
    private val defaultValue: Long? = null,
    private val prefs: Preferences = Common.prefs,
) : PreferenceDelegate<Long?> {
    override operator fun getValue(
        thisRef: Any?,
        property: KProperty<*>,
    ): Long? {
        val stored = prefs.getString(key, null)
        return stored?.toLongOrNull() ?: defaultValue
    }

    override operator fun setValue(
        thisRef: Any?,
        property: KProperty<*>,
        value: Long?,
    ) {
        if (value == null) {
            prefs.remove(key)
        } else {
            prefs.putLong(key, value)
        }
    }
}
