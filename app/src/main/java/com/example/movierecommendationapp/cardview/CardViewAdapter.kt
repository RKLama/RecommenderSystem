package com.example.movierecommendationapp.cardview

import android.animation.Animator
import com.example.movierecommendationapp.cardview.CardMovieDetails.backDropPath
import com.example.movierecommendationapp.cardview.CardMovieDetails.movieName
import com.example.movierecommendationapp.cardview.CardMovieDetails.ratings
import com.example.movierecommendationapp.cardview.CardMovieDetails.movieDescription
import com.example.movierecommendationapp.cardview.CardMovieDetails.movieId
import com.example.movierecommendationapp.cardview.CardMovieDetails
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import android.view.ViewGroup
import android.view.LayoutInflater
import com.example.movierecommendationapp.R
import com.bumptech.glide.Glide
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import android.content.SharedPreferences
import com.google.android.gms.tasks.OnSuccessListener
import android.widget.Toast
import com.google.android.gms.tasks.OnFailureListener
import android.animation.ObjectAnimator
import android.view.animation.AccelerateDecelerateInterpolator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import com.example.movierecommendationapp.recommendations.RecommendationActivity
import java.util.*

class CardViewAdapter     // Constructor
    (// Card Adapter Class
    private val context: Context, private val movies: ArrayList<CardMovieDetails>
) : RecyclerView.Adapter<CardViewAdapter.MovieViewHolder>() {
    var db = FirebaseFirestore.getInstance()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.card, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        Glide.with(holder.movieImg).load(movies[position].backDropPath).into(holder.movieImg)
        holder.setDetails(movie, holder, position)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    internal inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var clicked: Int
        private val movieName: TextView
        private val movieRatings: TextView
        private val movieDescription: TextView
        private val movieImg: ImageView
        private val addToFavButton: MaterialButton
        private val recommendationsBtn: MaterialButton
        fun setDetails(details: CardMovieDetails, holder: MovieViewHolder, position: Int) {
            movieName.text = String.format(Locale.US, details.movieName)
            movieRatings.text = String.format(Locale.US, details.ratings)
            movieDescription.text = String.format(Locale.US, details.movieDescription)
            addToFavButton.setOnClickListener {
                val prefs = context.getSharedPreferences("LoggedIn", Context.MODE_PRIVATE)
                val loggedInUser = prefs.getString("loggedin", "NoUserLoggedIn")
                val newFavorite: MutableMap<String, Any?> = HashMap()
                newFavorite["email"] = loggedInUser
                newFavorite["favMovieId"] = details.movieId
                db.collection("favoriteMovies").add(newFavorite).addOnSuccessListener {
                    Toast.makeText(
                        context,
                        "Added " + details.movieName + " as favorite",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                    .addOnFailureListener {
                        Toast.makeText(context, "Error DB", Toast.LENGTH_SHORT).show()
                    }
                addToFavButton.isEnabled = false
                addToFavButton.setTextColor(1)
            }
            movieImg.setOnClickListener {
                val oa1 = ObjectAnimator.ofFloat(movieImg, "scaleX", 1f, 0f)
                val oa2 = ObjectAnimator.ofFloat(movieImg, "scaleX", 0f, 1f)
                oa1.interpolator = DecelerateInterpolator()
                oa2.interpolator = AccelerateDecelerateInterpolator()
                oa1.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                        clicked = if (clicked == 0) {
                            movieImg.setImageResource(R.drawable.conjuring3)
                            1
                        } else {
                            Glide.with(holder.movieImg).load(movies[position].backDropPath)
                                .into(holder.movieImg)
                            0
                        }
                        oa2.start()
                    }
                })
                oa1.start()
            }
            recommendationsBtn.setOnClickListener {
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
            addToFavButton = itemView.findViewById(R.id.addToFavButton)
            recommendationsBtn = itemView.findViewById(R.id.recommendationsBtn)
            clicked = 0
        }
    }
}