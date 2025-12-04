package com.example.lab_week_12

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MovieAdapter(private val onMovieClick: (Movie) -> Unit) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private val movies = mutableListOf<Movie>()

    fun addMovies(newMovies: List<Movie>) {
        movies.clear()
        movies.addAll(newMovies)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount() = movies.size

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val posterImageView: ImageView = itemView.findViewById(R.id.movie_poster)
        private val titleTextView: TextView = itemView.findViewById(R.id.movie_title)
        private val releaseDateTextView: TextView = itemView.findViewById(R.id.movie_release_date)

        fun bind(movie: Movie) {
            titleTextView.text = movie.title
            releaseDateTextView.text = movie.releaseDate

            val posterUrl = "https://image.tmdb.org/t/p/w500${movie.posterPath}"
            Glide.with(itemView.context)
                .load(posterUrl)
                .into(posterImageView)

            itemView.setOnClickListener {
                onMovieClick(movie)
            }
        }
    }
}