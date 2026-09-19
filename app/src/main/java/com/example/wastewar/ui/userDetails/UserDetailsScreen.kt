package com.example.wastewar.ui.userDetails

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.AddAPhoto
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.wastewar.ui.auth.SessionManager
import com.example.wastewar.ui.createImagePart
import kotlinx.coroutines.flow.compose


val ForestGreen = Color(0xFF1E3A27)
val MeadowGreen = Color(0xFF2E6F40)
val SoftLeafGreen = Color(0xFFE8F2EA)
val SageOutline = Color(0xFFA8C3AD)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailsScreen(
    rootNavController: NavController
) {
    // Form state variables
    var phoneNumber by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var pinCode by remember { mutableStateOf("") }
    var coordinates by remember { mutableStateOf(emptyList<Double>()) }

    var logoSelected by remember { mutableStateOf(false) }

    val context= LocalContext.current

    val sessionManager=SessionManager(context)

    val repo=DetailsRepo(sessionManager)

    val viewModel:DetailsVM= viewModel(
        factory = DetailsVmFac(repo)
    )

    val userDetailState by viewModel.userDetailState.collectAsState()


    var selectedImageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val launcher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia()
        ) { uri ->

            selectedImageUri = uri

        }


    val scrollState = rememberScrollState()


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "User Profile",
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

            // Profile Picture Placeholder
            Box(
                modifier = Modifier
                    .size(110.dp)
                    .clip(CircleShape)

                    .background(SoftLeafGreen)
                    .border(2.dp, SageOutline, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                if (selectedImageUri != null) {

                    AsyncImage(
                        model = selectedImageUri,
                        contentDescription = "Selected Logo",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

                } else {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.AddAPhoto,
                            contentDescription = "Upload Logo",
                            tint = MeadowGreen,
                            modifier = Modifier.size(32.dp)
                                .clickable {
                                    launcher.launch(
                                        PickVisualMediaRequest(
                                            ActivityResultContracts.PickVisualMedia.ImageOnly
                                        )
                                    )
                                }
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = if (logoSelected) "Logo Added" else "Upload Logo",
                            style = MaterialTheme.typography.labelSmall,
                            color = MeadowGreen,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            Text(
                text = "Add Profile Picture",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Phone Number Input
            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                label = { Text("Phone Number") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Phone,
                        contentDescription = null
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Address Input
            OutlinedTextField(
                value = address,
                onValueChange = { address = it },
                label = { Text("Address") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Home,
                        contentDescription = null
                    )
                },
                maxLines = 3,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // City Input
            OutlinedTextField(
                value = city,
                onValueChange = { city = it },
                label = { Text("City") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.LocationCity,
                        contentDescription = null
                    )
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Pin Code Input
            OutlinedTextField(
                value = pinCode,
                onValueChange = { pinCode = it },
                label = { Text("PIN Code") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Pin,
                        contentDescription = null
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Submit Button
            Button(
                onClick = {

                    val imagePart =
                        selectedImageUri?.let {

                            createImagePart(
                                context,
                                it
                            )

                        }

                    viewModel.createUserProfile(
                        phoneNumber = phoneNumber,
                        city = city,
                        pinCode = pinCode,
                        address = address,
                        profile = imagePart,
                        coordinates = listOf(12.9716, 77.5946)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    text = "Save Details",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        when(userDetailState){
            is UserDetailState.Success -> {
                rootNavController.navigate("main-screen")
            }

            is UserDetailState.Error -> {
                // Handle error state
            }

            is UserDetailState.Loading -> {
                // Handle loading state
            }
            is UserDetailState.Idle -> {
                // Handle idle state
            }
        }

    }
}