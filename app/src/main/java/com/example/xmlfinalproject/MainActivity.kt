package com.example.xmlfinalproject


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.example.xmlfinalproject.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var category: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val scale = resources.displayMetrics.density
            val padding = (16 * scale + 0.5f).toInt()
            v.setPadding(
                systemBars.left + padding,
                systemBars.top + padding,
                systemBars.right + padding,
                systemBars.bottom + padding
            )
            insets
        }


//Change category from CategoryActivity - Yahya Aboabdo

        category = intent.getStringExtra("category")!!



        val sharedPrefs = getSharedPreferences("NewsPrefs", MODE_PRIVATE)
        val country = sharedPrefs.getString("COUNTRY_KEY", "us") ?: "us"



        setTitle(category.replaceFirstChar { it.uppercase() })

        getNews(category, country)


        auth = Firebase.auth

        binding.apply {
            swipeToRefresh.setOnRefreshListener {
                getNews(category, country)
                progressCircular.isVisible = true
                articleListItem.isVisible = false
            }
        }
    }

    private fun getNews(category: String, country: String) {
        val retroFit = Retrofit
            .Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val newsCallable = retroFit.create(ArticleCallable::class.java)


        newsCallable.getNews(category = category, country = country)
            .enqueue(object : Callback<News> {
                override fun onResponse(
                    call: Call<News?>,
                    response: Response<News?>
                ) {
                    Log.d("ArticleList", category)
                    if (response.isSuccessful) {
                        val news = response.body()
                        val adapter = ArticleAdapter(this@MainActivity, news!!)
                        binding.apply {
                            articleListItem.adapter = adapter
                            progressCircular.isVisible = false
                            swipeToRefresh.isRefreshing = false
                            articleListItem.isVisible = true
                        }
                    } else {
                        Toast.makeText(this@MainActivity, "No News!", Toast.LENGTH_SHORT).show()
                        val errorString = response.errorBody()?.string()
                        Log.d("ArticleList", errorString ?: "nothing?")
                    }
                }

                override fun onFailure(
                    call: Call<News?>,
                    t: Throwable
                ) {
                    binding.progressCircular.isVisible = false
                    Log.d("ArticleList", t.message.toString())
                }
            })
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
            item.itemId -> {
                signOut()
                return true
            }
            item.itemId -> {
                val i = Intent(this, SettingsActivity::class.java)
                i.putExtra("activity", "main")
                i.putExtra("category", category)
                startActivity(i)
                finish()
                return true
            }
            item.itemId -> {
                startActivity(Intent(this, FavouritesActivity::class.java))
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}