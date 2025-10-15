package com.example.xmlfinalproject

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.xmlfinalproject.databinding.ActivitySettingsBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class SettingsActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = Firebase.auth

        setTitle("Settings")


        val incoming = intent.getStringExtra("activity")
        val incomingCategory = intent.getStringExtra("category")
        binding.signOutBtn.setOnClickListener {
            signOut()
        }

        binding.changeCountryBtn.setOnClickListener {
            startActivity(Intent(this, CountriesActivity::class.java))
        }

        binding.goBackBtn.setOnClickListener {
            finish()
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
}