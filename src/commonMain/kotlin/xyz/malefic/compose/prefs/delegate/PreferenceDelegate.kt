package xyz.malefic.compose.prefs.delegate

import kotlin.reflect.KProperty

/**
 * Interface for a preference delegate that provides methods to get and set preference values.
 *
 * @param T The type of the preference value.
 */
interface PreferenceDelegate<T> {
    /**
     * Retrieves the value of the preference.
     *
     * @param thisRef The reference to the object that contains the property.
     * @param property The metadata for the property.
     * @return The value of the preference.
     */
    operator fun getValue(
        thisRef: Any?,
        property: KProperty<*>,
    ): T

    /**
     * Sets the value of the preference.
     *
     * @param thisRef The reference to the object that contains the property.
     * @param property The metadata for the property.
     * @param value The value to set for the preference.
     */
    operator fun setValue(
        thisRef: Any?,
        property: KProperty<*>,
        value: T,
    )
}
