package com.example.xmlfinalproject

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.xmlfinalproject.databinding.ActivityFavouritesBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class FavouritesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavouritesBinding
    private lateinit var adapter: FavouriteAdapter
    val firestore = Firebase.firestore
    val favouriteList = mutableListOf<FavouriteArticle>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFavouritesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        favouriteList.clear()
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/

//        setRecyclerView()
        loadFavourites()
    }

    private fun setRecyclerView() {
        adapter = FavouriteAdapter(this, favouriteList)
//        binding.favouriteListItem.layoutManager = LinearLayoutManager(this@FavouritesActivity)
//        binding.favouriteListItem.adapter = this@FavouritesActivity.adapter
        binding.favouriteListItem.adapter = adapter
    }

    private fun loadFavourites() {
        binding.progressFavourites.isVisible = true
        firestore
            .collection("favourites")
            .get()
            .addOnSuccessListener { queryDocumentSnapshots ->
                queryDocumentSnapshots.forEach {
                    val currentFav = it.toObject<FavouriteArticle>()
                    Log.d("trace" , "${currentFav.title}")
                    favouriteList.add(currentFav)
                }
                val adapter = FavouriteAdapter(this , favouriteList)
                binding.favouriteListItem.adapter = adapter
                binding.progressFavourites.isVisible = false
            }

    }
}