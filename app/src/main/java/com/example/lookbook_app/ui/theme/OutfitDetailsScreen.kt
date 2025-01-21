package com.example.lookbook_app.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.lookbook_app.data.Outfit
import com.example.lookbook_app.data.OutfitViewModel

@Composable
fun OutfitDetailsScreen(outfitId: Int,
                        navController: NavController,
                        viewModel: OutfitViewModel) {
    val outfit = viewModel.outfitsData[outfitId]

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
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif,
            color = Pink40
        )
        Spacer(modifier = Modifier.height(20.dp))


        OutfitPhoto(
            photo = outfit.imageUrl,
            viewModel = viewModel,
            outfit = outfit)

        Spacer(modifier = Modifier.height(16.dp))

        // Outfit Description

        Text(
            text = outfit.description,
            fontSize = 20.sp,
            color = Purple40 // Purple color for text
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Outfit Tag
        TextButton(
            onClick = { },
            colors = ButtonDefaults.buttonColors(contentColor =
            White, containerColor = Pink40),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.padding(12.dp)
        ) {
            Text(text = outfit.tag)
        }

        Spacer(modifier = Modifier.weight(1f))

        // Back Button
        Button(
            onClick = { navController.popBackStack() },
            colors =ButtonDefaults.buttonColors(contentColor =
            White, containerColor = Pink40), // Purple color
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
