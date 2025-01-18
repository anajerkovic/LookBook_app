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

    fun addOutfit(outfit: Outfit, onSuccess: () -> Unit = {}, onFailure: (Exception) -> Unit = {}) {
        val outfitData = hashMapOf(
            "title" to outfit.title,
            "description" to outfit.description,
            "tag" to outfit.tag,
            "imageUrl" to outfit.imageUrl // Placeholder for image URL
        )
        db.collection("outfits")
            .add(outfitData)
            .addOnSuccessListener { documentReference ->
                // Update the outfit's ID after successful addition
                outfit.id = documentReference.id
                outfitsData.add(outfit) // Add to the local list
                onSuccess()
            }
            .addOnFailureListener { exception ->
                println("Error adding outfit: ${exception.message}")
                onFailure(exception)
            }
    }

    // Update an existing outfit in Firestore
    fun updateOutfit(outfit: Outfit, onSuccess: () -> Unit = {}, onFailure: (Exception) -> Unit = {}) {
        if (outfit.id == null) {
            println("Error: Cannot update outfit without an ID.")
            return
        }

        db.collection("outfits")
            .document(outfit.id!!)
            .set(outfit)
            .addOnSuccessListener {
                // Update the local list
                val index = outfitsData.indexOfFirst { it.id == outfit.id }
                if (index >= 0) {
                    outfitsData[index] = outfit
                }
                onSuccess()
            }
            .addOnFailureListener { exception ->
                println("Error updating outfit: ${exception.message}")
                onFailure(exception)
            }
    }
}