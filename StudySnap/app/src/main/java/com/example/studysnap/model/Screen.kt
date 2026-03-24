package com.example.studysnap.model

/**
 * Sealed class defining all navigation routes in the SnapStudy app.
 */
sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Scanner : Screen("scanner")
    data object Flashcards : Screen("flashcards")
    data object Quiz : Screen("quiz")
}
