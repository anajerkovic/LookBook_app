package com.example.lookbook_app.ui.theme

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.lookbook_app.data.Outfit
import com.google.firebase.firestore.FirebaseFirestore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddOutfitScreen(navController: NavController) {
    var outfitTitle by remember { mutableStateOf("") }
    var outfitDescription by remember { mutableStateOf("") }
    var selectedTag by remember { mutableStateOf("Spring") }

    val tags = listOf("Spring", "Summer", "Autumn", "Winter")
    val db = FirebaseFirestore.getInstance()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Add a new outfit...", fontSize = 24.sp, color = Color.Black)

        // Outfit Title Input
        Text(text = "Title", fontSize = 18.sp, color = Color.Black)
        BasicTextField(

            value = outfitTitle,
            onValueChange = { outfitTitle = it },
            textStyle = TextStyle(fontSize = 18.sp),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFEDEDED), shape = RoundedCornerShape(8.dp))
                .padding(12.dp)

        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(text = "Description", fontSize = 18.sp, color = Color.Black)
        // Outfit Description Input
        BasicTextField(
            value = outfitDescription,
            onValueChange = { outfitDescription = it },
            textStyle = TextStyle(fontSize = 18.sp),
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(Color(0xFFEDEDED), shape = RoundedCornerShape(8.dp))
                .padding(12.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Tags Selection
        Text(text = "Add a tag!", fontSize = 18.sp, color = Color.Black)
        LazyRow(modifier = Modifier.padding(vertical = 8.dp))
        {items(1){
            tags.forEach { tag ->
                Button(
                    onClick = { selectedTag = tag },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedTag == tag) Color(0xFF6A1B9A) else Color(0xFFE0E0E0),
                        contentColor = if (selectedTag == tag) Color.White else Color.Black
                    ),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.padding(4.dp)
                ) {
                    Text(text = tag)
                }
            }
        }

        }

        Spacer(modifier = Modifier.height(16.dp))

        // Placeholder for Camera (Later will integrate CameraX)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color(0xFFEDEDED), shape = RoundedCornerShape(8.dp))
                .clickable { /* TODO: Implement Camera */ },
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Take a photo!", color = Color.Gray, fontSize = 20.sp)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Save Outfit Button
        Button(
            onClick = {
                if (outfitTitle.isNotBlank() && outfitDescription.isNotBlank()) {
                    val outfit = Outfit(outfitTitle, outfitDescription, selectedTag)
                    saveOutfitToFirestore(outfit)
                    navController.popBackStack()  // Go back to the outfit list
                } else {
                    Toast.makeText(navController.context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = "Save outfit", fontSize = 20.sp, color = Color.White)
        }

        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Go Back")
        }
    }
}

// Function to save outfit to Firestore
fun saveOutfitToFirestore(outfit: Outfit) {
    val db = FirebaseFirestore.getInstance()
    db.collection("outfits")
        .add(outfit)
        .addOnSuccessListener {
            println("Outfit added successfully!")
        }
        .addOnFailureListener {
            println("Error adding outfit: ${it.message}")
        }
}
