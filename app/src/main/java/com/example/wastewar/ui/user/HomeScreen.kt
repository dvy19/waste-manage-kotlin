package com.example.wastewar.ui.user

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Message
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wastewar.Screens
import com.example.wastewar.ui.auth.SessionManager
import com.example.wastewar.ui.auth.SoftLeafGreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    mainNavController: NavController
){




    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )

    val context= LocalContext.current

    val scope= rememberCoroutineScope()

    val sessionManager=SessionManager(context)


    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(240.dp),
                drawerContainerColor = Color.White
            ) {
                // Header spacing (or add a DrawerHeader here)
                Spacer(modifier = Modifier.height(16.dp))

                NavigationDrawerItem(
                    label = {
                        Text(
                            "Saved Facts",
                            color = Color.Black
                        )
                    },
                    selected = false,

                    colors = NavigationDrawerItemDefaults.colors(
                        unselectedContainerColor = Color.White,
                        selectedContainerColor = Color(0xFFE0E0E0), // Light gray when selected
                        unselectedTextColor = Color.Black,
                        selectedTextColor = Color.Black,
                        unselectedIconColor = Color.Black,
                        selectedIconColor = Color.Black
                    ),
                    onClick = {
                        //mainNavController.navigate(Screens.SavedFactsScreen.route)
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )


                NavigationDrawerItem(
                    label = { Text(
                        "Your Plants",
                        color = Color.Black
                    ) },
                    selected = false,

                    colors = NavigationDrawerItemDefaults.colors(
                        unselectedContainerColor = Color.White,
                        selectedContainerColor = Color(0xFFE0E0E0), // Light gray when selected
                        unselectedTextColor = Color.Black,
                        selectedTextColor = Color.Black,
                        unselectedIconColor = Color.Black,
                        selectedIconColor = Color.Black
                    ),
                    onClick = {
                       // mainNavController.navigate(Screens.YourPlantsScreen.route)
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )

                NavigationDrawerItem(
                    label = { Text(
                        "Plant Catalog",
                        color = Color.Black
                    ) },
                    selected = false,

                    colors = NavigationDrawerItemDefaults.colors(
                        unselectedContainerColor = Color.White,
                        selectedContainerColor = Color(0xFFE0E0E0), // Light gray when selected
                        unselectedTextColor = Color.Black,
                        selectedTextColor = Color.Black,
                        unselectedIconColor = Color.Black,
                        selectedIconColor = Color.Black
                    ),
                    onClick = {
                        //mainNavController.navigate(Screens.PlantCatalogScreen.route)
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )

                NavigationDrawerItem(
                    label = { Text(
                        "Logout",
                        color = Color.Red
                    ) },
                    selected = false,

                    colors = NavigationDrawerItemDefaults.colors(
                        unselectedContainerColor = Color.White,
                        selectedContainerColor = Color(0xFFE0E0E0), // Light gray when selected
                        unselectedTextColor = Color.Black,
                        selectedTextColor = Color.Black,
                        unselectedIconColor = Color.Black,
                        selectedIconColor = Color.Black
                    ),

                    onClick = {
                        sessionManager.logout()

                        //mainNavController.navigate(Screens.GetStartScreen.route)

                        Toast.makeText(context, "Logged Out", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )

                // Push the close button to the bottom of the drawer
                Spacer(modifier = Modifier.weight(1f))

                NavigationDrawerItem(
                    label = { Text(
                        "Close",
                        color = Color.Black
                    ) },

                    colors = NavigationDrawerItemDefaults.colors(
                        unselectedContainerColor = Color.White,
                        selectedContainerColor = Color(0xFFE0E0E0), // Light gray when selected
                        unselectedTextColor = Color.Black,
                        selectedTextColor = Color.Black,
                        unselectedIconColor = Color.Black,
                        selectedIconColor = Color.Black
                    ),
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    modifier = Modifier.heightIn(min = 56.dp)
                        .background(SoftLeafGreen),
                    title = { Text("HomeScreen") },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch { drawerState.open() }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Open navigation drawer"
                            )
                        }
                    },

                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.White,
                        titleContentColor = Color.Black,
                        navigationIconContentColor = Color.Black,
                        actionIconContentColor = Color.Black
                    ),
                    actions = {
                        IconButton(onClick = {}) {
                            Icon(
                                imageVector = Icons.Default.Message,
                                contentDescription = "Messages"
                            )
                        }
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(onClick = {
                    mainNavController.navigate(Screens.AddItemScreen.routes)
                }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add"
                    )
                }
            }
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(innerPadding),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

            }

        }

    }



}