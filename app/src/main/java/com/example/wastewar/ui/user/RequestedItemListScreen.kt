package com.example.wastewar.ui.user

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.wastewar.ui.RequestedItemCard
import com.example.wastewar.ui.auth.SessionManager
import com.example.wastewar.ui.user.item.AddItemRepo
import com.example.wastewar.ui.user.item.AddItemVM
import com.example.wastewar.ui.user.item.GetUserReqItemsState
import com.example.wastewar.ui.user.item.ItemData
import com.example.wastewar.ui.userDetails.AddItemFac
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestedItemListScreen(
    mainNavController: NavController,
    //onItemClick: (RequestedItem) -> Unit
) {

    val context= LocalContext.current
    val sessionManager= SessionManager(context)
    val repo=AddItemRepo( sessionManager)

    val viewModel:AddItemVM= viewModel(
        factory = AddItemFac(repo)
    )

    LaunchedEffect(Unit) {
        viewModel.get_user_req_items()
    }

    val getUserReqItemsState by viewModel.getUserReqItemsState.collectAsState()






    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Requested Items",
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { innerPadding ->

        when(val state=getUserReqItemsState){

            is GetUserReqItemsState.Idle->{

            }

            is GetUserReqItemsState.Loading->{

            }

            is GetUserReqItemsState.Success-> {

                val reqItems = state.data.item

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        items = reqItems,
                        key = { item -> item._id }
                    ) { item ->
                        RequestedItemCard(
                            item = item,
                            onCardClick = {  }
                        )
                    }
                }


            }

            is GetUserReqItemsState.Error->{

            }
        }

    }
}