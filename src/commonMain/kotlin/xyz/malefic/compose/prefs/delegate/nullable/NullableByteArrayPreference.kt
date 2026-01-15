package xyz.malefic.compose.prefs.delegate.nullable

import xyz.malefic.compose.prefs.Common
import xyz.malefic.compose.prefs.Preferences
import xyz.malefic.compose.prefs.delegate.PreferenceDelegate
import kotlin.reflect.KProperty

/**
 * A class that provides a delegate for storing and retrieving nullable byte array preferences.
 *
 * @property key The key for the preference.
 * @property defaultValue The default value for the preference.
 * @property prefs The Preferences instance used to store the preference.
 */
class NullableByteArrayPreference(
    private val key: String,
    private val defaultValue: ByteArray? = null,
    private val prefs: Preferences = Common.prefs,
) : PreferenceDelegate<ByteArray?> {
    override operator fun getValue(
        thisRef: Any?,
        property: KProperty<*>,
    ): ByteArray? = prefs.getByteArray(key, defaultValue)

    override operator fun setValue(
        thisRef: Any?,
        property: KProperty<*>,
        value: ByteArray?,
    ) {
        if (value == null) {
            prefs.remove(key)
        } else {
            prefs.putByteArray(key, value)
        }
    }
}
