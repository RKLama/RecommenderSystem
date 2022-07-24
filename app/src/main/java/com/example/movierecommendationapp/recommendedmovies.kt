package com.example.movierecommendationapp

import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.os.Bundle
import android.widget.ImageView
import com.example.movierecommendationapp.R
import java.util.ArrayList

class recommendedmovies : AppCompatActivity() {
    var tv1: TextView? = null
    var tv2: TextView? = null
    var tv3: TextView? = null
    var tv4: TextView? = null
    var tv5: TextView? = null
    var tv6: TextView? = null
    var iv1: ImageView? = null
    var iv2: ImageView? = null
    var iv3: ImageView? = null
    var iv4: ImageView? = null
    var iv5: ImageView? = null
    var iv6: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recommendedmovies)
        tv1 = findViewById(R.id.textview1)
        tv2 = findViewById(R.id.textview2)
        tv3 = findViewById(R.id.textview3)
        tv4 = findViewById(R.id.textview4)
        tv5 = findViewById(R.id.textview5)
        tv6 = findViewById(R.id.textview6)
        iv1 = findViewById(R.id.image1)
        iv2 = findViewById(R.id.image2)
        iv3 = findViewById(R.id.image3)
        iv4 = findViewById(R.id.image4)
        iv5 = findViewById(R.id.image5)
        iv6 = findViewById(R.id.image6)
        val bundle = intent.extras
        val mylist = intent.getSerializableExtra("mylist") as ArrayList<String>?
        val s = mylist!!.size
        for (i in 0 until s) {
            if (i == 0) {
                if (mylist[i] == "comedy") {
                    tv1.setText("Hang Over\n\nComedy\n\nIMDB:9.1/10")
                    iv1.setImageResource(R.drawable.hangover)
                }
                if (mylist[i] == "thriller") {
                    tv1.setText("Curse of the Nun\n\nThriller\n\nIMDB:8.5/10")
                    iv1.setImageResource(R.drawable.curseofthenun)
                }
                if (mylist[i] == "action") {
                    tv1.setText("Skylines\n\nAction\n\nIMDB:7.9/10")
                    iv1.setImageResource(R.drawable.skylines)
                }
                if (mylist[i] == "horror") {
                    tv1.setText("Conjuring 3\n\nHorror\n\nIMDB:8.1/10")
                    iv1.setImageResource(R.drawable.conjuring3)
                }
                if (mylist[i] == "romance") {
                    tv1.setText("Titanic\n\nRomance\n\nIMDB:9.5/10")
                    iv1.setImageResource(R.drawable.titanic)
                }
                if (mylist[i] == "mystery") {
                    tv1.setText("Zodiac\n\nMystery\n\nIMDB:6.9/10")
                    iv1.setImageResource(R.drawable.zodiacmysery)
                }
            }
            if (i == 1) {
                if (mylist[i] == "comedy") {
                    tv2.setText("Hang Over\n\nComedy\n\nIMDB:9.1/10")
                    iv2.setImageResource(R.drawable.hangover)
                }
                if (mylist[i] == "thriller") {
                    tv2.setText("Curse of the Nun\n\nThriller\n\nIMDB:8.5/10")
                    iv2.setImageResource(R.drawable.curseofthenun)
                }
                if (mylist[i] == "action") {
                    tv2.setText("Skylines\n\nAction\n\nIMDB:7.9/10")
                    iv2.setImageResource(R.drawable.skylines)
                }
                if (mylist[i] == "horror") {
                    tv2.setText("Conjuring 3\n\nHorror\n\nIMDB:8.1/10")
                    iv2.setImageResource(R.drawable.conjuring3)
                }
                if (mylist[i] == "romance") {
                    tv2.setText("Titanic\n\nRomance\n\nIMDB:9.5/10")
                    iv2.setImageResource(R.drawable.titanic)
                }
                if (mylist[i] == "mystery") {
                    tv2.setText("Zodiac\n\nMystery\n\nIMDB:6.9/10")
                    iv2.setImageResource(R.drawable.zodiacmysery)
                }
            }
            if (i == 2) {
                if (mylist[i] == "comedy") {
                    tv3.setText("Hang Over\n\nComedy\n\nIMDB:9.1/10")
                    iv3.setImageResource(R.drawable.hangover)
                }
                if (mylist[i] == "thriller") {
                    tv3.setText("Curse of the Nun\n\nThriller\n\nIMDB:8.5/10")
                    iv3.setImageResource(R.drawable.curseofthenun)
                }
                if (mylist[i] == "action") {
                    tv3.setText("Skylines\n\nAction\n\nIMDB:7.9/10")
                    iv3.setImageResource(R.drawable.skylines)
                }
                if (mylist[i] == "horror") {
                    tv3.setText("Conjuring 3\n\nHorror\n\nIMDB:8.1/10")
                    iv3.setImageResource(R.drawable.conjuring3)
                }
                if (mylist[i] == "romance") {
                    tv3.setText("Titanic\n\nRomance\n\nIMDB:9.5/10")
                    iv3.setImageResource(R.drawable.titanic)
                }
                if (mylist[i] == "mystery") {
                    tv3.setText("Zodiac\n\nMystery\n\nIMDB:6.9/10")
                    iv3.setImageResource(R.drawable.zodiacmysery)
                }
            }
            if (i == 3) {
                if (mylist[i] == "comedy") {
                    tv4.setText("Hang Over\n\nComedy\n\nIMDB:9.1/10")
                    iv4.setImageResource(R.drawable.hangover)
                }
                if (mylist[i] == "thriller") {
                    tv4.setText("Curse of the Nun\n\nThriller\n\nIMDB:8.5/10")
                    iv4.setImageResource(R.drawable.curseofthenun)
                }
                if (mylist[i] == "action") {
                    tv4.setText("Skylines\n\nAction\n\nIMDB:7.9/10")
                    iv4.setImageResource(R.drawable.skylines)
                }
                if (mylist[i] == "horror") {
                    tv4.setText("Conjuring 3\n\nHorror\n\nIMDB:8.1/10")
                    iv4.setImageResource(R.drawable.conjuring3)
                }
                if (mylist[i] == "romance") {
                    tv4.setText("Titanic\n\nRomance\n\nIMDB:9.5/10")
                    iv4.setImageResource(R.drawable.titanic)
                }
                if (mylist[i] == "mystery") {
                    tv4.setText("Zodiac\n\nMystery\n\nIMDB:6.9/10")
                    iv4.setImageResource(R.drawable.zodiacmysery)
                }
            }
            if (i == 4) {
                if (mylist[i] == "comedy") {
                    tv5.setText("Hang Over\n\nComedy\n\nIMDB:9.1/10")
                    iv5.setImageResource(R.drawable.hangover)
                }
                if (mylist[i] == "thriller") {
                    tv5.setText("Curse of the Nun\n\nThriller\n\nIMDB:8.5/10")
                    iv5.setImageResource(R.drawable.curseofthenun)
                }
                if (mylist[i] == "action") {
                    tv5.setText("Skylines\n\nAction\n\nIMDB:7.9/10")
                    iv5.setImageResource(R.drawable.skylines)
                }
                if (mylist[i] == "horror") {
                    tv5.setText("Conjuring 3\n\nHorror\n\nIMDB:8.1/10")
                    iv5.setImageResource(R.drawable.conjuring3)
                }
                if (mylist[i] == "romance") {
                    tv5.setText("Titanic\n\nRomance\n\nIMDB:9.5/10")
                    iv5.setImageResource(R.drawable.titanic)
                }
                if (mylist[i] == "mystery") {
                    tv5.setText("Zodiac\n\nMystery\n\nIMDB:6.9/10")
                    iv5.setImageResource(R.drawable.zodiacmysery)
                }
            }
            if (i == 5) {
                if (mylist[i] == "comedy") {
                    tv6.setText("Hang Over\n\nComedy\n\nIMDB:9.1/10")
                    iv6.setImageResource(R.drawable.hangover)
                }
                if (mylist[i] == "thriller") {
                    tv6.setText("Curse of the Nun\n\nThriller\n\nIMDB:8.5/10")
                    iv6.setImageResource(R.drawable.curseofthenun)
                }
                if (mylist[i] == "action") {
                    tv6.setText("Skylines\n\nAction\n\nIMDB:7.9/10")
                    iv6.setImageResource(R.drawable.skylines)
                }
                if (mylist[i] == "horror") {
                    tv6.setText("Conjuring 3\n\nHorror\n\nIMDB:8.1/10")
                    iv6.setImageResource(R.drawable.conjuring3)
                }
                if (mylist[i] == "romance") {
                    tv6.setText("Titanic\n\nRomance\n\nIMDB:9.5/10")
                    iv6.setImageResource(R.drawable.titanic)
                }
                if (mylist[i] == "mystery") {
                    tv6.setText("Zodiac\n\nMystery\n\nIMDB:6.9/10")
                    iv6.setImageResource(R.drawable.zodiacmysery)
                }
            }
        }
    }
}