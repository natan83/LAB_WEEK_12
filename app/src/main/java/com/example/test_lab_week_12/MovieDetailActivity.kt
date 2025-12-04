package com.example.test_lab_week_12

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class MovieDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        val title = intent.getStringExtra("MOVIE_TITLE")
        val overview = intent.getStringExtra("MOVIE_OVERVIEW")
        val posterPath = intent.getStringExtra("MOVIE_POSTER")
        val backdropPath = intent.getStringExtra("MOVIE_BACKDROP")
        val releaseDate = intent.getStringExtra("MOVIE_RELEASE_DATE")
        val rating = intent.getDoubleExtra("MOVIE_RATING", 0.0)

        findViewById<TextView>(R.id.detail_title).text = title
        findViewById<TextView>(R.id.detail_overview).text = overview
        findViewById<TextView>(R.id.detail_release_date).text = "Release: $releaseDate"
        findViewById<TextView>(R.id.detail_rating).text = "Rating: $rating"

        val backdropUrl = "https://image.tmdb.org/t/p/w500$backdropPath"
        Glide.with(this)
            .load(backdropUrl)
            .into(findViewById<ImageView>(R.id.detail_backdrop))
    }
}