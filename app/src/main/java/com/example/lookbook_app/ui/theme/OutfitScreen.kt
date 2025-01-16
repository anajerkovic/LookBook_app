package com.example.lookbook_app.ui.theme

import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.lookbook_app.R
import com.example.lookbook_app.Routes

import android.Manifest
import android.content.Intent
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.compose.ui.draw.blur
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.example.lookbook_app.data.OutfitViewModel


@Composable
fun OutfitScreen(navController: NavController,modifier: Modifier, viewModel: OutfitViewModel){
    /*
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ){
        ScreenTitle(title = "LookBook")
        SearchBar(iconResource = R.drawable.ic_search, labelText = "")
        Spacer(Modifier.height(65.dp))
        //TODO categories in a row
        OutfitTags()
        OutfitCard(iconResource = R.drawable.placeholder, title = "Test Outfit")
        IconButton(iconResource= R.drawable.ic_plus, text = "Add an outfit")
        Button(onClick = { navController.navigate("camera") }) {
            Text("Open Camera")
        }
    }}*/

    val context = LocalContext.current

    // Request permissions at runtime
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                openCamera(context) // Permission granted, open the camera
            } else {
                Toast.makeText(context, "Camera Permission is needed", Toast.LENGTH_SHORT).show()
            }
        }
    )

    // Check if the camera permission is granted
    val isPermissionGranted = ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.CAMERA
    ) == PackageManager.PERMISSION_GRANTED

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp) ,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ScreenTitle(title = "LookBook")
        SearchBar(iconResource = R.drawable.ic_search, labelText = "")
        Spacer(Modifier.height(15.dp))
        OutfitTags()
        /*
        Card(modifier = Modifier
            .fillMaxWidth()
            .clickable { navController.navigate(Routes.getOutfitDetailsPath(1)) },
            elevation = CardDefaults.cardElevation(4.dp)){
            OutfitCard(iconResource = R.drawable.placeholder, title = "Test Outfit")
        }*/
        OutfitList(modifier ,navController,viewModel)
        Spacer(Modifier.height(15.dp))
        Button(
            onClick = { navController.navigate(Routes.SCREEN_ADD_OUTFIT) },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Add a new outfit")
        }


        Button(
            onClick = {
                if (isPermissionGranted) {
                    openCamera(context)
                } else {
                    permissionLauncher.launch(Manifest.permission.CAMERA) // Request permission
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Open Camera")
        }
    }
}

private fun openCamera(context: android.content.Context) {
    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

    // Check if there's a camera app available to handle the intent
    if (intent.resolveActivity(context.packageManager) != null) {
        // Launch the camera app
        (context as ComponentActivity).startActivityForResult(intent, 1)
    } else {
        // Show a message if no camera app is found
        Toast.makeText(context, "No camera app found", Toast.LENGTH_SHORT).show()
    }
}



@Composable
fun ScreenTitle(modifier: Modifier = Modifier, title : String) {
    Box(modifier = Modifier
        .fillMaxWidth()){
        Text(text = title,
            color = Color.Black,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 25.dp, vertical = 30.dp))
    }
}

@Composable
fun SearchBar(modifier: Modifier = Modifier, @DrawableRes iconResource: Int, labelText: String) {

    var searchInput by remember { mutableStateOf("") }

    TextField(value = searchInput,
        onValueChange={searchInput = it},
        label = {Text(labelText)},
        leadingIcon = {
            Icon(painter = painterResource(id=iconResource),
            contentDescription = labelText,
            modifier = Modifier.width(16.dp).height(16.dp)) },
        modifier = Modifier
            .fillMaxWidth())
}


@Composable
fun IconButton(modifier: Modifier = Modifier,@DrawableRes iconResource : Int,
               text: String) {
    Button(
        onClick = { /*TODO*/},
        colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
    ){
        Row{
            Icon(painter = painterResource(id=iconResource),contentDescription = "")
            Spacer(Modifier.width(2.dp))
            Text(text=text)
        }
    }

}


@Composable
fun Chip(modifier: Modifier = Modifier, text:String, backgroundColor: Color = White, textColor:Color = Color.DarkGray) {
    Box(modifier = Modifier
        .background(color = backgroundColor, shape = RoundedCornerShape(12.dp))
        .padding(horizontal = 8.dp, vertical = 2.dp)){
        Text(
            text = text,
            style = TextStyle(color=textColor, fontSize = 12.sp)
        )
    }
}


@Composable
fun OutfitCard(
    modifier: Modifier = Modifier,
    @DrawableRes iconResource: Int,
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
        ) {
            Box(modifier = Modifier){
                Image(contentDescription = "",
                    contentScale = ContentScale.Crop,
                    painter = painterResource(id = iconResource)
                )
                Column(modifier = Modifier
                    .padding(15.dp)
                    .align(Alignment.BottomStart)){
                    Text("Test Outfit", color = White)
                    Row { Chip(text = "Spring")
                        Spacer(Modifier.width(2.dp))}
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
        White) else
            ButtonDefaults.buttonColors(contentColor = Color.DarkGray, containerColor =
            LightGray),
        modifier = Modifier.fillMaxHeight(),
        onClick = { onClick() }
    ) {
        Text(text)
    }
}

@Composable
fun OutfitTags() {
    var currentActiveButton by remember { mutableStateOf(0) }
    LazyRow(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 16.dp)
            .background(Color.Transparent)
            .fillMaxWidth()
            .height(34.dp)
    ) {items(1){
        TabButton(text = "All", isActive = currentActiveButton == 0) {
            currentActiveButton = 0
        }
        TabButton(text = "Spring", isActive = currentActiveButton == 1
        ){
            currentActiveButton = 1
        }
        TabButton(text = "Summer", isActive = currentActiveButton == 2) {
            currentActiveButton = 2
        }
        TabButton(text = "Autumn", isActive = currentActiveButton == 3) {
            currentActiveButton = 3
        }
        TabButton(text = "Winter", isActive = currentActiveButton == 4) {
            currentActiveButton = 4
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
        ) {
            items(viewModel.outfitsData.size) {
                OutfitCard(
                    iconResource = R.drawable.placeholder,
                    title = "Test Outfit"){
                    navigation.navigate(
                        Routes.getOutfitDetailsPath(1)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
