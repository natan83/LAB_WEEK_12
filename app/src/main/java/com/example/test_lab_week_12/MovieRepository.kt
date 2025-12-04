package com.example.test_lab_week_12

class MovieRepository(private val movieService: MovieService) {
    private val apiKey = "your_api_key_here"

    fun fetchMovies(): List<Movie> {
        val popularMovies = movieService.getPopularMovies(apiKey)
        return popularMovies.results
    }
}