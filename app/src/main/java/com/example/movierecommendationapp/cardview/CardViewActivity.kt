package com.example.movierecommendationapp.cardview

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import android.widget.ProgressBar
import android.os.Bundle
import com.example.movierecommendationapp.R
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.toolbox.Volley
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONException
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.android.volley.Request
import com.example.movierecommendationapp.MainActivity
import com.example.movierecommendationapp.favorites.FavoritesActivity
import java.util.ArrayList

public class CardViewActivity : AppCompatActivity() {
    private var recyclerView: RecyclerView? = null
    private var adapter: CardViewAdapter? = null
    private var moviesArrayList: ArrayList<CardMovieDetails>? = null
    private var mQueue: RequestQueue? = null
    private var progressBar: ProgressBar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.card_views)
        InitializeCardView()
    }

    private fun InitializeCardView() {
        recyclerView = findViewById(R.id.recyclerViewCard)
        recyclerView.setLayoutManager(LinearLayoutManager(this))
        moviesArrayList = ArrayList()
        progressBar = findViewById<View>(R.id.progress_bar) as ProgressBar
        mQueue = Volley.newRequestQueue(this)
        progressBarVisible()
        jsonParse()
        //        CreateDataForCards("Kabhi kushi kabhi gham","6.9","aaa aaa aaa aaa");
        //      CreateDataForCards("Kabhi kushi kabhi gham","6.9","aaa aaa aaa aaa");
        adapter = CardViewAdapter(this, moviesArrayList)
        recyclerView.setAdapter(adapter)
    }

    fun progressBarVisible() {
        progressBar!!.visibility = View.VISIBLE
        recyclerView!!.visibility = View.INVISIBLE
    }

    fun progressBarInVisible() {
        progressBar!!.visibility = View.INVISIBLE
        recyclerView!!.visibility = View.VISIBLE
    }

    private fun jsonParse() {
        val URL = "https://movie-recommender-fastapi.herokuapp.com"
        val request = JsonObjectRequest(Request.Method.GET, URL, null, { response ->
            val keys = response.names()
            progressBarInVisible()
            for (i in 0 until keys.length()) {
                try {
                    val movieId = keys[i] as String
                    val movie_name =
                        response.getJSONObject(keys[i] as String).getString("original_title")
                    val description =
                        response.getJSONObject(keys[i] as String).getString("overview")
                    val ratings = "Ratings : " + response.getJSONObject(keys[i] as String)
                        .getString("vote_average") + "/10"
                    val backDropPath =
                        response.getJSONObject(keys[i] as String).getString("backdrop_path")
                    val posterPath =
                        response.getJSONObject(keys[i] as String).getString("poster_path")
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

            //  Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();
        }) { error -> error.printStackTrace() }
        mQueue!!.add(request)
    }

    private fun CreateDataForCards(
        movieId: String,
        movieName: String,
        ratings: String,
        movieDescription: String,
        backDropPath: String,
        posterPath: String
    ) {
        val movie = CardMovieDetails(
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

    //Header menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.five_movie_screen_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.myProfileItem -> {
                val intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.confirmNoItem -> return true
            R.id.confirmYesItem -> {
                val prefs = getSharedPreferences("LoggedIn", MODE_PRIVATE)
                val editor = prefs.edit()
                editor.clear()
                editor.commit()
                //                prefs = this.getSharedPreferences("Register",MODE_PRIVATE);
//                editor = prefs.edit();
//                editor.clear();
//                editor.commit();
                val logoutIntent = Intent(this, MainActivity::class.java)
                logoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                logoutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(logoutIntent)
                return true
            }
            R.id.favoritesItem -> {
                val favIntent = Intent(this, FavoritesActivity::class.java)
                startActivity(favIntent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}