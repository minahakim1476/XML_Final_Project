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

        val countries = arrayOf("us", "eg")
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, countries)
        // Resolve country_spinner id dynamically to avoid any generated R id issues
        val countryId = resources.getIdentifier("country_spinner", "id", packageName)
        val countryField =
            if (countryId != 0) binding.root.findViewById<com.google.android.material.textfield.MaterialAutoCompleteTextView>(
                countryId
            ) else null
        countryField?.setAdapter(adapter)

        val sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE)
        val savedCountry = sharedPreferences.getString("country", "us")
        // Pre-fill the AutoCompleteTextView with the saved value
        countryField?.setText(savedCountry, false)

        // When a user picks an item from the dropdown, save it
        countryField?.setOnItemClickListener { parent: android.widget.AdapterView<*>, view: android.view.View, position: Int, id: Long ->
            val selectedCountry = adapter.getItem(position) ?: return@setOnItemClickListener
            sharedPreferences.edit { putString("country", selectedCountry) }
        }

        // Show dropdown when the field is tapped (prevents soft keyboard and behaves like a spinner)
        countryField?.let {
            it.isFocusable = false
            it.setOnClickListener { _ -> it.showDropDown() }
        }

        val incoming = intent.getStringExtra("activity")
        val incomingCategory = intent.getStringExtra("category")
        binding.signOutBtn.setOnClickListener {
            signOut()
        }

        binding.changeCountryBtn.setOnClickListener {
            startActivity(Intent(this, CountriesActivity::class.java))
        }

        binding.goBackBtn.setOnClickListener {
            when (incoming) {
                "main" -> {
                    val i = Intent(this, MainActivity::class.java)
                    i.putExtra("category", incomingCategory)
                    startActivity(i)
                }

                "category" -> startActivity(Intent(this, CategoryActivity::class.java))
            }
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