package com.example.wastewar.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.wastewar.Screens
import com.example.wastewar.ui.auth.Login
import com.example.wastewar.ui.auth.Register
import com.example.wastewar.ui.auth.SplashScreen
import com.example.wastewar.ui.user.AiSuggestionScreen
import com.example.wastewar.ui.user.CartScreen
import com.example.wastewar.ui.userDetails.UserDetailsScreen


@Composable
fun RootNavController(innerPadding: PaddingValues) {
    val rootNavController = rememberNavController()

    val navBackStackEntry by rootNavController.currentBackStackEntryAsState()

    NavHost(
            navController = rootNavController,
            startDestination = Screens.Register.routes
        ) {

            composable(Screens.Register.routes){
                Register(rootNavController)
            }

            composable(Screens.Login.routes){
                Login(rootNavController)
            }

            composable(Screens.SplashScreen.routes){
                SplashScreen()
            }

            composable(Screens.MainScreen.routes){
                MainScreen(rootNavController , innerPadding)
            }

            composable(Screens.UserDetailsScreen.routes){
                UserDetailsScreen(rootNavController)
            }




        }




}