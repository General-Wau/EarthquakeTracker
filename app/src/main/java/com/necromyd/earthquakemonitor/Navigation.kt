package com.necromyd.earthquakemonitor

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.necromyd.earthquakemonitor.viewmodel.EarthquakeViewModel
import com.necromyd.earthquakemonitor.Screen.*
import com.necromyd.earthquakemonitor.view.*

@Composable
fun Navigation(viewModel: EarthquakeViewModel) {
    val navController = rememberNavController()
    val windowSize = rememberWindowSize()
    NavHost(navController = navController, startDestination = PrimaryScreen.route) {
        composable(PrimaryScreen.route) {
//            Screen.MainScreen(viewModel)
        }
//        composable(CustomQueryScreen.route) {
//            CustomQueryScreenComposable()
//        }
//        composable(SettingsScreen.route) {
//            SettingsScreenComposable()
//        }
    }
}