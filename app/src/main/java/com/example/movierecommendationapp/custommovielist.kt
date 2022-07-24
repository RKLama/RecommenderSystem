package com.example.movierecommendationapp

import android.widget.ArrayAdapter
import android.app.Activity
import android.content.Context
import com.example.movierecommendationapp.R
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView

class custommovielist : ArrayAdapter<Any?> {
    lateinit var moviename: Array<String>
    lateinit var movietype: Array<String>
    lateinit var imdb: Array<String>
    lateinit var imageid: Array<Int>
    var context: Activity? = null

    constructor(
        context: Activity?,
        moviename: Array<String>,
        movietype: Array<String>,
        imdb: Array<String>,
        imageid: Array<Int>
    ) : super(
        context!!, R.layout.row_item, moviename
    ) {
        this.context = context
        this.moviename = moviename
        this.movietype = movietype
        this.imdb = imdb
        this.imageid = imageid
    }

    constructor(context: Context, resource: Int) : super(context, resource) {}

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var row = convertView
        val inflater = context!!.layoutInflater
        if (convertView == null) row = inflater.inflate(R.layout.row_item, null, true)
        val textViewmovie = row!!.findViewById<View>(R.id.textViewmovie) as TextView
        val textViewtype = row.findViewById<View>(R.id.textViewtype) as TextView
        val textViewimdb = row.findViewById<View>(R.id.textViewimdb) as TextView
        val imageFlag = row.findViewById<View>(R.id.imageViewFlag) as ImageView
        textViewmovie.text = moviename[position]
        textViewtype.text = movietype[position]
        textViewimdb.text = imdb[position]
        imageFlag.setImageResource(imageid[position])
        return row
    }
}