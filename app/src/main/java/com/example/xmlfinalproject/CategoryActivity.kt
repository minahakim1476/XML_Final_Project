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

        val i = Intent(this, MainActivity::class.java)
        binding.apply {
            businessCv.setOnClickListener {
                i.putExtra("category", "business")
                startActivity(i)
            }
            entertainmentCv.setOnClickListener {
                i.putExtra("category", "entertainment")
                startActivity(i)
            }
            generalCv.setOnClickListener {
                i.putExtra("category", "general")
                startActivity(i)
            }
            healthCv.setOnClickListener {
                i.putExtra("category", "health")
                startActivity(i)
            }
            scienceCv.setOnClickListener {
                i.putExtra("category", "science")
                startActivity(i)
            }
            sportsCv.setOnClickListener {
                i.putExtra("category", "sports")
                startActivity(i)
            }
            technologyCv.setOnClickListener {
                i.putExtra("category", "technology")
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
        } else if (item.itemId == R.id.action_favorites) {
            startActivity(Intent(this, FavoritesActivity::class.java))
            return true
        } else if (item.itemId == R.id.settings_option) {
            val i = Intent(this, SettingsActivity::class.java)
            i.putExtra("activity", "category")
            startActivity(i)
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}