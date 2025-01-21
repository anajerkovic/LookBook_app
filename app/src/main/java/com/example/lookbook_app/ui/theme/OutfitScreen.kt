package com.example.lookbook_app.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.lookbook_app.R
import com.example.lookbook_app.Routes
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.text.font.FontFamily
import coil.compose.rememberAsyncImagePainter
import com.example.lookbook_app.data.OutfitViewModel


@Composable
fun OutfitScreen(navController: NavController,modifier: Modifier, viewModel: OutfitViewModel){
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ScreenTitle(title = "LookBook")
        Spacer(Modifier.height(5.dp))
        OutfitTags(viewModel = viewModel)
        OutfitList(modifier ,navController,viewModel)
        Spacer(Modifier.height(15.dp))
        Button(
            onClick = { navController.navigate(Routes.SCREEN_ADD_OUTFIT) },
            modifier = Modifier.padding(16.dp),
            colors = ButtonDefaults.buttonColors(contentColor =
            White, containerColor = Pink40)
        ) {
            Text(text = "Add a new outfit")
        }

    }
}

@Composable
fun ScreenTitle(modifier: Modifier = Modifier, title : String) {
    Box(modifier = Modifier
        .fillMaxWidth(),
        contentAlignment = Alignment.Center
        ){
        Text(text = title,
            fontFamily = FontFamily.Serif,
            color = Pink40,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 25.dp, vertical = 30.dp))
    }
}

@Composable
fun OutfitCard(
    imageResource: String,
    title : String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(bottom = 16.dp, top = 16.dp)
    ) {
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = LightGray),
            modifier = Modifier
                .padding(bottom = 8.dp)
                .clickable {
                    onClick()
                }
                .fillMaxWidth()
        ) {
            Box(modifier = Modifier){
                Image(
                    painter = rememberAsyncImagePainter(model = imageResource,
                        error = painterResource(R.drawable.placeholder),
                        placeholder = painterResource(R.drawable.placeholder)),
                    contentDescription = title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()

                )
                Column(modifier = Modifier
                    .padding(15.dp)
                    .align(Alignment.BottomStart)){
                    Text(text = title,
                        letterSpacing = 0.32.sp,
                        color = White)
                }
            }
        }
    }
}


@Composable
fun TabButton(
    text: String,
    isActive: Boolean,
    onClick: () -> Unit
) {
    Button(
        shape = RoundedCornerShape(24.dp),
        elevation = null,
        colors = if (isActive) ButtonDefaults.buttonColors(contentColor =
        White, containerColor = Pink40) else
            ButtonDefaults.buttonColors(contentColor = Color.DarkGray, containerColor =
            LightGray),
        modifier = Modifier.fillMaxHeight(),
        onClick = { onClick() }
    ) {
        Text(text)
    }
}


@Composable
fun OutfitTags(viewModel: OutfitViewModel) {
    var currentActiveButton by remember { mutableStateOf(0) }
    val tags = listOf("All", "Spring", "Summer", "Autumn", "Winter")

    LazyRow(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 16.dp)
            .background(Color.Transparent)
            .fillMaxWidth()
            .height(34.dp)
    ) {
        items(tags.size) { index ->
            TabButton(
                text = tags[index],
                isActive = currentActiveButton == index
            ) {
                currentActiveButton = index
                viewModel.filterByTag(tags[index]) // Trigger filtering
            }
        }
    }
}



@Composable
fun OutfitList(modifier: Modifier,
               navigation: NavController,
               viewModel: OutfitViewModel
    ){

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(450.dp)
        ) {
            items(viewModel.outfitsData.size) {
                OutfitCard(
                    imageResource = viewModel.outfitsData[it].imageUrl,
                    title = viewModel.outfitsData[it].title){
                    navigation.navigate(
                        Routes.getOutfitDetailsPath(it)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
