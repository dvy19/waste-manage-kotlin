package com.example.wastewar.ui.user.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForwardIos
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.wastewar.Screens
import com.example.wastewar.ui.auth.SessionManager
import com.example.wastewar.ui.userDetails.DetailsRepo
import com.example.wastewar.ui.userDetails.DetailsVM
import com.example.wastewar.ui.userDetails.DetailsVmFac
import com.example.wastewar.ui.userDetails.GetProfileState
import com.example.wastewar.ui.userDetails.UserStatsState

// --- MAIN SCREEN ---

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileScreen(
    mainNavController: NavController,
    userName: String = "Alex Morgan",
    userAddress: String = "742 Evergreen Terrace, Springfield",
    onCartClick: () -> Unit = {},
    onTrackItemClick: () -> Unit = {},
    onViewOrdersClick: () -> Unit = {},
    onCreateCouponClick: () -> Unit = {}
) {

    val context=LocalContext.current

    val repo=DetailsRepo(SessionManager(context))

    val viewModel:DetailsVM=viewModel(
        factory = DetailsVmFac(repo)
    )

    LaunchedEffect(Unit) {
        viewModel.get_profile()
        viewModel.userStats()
    }

    val getProfileState by viewModel.getProfileState.collectAsState()

    val userStats by viewModel.userStatsState.collectAsState()

    when(val state=getProfileState){

        is GetProfileState.Idle ->{

        }

        is GetProfileState.Loading ->{

        }

        is GetProfileState.Success ->{

            val profile=state

            ProfileHeaderCard(name = "profile.data.Profile!!.user.name" , address = "profile.data.Profile.address")


        }

        is GetProfileState.Error ->{

        }
    }



    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Profile",
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    IconButton(onClick = onCartClick) {
                        Icon(
                            imageVector = Icons.Outlined.ShoppingCart,
                            contentDescription = "Shopping Cart"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            // 1. Profile Header Card
            ProfileHeaderCard(name = userName, address = userAddress)

            Spacer(modifier = Modifier.height(16.dp))

            // 2. Stats Cards (1 Row x 2 Columns)

            when(val state=userStats){

                is UserStatsState.Idle ->{

                }

                is UserStatsState.Loading ->{


                }

                is UserStatsState.Success ->{

                    val stats=state.data

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        StatCard(
                            number = stats.stats.points.toString(),
                            title = "Total Points Earned",
                            modifier = Modifier.weight(1f)
                        )
                        StatCard(
                            number = stats.stats.itemsAdded.toString(),
                            title = "Items Added",
                            modifier = Modifier.weight(1f)
                        )
                    }


                }

                is UserStatsState.Error ->{

                }

            }


            Spacer(modifier = Modifier.height(24.dp))

            // 3. Navigation List
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                )
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    ProfileMenuItem(
                        icon = Icons.Outlined.LocalShipping,
                        title = "Track Item",
                        onClick = onTrackItemClick
                    )
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
                    )
                    ProfileMenuItem(
                        icon = Icons.Outlined.ReceiptLong,
                        title = "View All Orders",
                        onClick = onViewOrdersClick
                    )
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
                    )
                    ProfileMenuItem(
                        icon = Icons.Outlined.ConfirmationNumber,
                        title = "Create Coupon",
                        onClick = { mainNavController.navigate(Screens.CreateCouponScreen.routes) }
                    )

                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
                    )
                    ProfileMenuItem(
                        icon = Icons.Outlined.RequestPage,
                        title = "View Requested Items",
                        onClick = {mainNavController.navigate(Screens.RequestedItemsScreen.routes)}
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

// --- SUB-COMPONENTS ---

@Composable
private fun ProfileHeaderCard(
    name: String,
    address: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Person,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(36.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = address,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun StatCard(
    number: String,
    title: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = number,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun ProfileMenuItem(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector = Icons.AutoMirrored.Outlined.ArrowForwardIos,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
            modifier = Modifier.size(16.dp)
        )
    }
}
