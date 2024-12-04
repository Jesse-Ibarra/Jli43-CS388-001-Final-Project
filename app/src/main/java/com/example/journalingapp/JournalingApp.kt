package com.example.journalingapp

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen

class JournalingApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize ThreeTenABP for LocalDate and time handling
        AndroidThreeTen.init(this)
    }
}
