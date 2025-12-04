package com.example.lab_week_12

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class MovieRepository(private val movieService: MovieService) {
    private val apiKey = "0c9c3b6c783f8b301c3ec456c88adb0e"

    // LiveData that contains a list of movies
    private val movieLiveData = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>>
        get() = movieLiveData

    // LiveData that contains an error message
    private val errorLiveData = MutableLiveData<String>()
    val error: LiveData<String>
        get() = errorLiveData

    // fetch movies from the API
    suspend fun fetchMovies() {
        try {
            // get the list of popular movies from the API
            val popularMovies = movieService.getPopularMovies(apiKey)
            movieLiveData.postValue(popularMovies.results)
        } catch (exception: Exception) {
            // if an error occurs, post the error message to the
            // errorLiveData
            errorLiveData.postValue(
                "An error occurred: ${exception.message}"
            )
        }
    }
}