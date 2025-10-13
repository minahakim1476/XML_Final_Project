package com.example.xmlfinalproject

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.xmlfinalproject.databinding.ArticleItemBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class ArticleAdapter(val activity: Activity, val news: News) :
    RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    private val firestore = Firebase.firestore

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ArticleItemBinding.inflate(activity.layoutInflater, parent, false)
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val currentArticle = news.articles[position]

        holder.binding.apply {
            val editTitle = currentArticle.title.length.let {
                if (it > 20) currentArticle.title.substring(
                    0,
                    20
                ) + "..." else currentArticle.title
            }
            articleTitle.text = editTitle
            articleCard.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, currentArticle.url.toUri())
                activity.startActivity(intent)
            }

            Glide.with(articleImage.context)
                .load(currentArticle.urlToImage)
                .transition(DrawableTransitionOptions.withCrossFade(500))
                .placeholder(R.drawable.ic_launcher_background)
                .into(articleImage)

            shareBtn.setOnClickListener {
                ShareCompat.IntentBuilder(activity)
                    .setType("text/plain")
                    .setChooserTitle("Share via...")
                    .setText(currentArticle.url)
                    .startChooser()
            }

            favouriteBtn.setOnClickListener {
                saveToFirestore(currentArticle)
            }
        }
    }

    override fun getItemCount(): Int = news.articles.size

    class ArticleViewHolder(val binding: ArticleItemBinding) : RecyclerView.ViewHolder(binding.root)

    private fun saveToFirestore(article: Article) {

//        val articleData = hashMapOf(
//            "title" to article.title,
//            "url" to article.url
//        )
        //Log.d("trace", "instace: ${firestore}")

        val articleData = FavouriteArticle(
            title = article.title,
            url = article.url
        )

        Log.d("trace", "What happened : ${article.title}")

        firestore.collection("favourites")
            .add(articleData)
            .addOnSuccessListener { documentReference ->
                Toast.makeText(
                    activity,
                    "Article saved to favourites successfully!",
                    Toast.LENGTH_SHORT
                ).show()
                Log.d("trace", "saved in fire store")
            }
            .addOnFailureListener { e ->
                Toast.makeText(
                    activity,
                    "Error to saving article: {${e.message}}",
                    Toast.LENGTH_SHORT
                ).show()
                Log.d("trace", "failed: {${e.message}}")
            }
            .addOnCompleteListener { task ->
                Log.d("trace", "COMPLETE: Success=${task.isSuccessful}")
            }
    }
}