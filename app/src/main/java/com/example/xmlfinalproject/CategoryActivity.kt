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

        val incomingCountry = intent.getStringExtra("country")

        binding.apply {

            businessCv.setOnClickListener {
                val i = Intent(this@CategoryActivity, MainActivity::class.java)
                i.putExtra("category", "business")
                if (incomingCountry != null) i.putExtra("country", incomingCountry)
                startActivity(i)
            }

            entertainmentCv.setOnClickListener {
                val i = Intent(this@CategoryActivity, MainActivity::class.java)
                i.putExtra("category", "entertainment")
                if (incomingCountry != null) i.putExtra("country", incomingCountry)
                startActivity(i)
            }

            generalCv.setOnClickListener {
                val i = Intent(this@CategoryActivity, MainActivity::class.java)
                i.putExtra("category", "general")
                if (incomingCountry != null) i.putExtra("country", incomingCountry)
                startActivity(i)
            }

            healthCv.setOnClickListener {
                val i = Intent(this@CategoryActivity, MainActivity::class.java)
                i.putExtra("category", "health")
                if (incomingCountry != null) i.putExtra("country", incomingCountry)
                startActivity(i)
            }

            scienceCv.setOnClickListener {
                val i = Intent(this@CategoryActivity, MainActivity::class.java)
                i.putExtra("category", "science")
                if (incomingCountry != null) i.putExtra("country", incomingCountry)
                startActivity(i)
            }

            sportsCv.setOnClickListener {
                val i = Intent(this@CategoryActivity, MainActivity::class.java)
                i.putExtra("category", "sports")
                if (incomingCountry != null) i.putExtra("country", incomingCountry)
                startActivity(i)
            }

            technologyCv.setOnClickListener {
                val i = Intent(this@CategoryActivity, MainActivity::class.java)
                i.putExtra("category", "technology")
                if (incomingCountry != null) i.putExtra("country", incomingCountry)
                startActivity(i)
            }
        }
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

        if (item.itemId == R.id.log_out_option) {
            signOut()
            return true
        }
        else if (item.itemId == R.id.settings_option) {
            val i = Intent(this, SettingsActivity::class.java)
            i.putExtra("activity", "category")
            startActivity(i)
            finish()
            return true
        }
        else if(item.itemId == R.id.action_favorites){
            startActivity(Intent(this , FavouritesActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}