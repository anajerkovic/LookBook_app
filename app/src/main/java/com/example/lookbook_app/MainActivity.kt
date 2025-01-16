package com.example.lookbook_app

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.lookbook_app.ui.theme.LookBook_appTheme
import android.Manifest
import androidx.activity.viewModels
import com.example.lookbook_app.data.OutfitViewModel


class MainActivity : ComponentActivity() {
    /*
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LookBook_appTheme {
                NavigationController()
            }
        }
    }*/

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Toast.makeText(this, "Camera Permission Granted", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Camera Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel by viewModels<OutfitViewModel>()
        // Request the camera permission
        requestPermissionLauncher.launch(Manifest.permission.CAMERA)

        setContent {
            LookBook_appTheme {
                // Use NavigationController for app navigation
                NavigationController(viewModel)
            }
        }
    }
}

