package com.example.wastelink

import androidx.compose.ui.Modifier

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@Composable
fun AIEstimatorScreen() {
    var wasteType by remember { mutableStateOf("") }
    var wasteAmount by remember { mutableStateOf(TextFieldValue("")) }
    var estimatedWaste by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(text = "AI Waste Estimator", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        // Input for Waste Type
        OutlinedTextField(
            value = wasteType,
            onValueChange = { wasteType = it },
            label = { Text("Enter Waste Type (e.g., Plastic, Metal, Paper)") }
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Input for Waste Amount
        OutlinedTextField(
            value = wasteAmount,
            onValueChange = { wasteAmount = it },
            label = { Text("Enter Waste Amount (kg)") }
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Estimate Button
        Button(onClick = {
            estimatedWaste = estimateWaste(wasteType, wasteAmount.text.toDoubleOrNull())
        }) {
            Text("Estimate Waste")
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Display Estimated Waste
        if (estimatedWaste.isNotEmpty()) {
            Text(text = "Estimated Waste: $estimatedWaste kg", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

// Function to estimate waste based on user input
private fun estimateWaste(wasteType: String, amount: Double?): String {
    return if (amount != null) {
        // Example estimation logic (this can be replaced with actual logic)
        val estimated = amount * when (wasteType.lowercase()) {
            "plastic" -> 0.8 // Placeholder multiplier for plastic
            "metal" -> 0.6 // Placeholder multiplier for metal
            "paper" -> 0.4 // Placeholder multiplier for paper
            else -> 1.0 // Default multiplier
        }
        String.format("%.2f", estimated)
    } else {
        "Invalid input"
    }
}

