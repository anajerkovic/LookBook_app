package com.example.lookbook_app.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.lookbook_app.R
import com.example.lookbook_app.data.Outfit
import com.example.lookbook_app.data.OutfitViewModel

@Composable
fun OutfitDetailsScreen(outfitId: Int,
                        navController: NavController,
                        viewModel: OutfitViewModel) {
    val scrollState = rememberLazyListState()
    val outfit = viewModel.outfitsData[outfitId]
    // TODO: Replace with actual outfit data retrieval from Firebase
    val outfitTitle = "Casual Outfit"
    val outfitDescription = "A stylish casual outfit for everyday wear."
    val outfitTag = "Spring"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //Outfit Title
        Text(
            text = outfit.title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutfitPhoto(
            photo = outfit.imageUrl,
            viewModel = viewModel,
            outfit = outfit)

        Spacer(modifier = Modifier.height(16.dp))

        // Outfit Description

        Text(
            text = outfit.description,
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
            Text(text = outfit.tag)
        }

        Spacer(modifier = Modifier.weight(1f))

        // Back Button
        Button(
            onClick = { navController.popBackStack() },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A1B9A)), // Purple color
            modifier = Modifier.fillMaxWidth()
        ) {Text(text = "Back", color = Color.White) }
    }
}

@Composable
fun OutfitPhoto(
    photo: String,
    viewModel: OutfitViewModel,
    outfit: Outfit
){
    Box(

        modifier = Modifier
            .height(500.dp)
            .fillMaxWidth()

    ) {
        Card(shape = RoundedCornerShape(12.dp),){
            Image(
                painter = rememberAsyncImagePainter(model = photo),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )
        }

}
}
