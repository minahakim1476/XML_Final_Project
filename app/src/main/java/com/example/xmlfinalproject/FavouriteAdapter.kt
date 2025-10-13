package com.example.xmlfinalproject

import android.app.Activity
import android.content.Intent
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.xmlfinalproject.databinding.FavouriteArticleItemBinding

class FavouriteAdapter(val activity: Activity, var favouriteNews: MutableList<FavouriteArticle>) :
    RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder>() {
    class FavouriteViewHolder(val binding: FavouriteArticleItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteAdapter.FavouriteViewHolder {
        val binding = FavouriteArticleItemBinding.inflate(activity.layoutInflater, parent, false)
        return FavouriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavouriteAdapter.FavouriteViewHolder, position: Int) {
        val favourites = favouriteNews[position]

        holder.binding.apply {
            favouriteTitle.text = favourites.title
            favouriteUrl.text = favourites.url

            favouriteCard.setOnClickListener {
                val i = Intent(Intent.ACTION_VIEW, favourites.url.toUri())
                activity.startActivity(i)
            }
        }
    }

    override fun getItemCount() = favouriteNews.size

    fun updateData(newFavourites: MutableList<FavouriteArticle>) {
        favouriteNews.clear()
//        favouriteNews.addAll(newFavourites)
        favouriteNews = newFavourites
        notifyDataSetChanged()
    }
}