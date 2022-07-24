package com.example.movierecommendationapp

import android.app.Activity
import com.example.movierecommendationapp.R
import android.os.Bundle
import android.graphics.Typeface
import com.example.movierecommendationapp.custommovielist
import android.widget.AdapterView.OnItemClickListener
import android.content.Intent
import android.view.View
import android.widget.*
import com.example.movierecommendationapp.recommendedmovies
import java.util.ArrayList

class movieslist : Activity() {
    var listView: ListView? = null
    var b1: Button? = null
    var moviename = arrayOf(
        "Johnny English Strikes\nAgain",
        "The Tomorrow War",
        "The Dead Don't Die",
        "Forensic",
        "Tamasha",
        "Zodiac"
    )
    var mylist = ArrayList<String>()
    var movietype = arrayOf(
        "comedy",
        "action",
        "horror",
        "thriller",
        "romance",
        "mystery"
    )
    var imdb = arrayOf(
        "IMDB:7.4",
        "IMDB:9.1",
        "IMDB:6.8",
        "IMDB:8.2",
        "IMDB:8.4",
        "IMDB:7.9"
    )
    var imageid = arrayOf(
        R.drawable.comedy,
        R.drawable.action,
        R.drawable.horror,
        R.drawable.thriller,
        R.drawable.romance,
        R.drawable.mystery
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movieslist)
        // Setting header
        val textView = TextView(this)
        textView.setTypeface(Typeface.DEFAULT_BOLD)
        val listView = findViewById<View>(android.R.id.list) as ListView
        b1 = findViewById(R.id.button)
        listView.addHeaderView(textView)

        // For populating list data
        val custommovielist = custommovielist(this, moviename, movietype, imdb, imageid)
        listView.adapter = custommovielist
        listView.onItemClickListener = OnItemClickListener { adapterView, view, position, l ->
            listView.setItemChecked(position, true)
            Toast.makeText(
                applicationContext,
                "You Selected " + moviename[position - 1],
                Toast.LENGTH_SHORT
            ).show()
            mylist.add(movietype[position - 1])
        }
        b1.setOnClickListener(View.OnClickListener {
            val intent = Intent(applicationContext, recommendedmovies::class.java)
            intent.putExtra("mylist", mylist)
            startActivity(intent)
        })
    }
}