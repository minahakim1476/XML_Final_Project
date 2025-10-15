package com.example.xmlfinalproject

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.xmlfinalproject.databinding.ActivityCategoryBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class CategoryActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var incomingCountry: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = Firebase.auth

        incomingCountry = intent.getStringExtra("country").toString()

        binding.apply {

            businessCv.setOnClickListener {
                listNewsWithCategory("business")
            }

            entertainmentCv.setOnClickListener {
                listNewsWithCategory("entertainment")
            }

            generalCv.setOnClickListener {
                listNewsWithCategory("general")
            }

            healthCv.setOnClickListener {
                listNewsWithCategory("health")
            }

            scienceCv.setOnClickListener {
                listNewsWithCategory("science")
            }

            sportsCv.setOnClickListener {
                listNewsWithCategory("sports")
            }

            technologyCv.setOnClickListener {
                listNewsWithCategory("technology")
            }
        }
    }

    private fun listNewsWithCategory(category: String) {
        val i = Intent(this@CategoryActivity, MainActivity::class.java)
        i.putExtra("category", category)
        if (incomingCountry != null) i.putExtra("country", incomingCountry)
        startActivity(i)
    }

    private fun signOut() {
        auth.signOut()
        navigateToLoginPage()
    }

    private fun navigateToLoginPage() {
        startActivity(Intent(this, LoginActivity::class.java))
        finishAffinity()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.logout_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.log_out_option -> {
                signOut()
                return true
            }

            R.id.settings_option -> {
                val i = Intent(this, SettingsActivity::class.java)
                i.putExtra("activity", "category")
                startActivity(i)
                return true
            }

            R.id.action_favorites -> {
                startActivity(Intent(this, FavouritesActivity::class.java))
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }
}