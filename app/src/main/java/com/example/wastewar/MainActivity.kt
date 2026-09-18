package com.example.wastewar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.wastewar.ui.auth.RootNavController
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
}