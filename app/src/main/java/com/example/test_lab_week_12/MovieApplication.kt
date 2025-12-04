package com.example.test_lab_week_12

import android.app.Application

class MovieApplication : Application() {
    lateinit var movieRepository: MovieRepository

    override fun onCreate() {
        super.onCreate()
        // Initialization will be done in Commit 2
    }
}