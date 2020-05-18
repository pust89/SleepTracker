package com.pustovit.trackmysleepquality.sleeptracker

import android.app.Application
import android.text.Spanned
import androidx.lifecycle.LiveData

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.pustovit.trackmysleepquality.database.SleepDatabase
import com.pustovit.trackmysleepquality.database.SleepNight
import com.pustovit.trackmysleepquality.database.SleepNightDao
import com.pustovit.trackmysleepquality.formatNights
import kotlinx.coroutines.*

class SleepTrackerViewModel(
    application: Application
) : ViewModel() {

    private val sleepDao: SleepNightDao =
        SleepDatabase.getInstance(application.applicationContext).getSleepNightDao()

    private var viewModelJob = Job();


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var tonight = MutableLiveData<SleepNight?>()

    private val _nights = sleepDao.getAllNights()

    val nights: LiveData<List<SleepNight>>
        get() = _nights


    val nightString: LiveData<Spanned> =
        Transformations.map(_nights) { nightsList: List<SleepNight> ->
            formatNights(nightsList, application.resources)
        }

    val startButtonVisible: LiveData<Boolean> = Transformations.map(tonight) {
        null == it
    }

    val stopButtonVisible: LiveData<Boolean> = Transformations.map(tonight) {
        null != it
    }

    val clearButtonVisible: LiveData<Boolean> = Transformations.map(_nights) {
        it?.isNotEmpty()
    }

    private var _showSnackbarEvent = MutableLiveData<Boolean>()

    val showSnackBarEvent: LiveData<Boolean>
        get() = _showSnackbarEvent

    fun doneShowingSnackbar() {
        _showSnackbarEvent.value = false
    }

    init {
        initializeTonight()
    }

    private fun initializeTonight() {
        uiScope.launch {
            tonight.value = getTonightFromDatabase()
        }
    }

    private suspend fun getTonightFromDatabase(): SleepNight? {
        return withContext(Dispatchers.IO) {
            var night = sleepDao.getTonight()
            if (night?.endTimeMilli != night?.startTimeMilli) {
                night = null
            }
            night
        }
    }

    fun onStartTracking() {
        uiScope.launch {
            val newNight = SleepNight()
            insert(newNight)
            tonight.value = getTonightFromDatabase()
        }
    }

    private suspend fun insert(night: SleepNight) {
        withContext(Dispatchers.IO) {
            sleepDao.insert(night)
        }
    }

    private val _navigateToSleepQuality = MutableLiveData<SleepNight>()

    val navigateToSleepQuality: LiveData<SleepNight>
        get() = _navigateToSleepQuality

    fun doneNavigating() {
        _navigateToSleepQuality.value = null
    }

    fun onStopTracking() {
        uiScope.launch {
            val oldNight = tonight.value ?: return@launch
            oldNight.endTimeMilli = System.currentTimeMillis()
            update(oldNight)
            _navigateToSleepQuality.value = oldNight
        }
    }

    private suspend fun update(night: SleepNight) {
        withContext(Dispatchers.IO) {
            sleepDao.update(night)
        }
    }

    fun onClear() {
        uiScope.launch {
            clear()
            tonight.value = null
        }
        _showSnackbarEvent.value = true
    }

    private suspend fun clear() {
        withContext(Dispatchers.IO) {
            sleepDao.clear()
        }
    }




    private val _navigateToSleepQualityDetail = MutableLiveData<Long>()

    val navigateToSleepQualityDetail:LiveData<Long>
        get() = _navigateToSleepQualityDetail

    fun onSleepNightClicked(id: Long) {
        _navigateToSleepQualityDetail.value = id
    }

    fun onSleepQualityDetailNavigated() {
        _navigateToSleepQualityDetail.value = null
    }
}
