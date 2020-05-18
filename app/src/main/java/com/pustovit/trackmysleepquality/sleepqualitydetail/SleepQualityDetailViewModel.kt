package com.pustovit.trackmysleepquality.sleepqualitydetail

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pustovit.trackmysleepquality.database.SleepDatabase
import com.pustovit.trackmysleepquality.database.SleepNight
import kotlinx.coroutines.*

class SleepQualityDetailViewModel(
    private val sleepId: Long,
    application: Application
) : ViewModel() {
    private val database =
        SleepDatabase.getInstance(application.applicationContext).getSleepNightDao()

    private val viewModelJob = Job()


    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _nightLiveData: MutableLiveData<SleepNight> = MutableLiveData()

    val nightLiveData: LiveData<SleepNight>
        get() = _nightLiveData


    init {
        getSleepNight()
    }

    private fun getSleepNight() {
        uiScope.launch {
            withContext(Dispatchers.IO) {

                val tonight = database.get(sleepId) ?: return@withContext
                _nightLiveData.postValue(tonight)
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private val _navigateToSleepTracker = MutableLiveData<Boolean?>()
    val navigateToSleepTracker : LiveData<Boolean?>
    get() = _navigateToSleepTracker

    fun onSleepTrackerNavigateDone() {
        _navigateToSleepTracker.value = null
    }

    fun onClose() {
        _navigateToSleepTracker.value = true
    }
}
