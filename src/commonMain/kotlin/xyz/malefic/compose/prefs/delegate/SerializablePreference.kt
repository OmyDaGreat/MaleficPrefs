package xyz.malefic.compose.prefs.delegate

import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import xyz.malefic.compose.prefs.Common
import xyz.malefic.compose.prefs.Preferences
import kotlin.reflect.KProperty

/**
 * A class that provides a delegate for storing and retrieving generic serializable preferences.
 *
 * @property key The key for the preference.
 * @property serializer The KSerializer for type T.
 * @property defaultValue The default value for the preference.
 * @property prefs The Preferences instance used to store the preference.
 */
class SerializablePreference<T>(
    private val key: String,
    private val serializer: KSerializer<T>,
    private val defaultValue: T,
    private val prefs: Preferences = Common.prefs,
) : PreferenceDelegate<T> {
    private val json = Json { ignoreUnknownKeys = true }

    override operator fun getValue(
        thisRef: Any?,
        property: KProperty<*>,
    ): T {
        val jsonString = prefs.getString(key, null) ?: return defaultValue
        return try {
            json.decodeFromString(serializer, jsonString)
        } catch (e: Exception) {
            defaultValue
        }
    }

    override operator fun setValue(
        thisRef: Any?,
        property: KProperty<*>,
        value: T,
    ) {
        val jsonString = json.encodeToString(serializer, value)
        prefs.putString(key, jsonString)
    }
}
