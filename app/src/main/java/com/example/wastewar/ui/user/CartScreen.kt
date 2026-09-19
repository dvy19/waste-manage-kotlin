package com.example.wastewar.ui.user

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn

import androidx.compose.runtime.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.*
import androidx.compose.ui.*
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.wastewar.ui.auth.SessionManager
import com.example.wastewar.ui.user.item.AddItemRepo
import com.example.wastewar.ui.user.item.AddItemVM
import com.example.wastewar.ui.user.item.GetCartItemState
import com.example.wastewar.ui.userDetails.AddItemFac
import androidx.compose.foundation.lazy.items

@Composable
fun CartScreen(
    rootNavController: NavController
) {

    val context= LocalContext.current

    val repo=AddItemRepo(SessionManager(context))

    val viewModel:AddItemVM= viewModel(
        factory = AddItemFac(repo)
    )

    LaunchedEffect(Unit) {
        viewModel.get_cart_items()
    }

    val getCartItemState by viewModel.getCartItemState.collectAsState()

   when(val state=getCartItemState){
       is GetCartItemState.Success -> {

           val cartItems=state.data.orders

           LazyColumn(
               modifier = Modifier.fillMaxSize(),
               contentPadding = PaddingValues(16.dp),
               verticalArrangement = Arrangement.spacedBy(12.dp)
           ) {
               items(cartItems) { item ->
                   CartItemCard(
                       imageUrl = item.item,
                       name = item.item,
                       price = item.amount,
                       status = CartItemStatus.IN_STOCK,
                       onViewDetailsClick = { /* Navigate or open dialog */ }
                   )
               }
           }

       }
       is GetCartItemState.Error -> {

       }
       is GetCartItemState.Loading -> {

       }
       is GetCartItemState.Idle -> {

       }
   }




   }
