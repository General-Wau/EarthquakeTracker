package com.necromyd.earthquakemonitor

sealed class Screen(val route: String) {
    object MainScreen : Screen("MainScreenRoute")
    object CustomQueryScreen : Screen("CustomQueryScreenRoute")
    object MapScreen : Screen("MapScreenRoute")
    object SettingsScreen : Screen("SettingsScreenRoute")
}