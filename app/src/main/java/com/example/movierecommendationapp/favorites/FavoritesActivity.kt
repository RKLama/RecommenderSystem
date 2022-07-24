package com.example.movierecommendationapp.favorites

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.movierecommendationapp.favorites.FavoritesAdapter
import com.example.movierecommendationapp.favorites.FavoritesDetails
import com.google.firebase.firestore.FirebaseFirestore
import com.android.volley.RequestQueue
import android.widget.ProgressBar
import android.content.SharedPreferences
import org.json.JSONObject
import com.android.volley.toolbox.JsonObjectRequest
import android.os.Bundle
import android.view.View
import com.example.movierecommendationapp.R
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.BaseTransientBottomBar.BaseCallback
import com.google.android.gms.tasks.OnSuccessListener
import android.widget.Toast
import com.google.android.gms.tasks.OnFailureListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.Volley
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.DocumentSnapshot
import org.json.JSONArray
import org.json.JSONException
import com.android.volley.VolleyError
import java.lang.Exception
import java.util.ArrayList

class FavoritesActivity : AppCompatActivity() {
    private var recyclerView: RecyclerView? = null
    private var adapter: FavoritesAdapter? = null
    private var moviesArrayList: ArrayList<FavoritesDetails?>? = null
    private var favMovieDocIds: ArrayList<String?>? = null
    var loggedInEmail: String? = null
    var db = FirebaseFirestore.getInstance()
    var allFavMovieIds: ArrayList<String?>? = null
    private var mQueue: RequestQueue? = null
    private var progressBar: ProgressBar? = null
    var prefs: SharedPreferences? = null
    var postMovieIds: JSONObject? = null
    var jsonObjectRequest: JsonObjectRequest? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)
        InitializeCardView()
    }

    var deletedMovie: FavoritesDetails? = null
    var deletedMovieFavDocId: String? = null
    var simpleCallback: ItemTouchHelper.SimpleCallback =
        object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                deletedMovie = moviesArrayList!![position]
                deletedMovieFavDocId = favMovieDocIds!![position]
                moviesArrayList!!.removeAt(position)
                favMovieDocIds!!.removeAt(position)
                adapter!!.notifyItemRemoved(position)
                val deleteSnackbar = Snackbar.make(
                    recyclerView!!,
                    deletedMovie!!.movieName + " removed",
                    Snackbar.LENGTH_LONG
                ).setAction("Undo") {
                    moviesArrayList!!.add(position, deletedMovie)
                    favMovieDocIds!!.add(position, deletedMovieFavDocId)
                    adapter!!.notifyDataSetChanged()
                }
                deleteSnackbar.animationMode = BaseTransientBottomBar.ANIMATION_MODE_SLIDE
                deleteSnackbar.addCallback(object : BaseCallback<Snackbar?>() {
                    override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                        if (event == DISMISS_EVENT_CONSECUTIVE || event == DISMISS_EVENT_TIMEOUT) {
                            //Delete the favorite movie entry from Firebase
                            db.collection("favoriteMovies").document(deletedMovieFavDocId!!)
                                .delete()
                                .addOnSuccessListener {
                                    Toast.makeText(
                                        this@FavoritesActivity,
                                        "Deleted '" + deletedMovie!!.movieName + "' from favorites",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                .addOnFailureListener { e -> e.printStackTrace() }
                        }
                        super.onDismissed(transientBottomBar, event)
                    }
                })
                deleteSnackbar.show()
            }
        }

    private fun InitializeCardView() {
        allFavMovieIds = ArrayList()
        favMovieDocIds = ArrayList()
        prefs = getSharedPreferences("LoggedIn", MODE_PRIVATE)
        loggedInEmail = prefs.getString("loggedin", "NoEmailLoggedIn")
        recyclerView = findViewById(R.id.favoriterecyclerViewCard)
        recyclerView.setLayoutManager(LinearLayoutManager(this))
        moviesArrayList = ArrayList()
        progressBar = findViewById<View>(R.id.progress_bar) as ProgressBar
        mQueue = Volley.newRequestQueue(this@FavoritesActivity)
        progressBarVisible()
        fetchResponseFromDatabaseAndAPI()
        adapter = FavoritesAdapter(this@FavoritesActivity, moviesArrayList)
        recyclerView.setAdapter(adapter)
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    fun progressBarVisible() {
        progressBar!!.visibility = View.VISIBLE
        recyclerView!!.visibility = View.INVISIBLE
    }

    fun progressBarInVisible() {
        progressBar!!.visibility = View.INVISIBLE
        recyclerView!!.visibility = View.VISIBLE
    }

    private fun fetchResponseFromDatabaseAndAPI() {
        //Get favorites info from DB
        db.collection("favoriteMovies").whereEqualTo("email", loggedInEmail).get()
            .addOnSuccessListener { queryDocumentSnapshots ->
                postMovieIds = JSONObject()
                val allSnaps = queryDocumentSnapshots.documents
                for (snapshot in allSnaps) {
                    favMovieDocIds!!.add(snapshot.id)
                    allFavMovieIds!!.add(snapshot.getString("favMovieId"))
                }
                //    tempHolder.setText(ans);
                try {
                    val jsonArray = JSONArray(allFavMovieIds)
                    postMovieIds!!.put("movieIds", jsonArray)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                if (allFavMovieIds!!.size > 0) {
                    //Send POST request to backend
                    val URL = "https://movie-recommender-fastapi.herokuapp.com/favorites/"
                    // String URL="http://127.0.0.1:8000/favorites";
                    println(postMovieIds)
                    jsonObjectRequest =
                        JsonObjectRequest(Request.Method.POST, URL, postMovieIds, { response ->
                            println(response)
                            val keys = response.names()
                            progressBarInVisible()
                            for (i in 0 until keys.length()) {
                                try {
                                    val movieId = keys[i] as String
                                    val movie_name = response.getJSONObject(keys[i] as String)
                                        .getString("original_title")
                                    val description = response.getJSONObject(keys[i] as String)
                                        .getString("overview")
                                    val ratings =
                                        "Ratings : " + response.getJSONObject(keys[i] as String)
                                            .getString("vote_average") + "/10"
                                    val backDropPath = response.getJSONObject(keys[i] as String)
                                        .getString("backdrop_path")
                                    val posterPath = response.getJSONObject(keys[i] as String)
                                        .getString("poster_path")
                                    if (description.length > 0) {
                                        CreateDataForCards(
                                            movieId,
                                            movie_name,
                                            description,
                                            ratings,
                                            backDropPath,
                                            posterPath
                                        )
                                        adapter!!.notifyDataSetChanged()
                                    }
                                } catch (e: JSONException) {
                                    e.printStackTrace()
                                }
                            }
                        }) { error -> error.printStackTrace() }
                    mQueue!!.add(jsonObjectRequest)
                } else {
                    progressBarInVisible()
                }
            }
    }

    private fun CreateDataForCards(
        movieId: String,
        movieName: String,
        ratings: String,
        movieDescription: String,
        backDropPath: String,
        posterPath: String
    ) {
        val movie = FavoritesDetails(
            movieId,
            movieName,
            ratings,
            movieDescription,
            backDropPath,
            posterPath
        )
        println("$movieId $movieName $ratings $movieDescription $backDropPath $posterPath")
        moviesArrayList!!.add(movie)
    }
}