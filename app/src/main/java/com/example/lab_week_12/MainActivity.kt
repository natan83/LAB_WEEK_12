package com.example.lab_week_12

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import java.util.Calendar

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

        val movieRepository = (application as MovieApplication).movieRepository
        val movieViewModel = ViewModelProvider(
            this, object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    @Suppress("UNCHECKED_CAST")
                    return MovieViewModel(movieRepository) as T
                }
            }
        )[MovieViewModel::class.java]

        movieViewModel.popularMovies.observe(this) { popularMovies ->
            val currentYear = Calendar.getInstance().get(Calendar.YEAR).toString()
            movieAdapter.addMovies(
                popularMovies
                    .filter { movie ->
                        movie.releaseDate?.startsWith(currentYear) == true
                    }
                    .sortedByDescending { it.popularity }
            )
        }

        movieViewModel.error.observe(this) { error ->
            if (error.isNotEmpty()) {
                Snackbar.make(recyclerView, error, Snackbar.LENGTH_LONG).show()
            }
        }
    }
}