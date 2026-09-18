package com.example.wastewar.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(rootNavController: NavController){

    val repo= AuthRepo()

    val viewModel: AuthVM= viewModel()

    val loginState by viewModel.loginState.collectAsState()

    //var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    //var role by remember { mutableStateOf("") }

    var isPasswordVisible by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current


    val scrollState= rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Login Here",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = ForestGreen
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        rootNavController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = "Back",
                            tint = ForestGreen
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFF9FBF9)
                ),

                )
        },

        containerColor = Color(0xFFF9FBF9)
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState)
                .background(Color.White)
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Header Title
            Text(
                text = "Create Account",
                color=Color.Black,
                style = MaterialTheme.typography.headlineLarge,
                //color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Email Input
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email Address") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Email Icon"
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),

                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = MeadowGreen,
                    unfocusedBorderColor = SageOutline,
                    focusedLabelColor = MeadowGreen,
                    unfocusedLabelColor = Color.Gray,

                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Password Input
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Password Icon"
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                        Icon(
                            imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = if (isPasswordVisible) "Hide password" else "Show password"
                        )
                    }
                },
                singleLine = true,
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                ),

                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = MeadowGreen,
                    unfocusedBorderColor = SageOutline,
                    focusedLabelColor = MeadowGreen,
                    unfocusedLabelColor = Color.Black,

                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Submit Button
            Button(
                onClick = {
                    focusManager.clearFocus()
                    viewModel.login_user(
                        LoginReq(
                            email = email,
                            password = password,
                        )
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                when (loginState) {
                    is AuthState.Idle -> {
                        Text(
                            text = "Register",
                            color=Color.Black,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }

                    is AuthState.Loading -> {
                        Text(
                            text = "Loading",
                            color=Color.Black,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }

                    is AuthState.Error -> {
                        Text(
                            text = "Error",
                            color=Color.Black,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }

                    is AuthState.Success -> {

                        //rootNavController.navigate(Screens.UserDetailsScreen.route)

                        Text(
                            text = "Success",
                            color=Color.Black,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Navigation to Login
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Already have an account?",
                    color=Color.Black,
                    style = MaterialTheme.typography.bodyMedium,
                    // color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                TextButton(onClick = {  }) {
                    Text(
                        text = "Log In",
                        color=Color.Black,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }
    }





}