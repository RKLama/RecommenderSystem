package com.example.movierecommendationapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.movierecommendationapp.R
import android.content.Intent
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private final var startBtn: Button? = null
    var i = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startBtn = findViewById(R.id.startBtn)

    }

    override fun onClick(v: View?) {

    }

//    override fun onClick(v: View) {
//        if (v.id == startBtn!!.id) {
//            val intent = Intent(this@MainActivity, UserLoginSignupActivity::class.java)
//            startActivity(intent)
//        }
//    }
}