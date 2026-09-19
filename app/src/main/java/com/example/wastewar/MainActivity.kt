package com.example.wastewar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.wastewar.ui.RootNavController
import com.example.wastewar.ui.theme.WasteWarTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WasteWarTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    RootNavController(innerPadding)

                }
            }
        }
    }
}


sealed class Screens(val routes:String){

    data object SplashScreen:Screens("splash")

    data object Register:Screens("register")
    data object Login:Screens("login")

    data object UserDetailsScreen:Screens("userDetails")
    data object HomeScreen:Screens("home")
    data object AddItemScreen:Screens("item")
    data object ProfileScreen:Screens("profile")

    data object TrackItem:Screens("track-item")

    data object CartScreen:Screens("cart-screen")

    data object AiSuggestScreen:Screens("ai-suggestion")

    data object MainScreen:Screens("main-screen")

    data object SaleItemDetailsScreen:Screens("sale_item_details/{id}")

    data object RequestedItemsScreen:Screens("requested-items")
}