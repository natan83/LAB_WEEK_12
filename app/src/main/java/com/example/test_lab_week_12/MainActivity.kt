package com.example.test_lab_week_12

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private val movieAdapter = MovieAdapter { movie ->
        val intent = Intent(this, MovieDetailActivity::class.java).apply {
            putExtra("MOVIE_ID", movie.id)
            putExtra("MOVIE_TITLE", movie.title)
            putExtra("MOVIE_OVERVIEW", movie.overview)
            putExtra("MOVIE_POSTER", movie.posterPath)
            putExtra("MOVIE_BACKDROP", movie.backdropPath)
            putExtra("MOVIE_RELEASE_DATE", movie.releaseDate)
            putExtra("MOVIE_RATING", movie.voteAverage)
        }
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.movie_list)
        recyclerView.adapter = movieAdapter

        // Implementation will continue in Commit 2
    }
}