package com.example.lookbook_app.data

import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import androidx.compose.runtime.mutableStateListOf
import com.google.firebase.firestore.firestore


class OutfitViewModel : ViewModel() {
    private val db = Firebase.firestore

    // Holds the list of outfits
    val outfitsData = mutableStateListOf<Outfit>()
    init {
        fetchOutfits()
    }

    // Fetch outfits from Firestore
    private fun fetchOutfits() {
        db.collection("outfits").get()
            .addOnSuccessListener { result ->
                for (data in result.documents) {
                    val outfit = data.toObject(Outfit::class.java)
                    if (outfit != null) {
                        outfit.id = data.id
                        outfitsData.add(outfit)
                    }
                }
            }
    }

    // Add a new outfit to Firestore
    fun updateOufit(outfit: Outfit) {
        db.collection("outfits")
            .document(outfit.id)
            .set(outfit)
    }
}