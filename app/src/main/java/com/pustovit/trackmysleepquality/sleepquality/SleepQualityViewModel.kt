package com.pustovit.trackmysleepquality.sleepquality

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pustovit.trackmysleepquality.database.SleepDatabase
import com.pustovit.trackmysleepquality.database.SleepNightDao
import kotlinx.coroutines.*

class SleepQualityViewModel(
    private val sleepNightKey: Long = 0L,
    private val application: Application

) : ViewModel() {
    private val database: SleepNightDao =
        SleepDatabase.getInstance(application.applicationContext).getSleepNightDao()
    private val viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _navigateToSleepTracker = MutableLiveData<Boolean?>()

    val navigateToSleepTracker: LiveData<Boolean?>
        get() = _navigateToSleepTracker

    fun doneNavigation() {
        _navigateToSleepTracker.value = null
    }

    fun onSetSleepQuality(quality: Int) {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                val tonight = database.get(sleepNightKey) ?: return@withContext
                tonight.sleepQuality = quality
                database.update(tonight)
            }
            _navigateToSleepTracker.value = true
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
