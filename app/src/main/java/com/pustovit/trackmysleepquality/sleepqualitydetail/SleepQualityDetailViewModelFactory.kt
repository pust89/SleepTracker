package com.pustovit.trackmysleepquality.sleepqualitydetail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SleepQualityDetailViewModelFactory(
    private val sleepId: Long,
    private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SleepQualityDetailViewModel::class.java)) {
            return SleepQualityDetailViewModel(sleepId, application) as T
        }
        throw IllegalArgumentException("Unknown argument ${modelClass.name}!")
    }
}