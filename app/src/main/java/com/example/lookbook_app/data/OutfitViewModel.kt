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
            "imageUrl" to outfit.imageUrl
        )
        db.collection("outfits")
            .add(outfitData)
            .addOnSuccessListener { documentReference ->
                outfit.id = documentReference.id
                outfitsData.add(outfit)
                onSuccess()
            }
            .addOnFailureListener { exception ->
                println("Error adding outfit: ${exception.message}")
                onFailure(exception)
            }
    }


    fun updateOutfit(outfit: Outfit) {
        db.collection("outfits")
            .document(outfit.id!!)
            .set(outfit)
    }

    fun filterByTag(tag: String) {
        outfitsData.clear()
        val query = if (tag == "All") {
            db.collection("outfits")
        } else {
            db.collection("outfits").whereEqualTo("tag", tag)
        }

        query.get()
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

}


