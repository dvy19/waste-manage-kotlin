package com.example.wastewar.ui

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ConfirmationNumber
import androidx.compose.material.icons.outlined.Stars
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.wastewar.ui.auth.SessionManager
import com.example.wastewar.ui.user.CouponData
import com.example.wastewar.ui.userDetails.CouponState
import com.example.wastewar.ui.userDetails.DetailsRepo
import com.example.wastewar.ui.userDetails.DetailsVM
import com.example.wastewar.ui.userDetails.DetailsVmFac
import com.example.wastewar.ui.userDetails.GetCouponState
import com.example.wastewar.ui.userDetails.UserStatsState

// Model representing a created coupon
data class Coupon(
    val id: String,
    val title: String,
    val pointsCost: Int,
    val code: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComposableCouponScreen(
    mainNavController: NavController
) {

    // State management
    var userPoints by remember { mutableIntStateOf(1250) }
    var couponTitle by remember { mutableStateOf("") }
    val coupons = remember {
        mutableStateListOf(
            Coupon("1", "10% Off Entire Order", 500, "SAVE10-ABC"),
            Coupon("2", "Free Shipping", 500, "FREESHIP-XYZ")
        )
    }

    val context=LocalContext.current

    val repo=DetailsRepo(SessionManager(context))

    val viewModel:DetailsVM=viewModel(
        factory = DetailsVmFac(repo)
    )

    LaunchedEffect(Unit) {
        viewModel.userStats()
        viewModel.get_all_coupons()
    }

    val userStats by viewModel.userStatsState.collectAsState()

    val couponState by viewModel.couponState.collectAsState()

    val getCouponState by viewModel.getCouponState.collectAsState()



    when(couponState){
        is CouponState.Idle->{

        }
        is CouponState.Loading->{

        }
        is CouponState.Success->{

            Toast.makeText(context, "Coupon created successfully", Toast.LENGTH_SHORT).show()


        }
        is CouponState.Error->{

        }
    }

    val canCreateCoupon = userPoints >= 500 && couponTitle.isNotBlank()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Coupons & Rewards") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Points Balance Card

            when(val state=userStats){
                is UserStatsState.Idle->{

                }
                is UserStatsState.Loading->{

                }
                is UserStatsState.Success->{
                    val stats=state.data
                    userPoints=stats.stats.points

                    item {
                        PointsBalanceCard(points = userPoints)
                    }
                }
                is UserStatsState.Error->{

                }
            }

            // Create Coupon Form Section
            item {
                CreateCouponCard(
                    userPoints = userPoints,
                    couponTitle = couponTitle,
                    onTitleChange = { couponTitle = it },
                    canCreateCoupon = canCreateCoupon,
                    onCreateClick = {
                        if (userPoints >= 500 && couponTitle.isNotBlank()) {
                            viewModel.create_coupon()
                            userPoints -= 500
                            couponTitle = ""
                        }
                    }
                )
            }

            // Available Coupons Header
            item {
                Text(
                    text = "Your Active Coupons",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            // Coupons List
            if (coupons.isEmpty()) {
                item {
                    Text(
                        text = "No coupons created yet. Redeem your points above!",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(vertical = 12.dp)
                    )
                }
            } else {

                when(val state=getCouponState){
                    is GetCouponState.Idle->{

                    }
                    is GetCouponState.Loading->{

                    }
                    is GetCouponState.Success->{
                        val coupons=state.data
                        items(coupons) { coupon ->
                            CouponItemCard(coupon = coupon)
                        }

                    }
                    is GetCouponState.Error->{

                    }
                }
            }
        }
    }
}

@Composable
private fun PointsBalanceCard(points: Int) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Current Points",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = "$points PTS",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
            Icon(
                imageVector = Icons.Outlined.Stars,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxSize(0.12f)
            )
        }
    }
}

@Composable
private fun CreateCouponCard(
    userPoints: Int,
    couponTitle: String,
    onTitleChange: (String) -> Unit,
    canCreateCoupon: Boolean,
    onCreateClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Redeem Coupon (500 pts)",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = couponTitle,
                onValueChange = onTitleChange,
                label = { Text("Coupon Description/Title") },
                placeholder = { Text("e.g. $5 Off Next Order") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            AnimatedVisibility(visible = userPoints < 500) {
                Text(
                    text = "You need at least 500 points to create a coupon.",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 6.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onCreateClick,
                enabled = canCreateCoupon,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Create Coupon")
            }
        }
    }
}

@Composable
private fun CouponItemCard(coupon: CouponData) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.ConfirmationNumber,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(end = 16.dp)
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = coupon.discount.toString(),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Code: ${coupon.code}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium
                )
            }
            Surface(
                color = MaterialTheme.colorScheme.secondaryContainer,
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "-${coupon.isUsed} pts",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
    }
}