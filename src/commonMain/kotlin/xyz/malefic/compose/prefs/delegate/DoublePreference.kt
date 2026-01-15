package xyz.malefic.compose.prefs.delegate

import xyz.malefic.compose.prefs.Common
import xyz.malefic.compose.prefs.Preferences
import kotlin.reflect.KProperty

/**
 * A class that provides a delegate for storing and retrieving double preferences.
 *
 * @property key The key for the preference.
 * @property defaultValue The default value for the preference.
 * @property prefs The Preferences instance used to store the preference.
 */
class DoublePreference(
    private val key: String,
    private val defaultValue: Double = 0.0,
    private val prefs: Preferences = Common.prefs,
) : PreferenceDelegate<Double> {
    override operator fun getValue(
        thisRef: Any?,
        property: KProperty<*>,
    ): Double = prefs.getDouble(key, defaultValue)

    override operator fun setValue(
        thisRef: Any?,
        property: KProperty<*>,
        value: Double,
    ) {
        prefs.putDouble(key, value)
    }
}
