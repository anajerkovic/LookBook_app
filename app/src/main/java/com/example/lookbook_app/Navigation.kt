package com.example.lookbook_app

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.lookbook_app.data.OutfitViewModel
import com.example.lookbook_app.ui.theme.*

object Routes {
    const val SCREEN_ALL_OUTFITS = "outfitList"
    const val SCREEN_OUTFIT_DETAILS = "outfitDetails/{outfitId}"
    const val SCREEN_ADD_OUTFIT = "addOutfit"

    fun getOutfitDetailsPath(outfitId: Int?): String {
        return if (outfitId != null && outfitId != -1) {
            "outfitDetails/$outfitId"
        } else {
            "outfitDetails/0"
        }
    }
}

@Composable
fun NavigationController(viewModel: OutfitViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.SCREEN_ALL_OUTFITS
    ) {
        composable(Routes.SCREEN_ALL_OUTFITS) {
            OutfitScreen(viewModel = viewModel, navController = navController, modifier = Modifier)
        }

        composable(
            route = Routes.SCREEN_OUTFIT_DETAILS,
            arguments = listOf(
                navArgument("outfitId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val outfitId = backStackEntry.arguments?.getInt("outfitId") ?: 0
            OutfitDetailsScreen(outfitId = outfitId, navController = navController)
        }

        composable(Routes.SCREEN_ADD_OUTFIT) {
            AddOutfitScreen(navController = navController)
        }
    }
}
