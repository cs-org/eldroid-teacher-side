package com.example.eldroid_teacher_side.util

import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder

/**
 * Prevents double-click navigation crashes by enforcing singleTop
 * and ignoring requests to navigate to the screen we are already on.
 */
fun NavController.navigateSafe(
    route: String,
    builder: (NavOptionsBuilder.() -> Unit)? = null
) {
    // Prevent navigating to the exact same route if we are already there
    if (this.currentDestination?.route != route) {
        this.navigate(route) {
            launchSingleTop = true // Prevents multiple copies on the back stack
            builder?.invoke(this)  // Applies any additional navigation options (like popUpTo)
        }
    }
}

fun NavController.popBackStackSafe() {
    // Check if there is a previous screen to return to
    val hasPreviousScreen = this.previousBackStackEntry != null

    // Check if the current screen is active (not already in the middle of closing)
    val isCurrentScreenActive = this.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED

    if (hasPreviousScreen && isCurrentScreenActive) {
        this.popBackStack()
    }
}

fun NavController.navigateUpSafe() {
    val isCurrentScreenActive = this.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED

    if (isCurrentScreenActive) {
        this.navigateUp()
    }
}