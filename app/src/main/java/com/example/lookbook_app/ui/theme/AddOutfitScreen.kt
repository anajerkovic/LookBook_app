package com.example.lookbook_app.ui.theme

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.lookbook_app.data.Outfit
import com.example.lookbook_app.data.OutfitViewModel
import com.google.firebase.firestore.FirebaseFirestore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddOutfitScreen(navController: NavController, viewModel: OutfitViewModel) {

    val context = LocalContext.current

    var outfitTitle by remember { mutableStateOf("") }
    var outfitDescription by remember { mutableStateOf("") }
    var selectedTag by remember { mutableStateOf("") }
    var outfitLink by remember { mutableStateOf("") }

    val tags = listOf("Spring", "Summer", "Autumn", "Winter")
    val db = FirebaseFirestore.getInstance()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text(text = "Add a new outfit...", fontSize = 24.sp, color = Color.Black, fontFamily = FontFamily.Serif)
        Spacer(modifier = Modifier.height(15.dp))

        // Outfit Title Input
        Text(text = "Title", fontSize = 18.sp, color = Purple40)
        BasicTextField(
            value = outfitTitle,
            onValueChange = { outfitTitle = it },
            textStyle = TextStyle(fontSize = 18.sp),
            modifier = Modifier
                .fillMaxWidth()
                .background(color = LightGray, shape = RoundedCornerShape(8.dp))
                .padding(12.dp)

        )
        Spacer(modifier = Modifier.height(12.dp))


        // Outfit Description Input
        Text(text = "Description", fontSize = 18.sp, color = Purple40)
        BasicTextField(
            value = outfitDescription,
            onValueChange = { outfitDescription = it },
            textStyle = TextStyle(fontSize = 18.sp),
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(color = LightGray, shape = RoundedCornerShape(8.dp))
                .padding(12.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

    //Url input
        Text(text = "Image URL", fontSize = 18.sp, color = Purple40)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(color = LightGray, shape = RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            BasicTextField(
                value = outfitLink,
                onValueChange = { outfitLink = it },
                textStyle = TextStyle(fontSize = 18.sp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(color = LightGray, shape = RoundedCornerShape(8.dp))
                    .padding(12.dp)
            )
        }
        Spacer(modifier = Modifier.height(24.dp))


        // Tags Selection
        Text(text = "Add a tag!", fontSize = 18.sp, color = Color.Black)
        LazyRow(modifier = Modifier.padding(vertical = 8.dp))
        {items(1){
            tags.forEach { tag ->
                Button(
                    onClick = { selectedTag = tag },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedTag == tag) Pink40 else DarkGray,
                        contentColor = if (selectedTag == tag) Color.White else Color.White
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



        // Save Outfit Button
        Button(
            onClick = {
                if (outfitTitle.isNotBlank() && outfitDescription.isNotBlank() && selectedTag.isNotBlank() && outfitLink.isNotBlank()) {
                    val newOutfit = Outfit(
                        title = outfitTitle,
                        description = outfitDescription,
                        tag = selectedTag,
                        imageUrl = outfitLink
                    )
                    // Add outfit via ViewModel
                    viewModel.addOutfit(newOutfit, onSuccess = {
                        Toast.makeText(context, "Outfit added successfully!", Toast.LENGTH_SHORT).show()
                        navController.popBackStack() // Navigate back to the Outfit List screen
                    }, onFailure = { exception ->
                        Toast.makeText(context, "Error: ${exception.message}", Toast.LENGTH_SHORT).show()
                    })
                } else {
                    Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(contentColor =
            White, containerColor = Pink40)
        ) {
            Text(text = "Save outfit", fontSize = 20.sp, color = Color.White)
        }

        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.padding(16.dp),
            colors = ButtonDefaults.buttonColors(contentColor =
            White, containerColor = Pink40)
        ) {
            Text(text = "Go Back")
        }
    }
}

fun saveOutfitToFirestore(outfit: Outfit) {
    val db = FirebaseFirestore.getInstance()
    val outfitData = hashMapOf(
        "title" to outfit.title,
        "description" to outfit.description,
        "tag" to outfit.tag,
        "imageUrl" to "" // Placeholder for image URL, if applicable
    )

    db.collection("outfits")
        .add(outfitData)
        .addOnSuccessListener { documentReference ->
            // Update the document with its auto-generated ID
            documentReference.update("id", documentReference.id)
            println("Outfit added successfully with ID: ${documentReference.id}")
        }
        .addOnFailureListener { exception ->
            println("Error adding outfit: ${exception.message}")
        }
}
