package com.example.wastewar.ui.user.item

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.wastewar.ui.auth.SessionManager
import com.example.wastewar.ui.userDetails.AddItemFac


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackItem(
    rootNavController: NavController
) {

    var searchInput by remember { mutableStateOf("") }
    var trackedItem by remember { mutableStateOf<ItemData?>(null) }
    var hasSearched by remember { mutableStateOf(false) }

    val context=LocalContext.current

    val repo=AddItemRepo(SessionManager(context))

    val viewModel:AddItemVM=viewModel(
        factory = AddItemFac(repo)
    )

    val trackItemState by viewModel.trackItemState.collectAsState()


    val keyboardController = LocalSoftwareKeyboardController.current

    fun handleSearch(trackingId:String){

        if (searchInput.isNotBlank()) {
            keyboardController?.hide()
            viewModel.track_item(trackingId)
            hasSearched = true
        }
    }



    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Track Item",
                        fontWeight = FontWeight.Bold
                    )
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
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Search Bar Input Section
            OutlinedTextField(
                value = searchInput,
                onValueChange = { searchInput = it },
                label = { Text("Enter Tracking ID") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Search,
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    if (searchInput.isNotEmpty()) {
                        IconButton(onClick = { searchInput = "" }) {
                            Icon(
                                imageVector = Icons.Outlined.Clear,
                                contentDescription = "Clear text"
                            )
                        }
                    }
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = { handleSearch(searchInput) }),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { handleSearch(searchInput) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    text = "Track",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Result Display Section
            AnimatedVisibility(
                visible = hasSearched,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                if (trackedItem != null) {
                    ItemResultCard(item = trackedItem!!)
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No item found with this ID",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ItemResultCard(item: ItemData) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            // Header: Image + Name & Status
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Inventory2,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.size(32.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = item.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Surface(
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = item.status,
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
            Spacer(modifier = Modifier.height(16.dp))

            // Details List
            DetailRow(label = "Tracking ID", value = item.trackingId)
            DetailRow(label = "Category", value = item.category.replaceFirstChar { it.uppercase() })
            DetailRow(label = "Quantity", value = item.quantity)
            DetailRow(label = "Weight", value = item.weight)
            DetailRow(
                label = "Processing",
                value = item.processingMethod,
                isLast = true
            )
        }
    }
}

@Composable
private fun DetailRow(
    label: String,
    value: String,
    isLast: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold
        )
    }
}