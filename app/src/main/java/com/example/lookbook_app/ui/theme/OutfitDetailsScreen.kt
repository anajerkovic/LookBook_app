package com.example.lookbook_app.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun OutfitDetailsScreen(outfitId: Int, navController: NavController) {
    // TODO: Replace with actual outfit data retrieval from Firebase
    val outfitTitle = "Casual Outfit"
    val outfitDescription = "A stylish casual outfit for everyday wear."
    val outfitTag = "Spring"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //Outfit Title
        Text(
            text = outfitTitle,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Outfit Photo Placeholder
        Box(
            modifier = Modifier
                .size(250.dp)
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Outfit\nphoto",
                fontSize = 18.sp,
                color = Color.Gray
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Outfit Description
        Text(
            text = outfitDescription,
            fontSize = 16.sp,
            color = Color(0xFF6A1B9A) // Purple color for text
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Outfit Tag
        TextButton(
            onClick = { /* Tag functionality can be added */ },
            colors = ButtonDefaults.textButtonColors(
                containerColor = Color.LightGray,
                contentColor = Color.DarkGray
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = outfitTag)
        }

        Spacer(modifier = Modifier.weight(1f))

        // Back Button
        Button(
            onClick = { navController.popBackStack() },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A1B9A)), // Purple color
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Back", color = Color.White)
        }
    }
}
