package com.nin0dev.vendroid.ui.screens

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nin0dev.vendroid.ui.theme.VendroidTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OptionsScreen() {
	val navController = rememberNavController()
	val onBackPressed: () -> Unit = {
		navController.popBackStack()
	}
	val enterTransition: @JvmSuppressWildcards
	AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition? = {
		slideIntoContainer(
			animationSpec = spring(
				dampingRatio = Spring.DampingRatioNoBouncy,
				stiffness = Spring.StiffnessMediumLow
			),
			towards = AnimatedContentTransitionScope.SlideDirection.Start
		)
	}
	val exitTransition: @JvmSuppressWildcards
	AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition? = {
		slideOutOfContainer(
			animationSpec = spring(
				dampingRatio = Spring.DampingRatioNoBouncy,
				stiffness = Spring.StiffnessMediumLow
			),
			towards = AnimatedContentTransitionScope.SlideDirection.End
		)
	}


	NavHost(
		navController, startDestination = "home",
		enterTransition = { EnterTransition.None },
		exitTransition = { ExitTransition.None },
	) {
		composable("home") {
			OptionsHomeScreen { route ->
				navController.navigate(route)
			}
		}
		composable(
			"mods",
			enterTransition = enterTransition,
			exitTransition = exitTransition
		) {
			OptionsModsScreen(onBackPressed)
		}
		composable(
			"vde_settings",
			enterTransition = enterTransition,
			exitTransition = exitTransition
		) {
			OptionsVDEScreen(onBackPressed)
		}
		composable(
			"vde_autoupdate",
			enterTransition = enterTransition,
			exitTransition = exitTransition
		) {
			OptionsAppUpdater(onBackPressed)
		}
		composable(
			"vencord_autoupdate",
			enterTransition = enterTransition,
			exitTransition = exitTransition
		) {
			OptionsModUpdater(onBackPressed)
		}
	}
}

@Composable
@Preview(
	showBackground = true,
	showSystemUi = true
)
private fun Preview() {
	VendroidTheme(true) {
		OptionsScreen()
	}
}
