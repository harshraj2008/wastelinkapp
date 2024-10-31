package com.example.wastelink

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController, email: String) {
    var fullName by remember { mutableStateOf(TextFieldValue("")) }
    var selectedCategory by remember { mutableStateOf("") }
    var selectedSubcategory by remember { mutableStateOf("") }
    var isGreeted by remember { mutableStateOf(false) }

    // Categories for the MCQ
    val categories = listOf("Waste Producer", "Waste Transporter", "Waste Recycler")

    // Subcategories
    val wasteProducerSubcategories = listOf("Individual", "Industry", "NGO", "Company")
    val wasteTransporterSubcategories = listOf("Small Fleet", "Large Fleet")
    val wasteRecyclerSubcategories = listOf("Plastic", "Paper", "Metal")

    // Additional fields for specific categories
    var wasteTransportCapacity by remember { mutableStateOf(TextFieldValue("")) }
    var recyclingCapacity by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Load and display the logo
//        Image(
//            painter = painterResource(id = R.mipmap.wastelinklogo1_round), // Change 'logo' to your actual logo file name
//            contentDescription = "App Logo",
//            modifier = Modifier
//                .size(100.dp) // Adjust size as needed
//                .padding(bottom = 16.dp) // Space below the logo
//        )

        Text(text = "Profile", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        // Display Email
        Text(text = "Email: $email")
        Spacer(modifier = Modifier.height(16.dp))

        // Input for Full Name
        OutlinedTextField(
            value = fullName,
            onValueChange = { fullName = it },
            label = { Text("Enter your Full Name") }
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Enter Button
        Button(onClick = { if (fullName.text.isNotEmpty()) isGreeted = true }) {
            Text("Enter")
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Greet the user after they enter their full name
        if (isGreeted) {
            Text(text = "Hello, ${fullName.text}!", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(16.dp))

            // Ask how they would categorize themselves using MCQs
            Text(text = "How would you categorize yourself?")
            Spacer(modifier = Modifier.height(8.dp))

            // Radio Buttons for Category Selection
            categories.forEach { category ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (selectedCategory == category),
                        onClick = {
                            selectedCategory = category
                            selectedSubcategory = "" // Reset subcategory when changing main category
                        }
                    )
                    Text(text = category)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Conditional input for subcategories based on selected category
            when (selectedCategory) {
                "Waste Producer" -> {
                    Text(text = "Please specify your type:")
                    Spacer(modifier = Modifier.height(8.dp))
                    wasteProducerSubcategories.forEach { subcategory ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = (selectedSubcategory == subcategory),
                                onClick = {
                                    selectedSubcategory = subcategory
                                    if (subcategory == "Industry" || subcategory == "NGO") {
                                        // Navigate to AI Estimator screen
                                        navController.navigate("AIEstimator")
                                    }
                                }
                            )
                            Text(text = subcategory)
                        }
                    }
                }
                "Waste Transporter" -> {
                    Text(text = "Please specify your fleet size:")
                    Spacer(modifier = Modifier.height(8.dp))
                    wasteTransporterSubcategories.forEach { subcategory ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = (selectedSubcategory == subcategory),
                                onClick = { selectedSubcategory = subcategory }
                            )
                            Text(text = subcategory)
                        }
                    }
                }
                "Waste Recycler" -> {
                    Text(text = "Please specify the type of waste you recycle:")
                    Spacer(modifier = Modifier.height(8.dp))
                    wasteRecyclerSubcategories.forEach { subcategory ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = (selectedSubcategory == subcategory),
                                onClick = { selectedSubcategory = subcategory }
                            )
                            Text(text = subcategory)
                        }
                    }
                }
            }

            // Conditional inputs based on selected category
            when (selectedCategory) {
                "Waste Transporter" -> {
                    OutlinedTextField(
                        value = wasteTransportCapacity,
                        onValueChange = { wasteTransportCapacity = it },
                        label = { Text("Enter your Waste Transport Capacity") }
                    )
                }
                "Waste Recycler" -> {
                    OutlinedTextField(
                        value = recyclingCapacity,
                        onValueChange = { recyclingCapacity = it },
                        label = { Text("Enter your Recycling Capacity") }
                    )
                }
                "Waste Producer" -> {
                    if (selectedSubcategory.isNotEmpty()) {
                        Text(text = "You selected: $selectedSubcategory")
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(32.dp))

        // Submit Button
        Button(onClick = {
            // Handle submission logic here
        }) {
            Text("Submit")
        }
    }
}
