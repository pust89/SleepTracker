package com.pustovit.trackmysleepquality.sleeptracker

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pustovit.trackmysleepquality.database.SleepNightDao

class SleepTrackerFragmentFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SleepTrackerViewModel::class.java)) {
            return SleepTrackerViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}