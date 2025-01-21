package com.example.lookbook_app

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.lookbook_app.ui.theme.LookBook_appTheme
import android.Manifest
import androidx.activity.viewModels
import com.example.lookbook_app.data.OutfitViewModel


class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel by viewModels<OutfitViewModel>()

        setContent {
            LookBook_appTheme {
                NavigationController(viewModel)
            }
        }
    }
}

