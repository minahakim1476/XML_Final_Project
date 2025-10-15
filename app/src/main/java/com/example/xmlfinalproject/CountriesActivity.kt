package com.example.xmlfinalproject


import android.os.Bundle
import android.content.Intent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.xmlfinalproject.databinding.ActivityCountriesBinding


class CountriesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityCountriesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setTitle("Countries")



        val sharedPrefs = getSharedPreferences("NewsPrefs", MODE_PRIVATE)
        val savedCountry = sharedPrefs.getString("COUNTRY_KEY", "us") ?: "us"
        val checkedId = when (savedCountry) {
            "us" -> R.id.us_rbtn
            "br" -> R.id.br_rbtn
            "eg" -> R.id.eg_rbtn
            else -> R.id.de_rbtn

        }
        binding.countriesGroup.check(checkedId)



        binding.saveCountryBtn.setOnClickListener {
            val selectedCountry = when (binding.countriesGroup.checkedRadioButtonId) {
                R.id.us_rbtn -> "us"
                R.id.br_rbtn -> "br"
                R.id.eg_rbtn -> "eg"
                else -> "de"
            }

            val editor = getSharedPreferences("NewsPrefs", MODE_PRIVATE).edit()
            editor.putString("COUNTRY_KEY", selectedCountry)
            editor.apply()

            val i = Intent(this, CategoryActivity::class.java)
            i.putExtra("country", selectedCountry)
            startActivity(i)
            finishAffinity()
        }

    }

}

