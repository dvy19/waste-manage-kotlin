package com.example.wastewar.ui.auth

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.wastewar.Screens


@Composable
fun RootNavController(innerPadding: PaddingValues) {
    val rootNavController = rememberNavController()

    val navBackStackEntry by rootNavController.currentBackStackEntryAsState()



        NavHost(
            navController = rootNavController,
            startDestination = Screens.SplashScreen.routes
        ) {

            composable(Screens.Register.routes){
                Register()
            }

            composable(Screens.Login.routes){
                Login()
            }

            composable(Screens.SplashScreen.routes){
                SplashScreen()
            }


        }




}