package com.example.movierecommendationapp.favorites

import android.content.Context
import com.example.movierecommendationapp.favorites.FavoritesDetails
import androidx.recyclerview.widget.RecyclerView
import com.example.movierecommendationapp.favorites.FavoritesAdapter.FavoritesViewHolder
import com.google.firebase.firestore.FirebaseFirestore
import android.view.ViewGroup
import android.view.LayoutInflater
import com.example.movierecommendationapp.R
import com.bumptech.glide.Glide
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import android.content.Intent
import android.view.View
import android.widget.ImageView
import com.example.movierecommendationapp.recommendations.RecommendationActivity
import java.util.*

class FavoritesAdapter     // Constructor
    (// Card Adapter Class
    private val context: Context, private val movies: ArrayList<FavoritesDetails>
) : RecyclerView.Adapter<FavoritesViewHolder>() {
    var db = FirebaseFirestore.getInstance()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.favorites_card, parent, false)
        return FavoritesViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        val movie = movies[position]
        Glide.with(holder.movieImg).load(movies[position].backDropPath).into(holder.movieImg)
        holder.setDetails(movie)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    internal inner class FavoritesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val movieName: TextView
        private val movieRatings: TextView
        private val movieDescription: TextView
        private val movieImg: ImageView
        private val showRecommendationsButton: MaterialButton
        fun setDetails(details: FavoritesDetails) {
            movieName.text = String.format(Locale.US, details.movieName)
            movieRatings.text = String.format(Locale.US, details.ratings)
            movieDescription.text = String.format(Locale.US, details.movieDescription)
            showRecommendationsButton.setOnClickListener {
                val intent = Intent(context, RecommendationActivity::class.java)
                intent.putExtra("movie_id", details.movieId)
                context.startActivity(intent)
            }
        }

        init {
            movieImg = itemView.findViewById(R.id.movieImage)
            movieName = itemView.findViewById(R.id.movieName)
            movieRatings = itemView.findViewById(R.id.movieRatings)
            movieDescription = itemView.findViewById(R.id.movieDescription)
            showRecommendationsButton = itemView.findViewById(R.id.showRecommendationsButton)
        }
    }
}