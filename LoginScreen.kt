package com.example.wastelink

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var errorMessage by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Login")
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )

        // Display error message if any
        if (errorMessage.isNotEmpty()) {
            Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Forgot Password and Sign Up Options
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(onClick = { showDialog = true }) { // Open dialog
                Text("Forgot Password?")
            }
            TextButton(onClick = { navController.navigate("signup") }) {
                Text("Sign Up")
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = {
            // Validation check
            if (email.text.isEmpty() || password.text.isEmpty()) {
                errorMessage = "Please fill in all fields"
            } else {
                errorMessage = "" // Reset error message
                navController.navigate("profile/${email.text}") // Navigate to profile with email
            }
        }) {
            Text("Login")
        }
    }

    // Forgot Password Dialog
    if (showDialog) {
        ForgotPasswordDialog(onDismiss = { showDialog = false })
    }
}

@Composable
fun ForgotPasswordDialog(onDismiss: () -> Unit) {
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var otp by remember { mutableStateOf(TextFieldValue("")) }
    var isOtpInputVisible by remember { mutableStateOf(false) }
    var successMessage by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Forgot Password") },
        text = {
            Column {
                if (!isOtpInputVisible) {
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Enter your Email") }
                    )
                    if (successMessage.isNotEmpty()) {
                        Text(text = successMessage, color = MaterialTheme.colorScheme.primary)
                    }
                } else {
                    OutlinedTextField(
                        value = otp,
                        onValueChange = { otp = it },
                        label = { Text("Enter OTP") }
                    )
                    if (successMessage.isNotEmpty()) {
                        Text(text = successMessage, color = MaterialTheme.colorScheme.primary)
                    }
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                if (!isOtpInputVisible) {
                    // Simulate sending OTP and show OTP field
                    if (email.text.isNotEmpty()) {
                        successMessage = "An OTP has been sent to $email."
                        isOtpInputVisible = true // Show OTP input
                    } else {
                        successMessage = "Please enter your email."
                    }
                } else {
                    // Handle OTP verification logic here
                    if (otp.text.isNotEmpty()) {
                        // Assume OTP verification is successful
                        successMessage = "OTP verified successfully!"
                        onDismiss() // Close dialog after success
                    } else {
                        successMessage = "Please enter the OTP."
                    }
                }
            }) {
                Text(if (!isOtpInputVisible) "Send OTP" else "Verify")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
