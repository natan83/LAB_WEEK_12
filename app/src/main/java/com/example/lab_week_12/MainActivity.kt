package com.example.lab_week_12

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
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

        // fetch movies from the API
        // lifecycleScope is a lifecycle-aware coroutine scope
        lifecycleScope.launch {
            // repeatOnLifecycle is a lifecycle-aware coroutine builder
            // Lifecycle.State.STARTED means that the coroutine will run
            // when the activity is started
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    // collect the list of movies from the StateFlow
                    movieViewModel.popularMovies.collect { movies ->
                        // Get current year
                        val currentYear = Calendar.getInstance().get(Calendar.YEAR).toString()

                        // Filter movies by current year and sort by popularity descending
                        val filteredMovies = movies
                            .filter { movie ->
                                // Filter only movies released in current year (2025)
                                movie.releaseDate?.startsWith(currentYear) == true
                            }
                            .sortedByDescending { it.popularity }

                        // add the filtered and sorted list to the adapter
                        movieAdapter.addMovies(filteredMovies)
                    }
                }
                launch {
                    // collect the error message from the StateFlow
                    movieViewModel.error.collect { error ->
                        // if an error occurs, show a Snackbar with the error
                        // message
                        if (error.isNotEmpty()) {
                            Snackbar.make(
                                recyclerView, error, Snackbar.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }
    }
}