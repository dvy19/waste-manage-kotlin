package com.example.wastewar.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.wastewar.Screens
import com.example.wastewar.ui.user.AiSuggestionScreen
import com.example.wastewar.ui.user.CartScreen
import com.example.wastewar.ui.user.HomeScreen
import com.example.wastewar.ui.user.SaleItemDetailsScreen
import com.example.wastewar.ui.user.item.AddItemScreen
import com.example.wastewar.ui.user.item.TrackItem
import com.example.wastewar.ui.user.profile.UserProfileScreen
import com.example.wastewar.ui.userDetails.UserDetailsScreen


@Composable
fun MainScreen(
    rootNavController: NavController,
    innerPadding: PaddingValues
) {
    val mainNavController = rememberNavController()

    val navBackStackEntry by mainNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route



    Scaffold(
        bottomBar = {
            BottomNav(mainNavController)
        }
    ) { paddingValues ->
        NavHost(
            modifier = Modifier.padding(paddingValues),
            navController = mainNavController,
            startDestination = Screens.HomeScreen.routes
        ) {

            composable(Screens.HomeScreen.routes){
                HomeScreen(mainNavController)
            }

            composable(Screens.AddItemScreen.routes){
                AddItemScreen(mainNavController)
            }

            composable(Screens.ProfileScreen.routes){
                UserProfileScreen(mainNavController)

            }

            composable(Screens.UserDetailsScreen.routes) {
                UserDetailsScreen(mainNavController)
            }

            composable(Screens.TrackItem.routes){
                TrackItem(mainNavController)
            }


            composable(Screens.CartScreen.routes){
                CartScreen(mainNavController)
            }


            composable(Screens.AiSuggestScreen.routes){
                AiSuggestionScreen(mainNavController)
            }

            composable(
                route = Screens.SaleItemDetailsScreen.routes,
                arguments = listOf(
                    navArgument("id") {
                        type = NavType.StringType
                    }
                )
            ) { backStackEntry ->

                val id = backStackEntry.arguments?.getString("id")

                SaleItemDetailsScreen(
                    mainNavController = mainNavController,
                    id= id
                )
            }
        }

    }

}

