package xyz.malefic.compose.prefs

/**
 * Singleton object for managing preferences.
 *
 * This object holds shared resources or utilities for managing user-specific preference data.
 * It provides a centralized place for static-like members, allowing them to be accessed globally
 * without needing an instance.
 *
 * The `Common` object is particularly useful for defining constants, utility functions, or
 * shared instances like `prefs`, which can be accessed directly via `Common.prefs`.
 */
object Common {
    /**
     * A `Preferences` instance that is initialized to the user node with a default node name.
     */
    var prefs: Preferences = Preferences.userNode("xyz.malefic.compose.prefs.Common")
}
