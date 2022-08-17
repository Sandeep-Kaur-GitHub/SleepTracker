package com.example.sleeptracker.sleeptracker

import android.app.Application
import androidx.lifecycle.*

import com.example.sleeptracker.database.SleepDatabase
import com.example.sleeptracker.database.SleepDatabaseDao
import com.example.sleeptracker.database.SleepNight
import com.example.sleeptracker.formatNights
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SleepTrackerViewModel(
    val databaseDao: SleepDatabaseDao,
    application: Application
) : AndroidViewModel(application) {
    private var tonight = MutableLiveData<SleepNight?>()
     var night = databaseDao.getAllNights()
    val nightsString = Transformations.map(night) { nights ->
        formatNights(nights, application.resources)
    }
    val startButtonVisible = Transformations.map(tonight) {
        null == it
    }
    val stopButtonVisible = Transformations.map(tonight) {
        null != it
    }
    val clearButtonVisible = Transformations.map(night) {
        it?.isNotEmpty()
    }

    private var _showSnackbarEvent = MutableLiveData<Boolean>()

    val showSnackBarEvent: LiveData<Boolean>
        get() = _showSnackbarEvent
    fun doneShowingSnackbar() {
        _showSnackbarEvent.value = false
    }


    private val _navigateToSleepQuality = MutableLiveData<SleepNight?>()

    val navigateToSleepQuality: MutableLiveData<SleepNight?>
        get() = _navigateToSleepQuality

    fun doneNavigating() {
        _navigateToSleepQuality.value = null
    }

    init {
        initializeTonight()
    }

    private fun initializeTonight() {
        viewModelScope.launch {
            tonight.value = getTonightFromDatabase()
        }
    }

    private suspend fun getTonightFromDatabase(): SleepNight? {
        var night = databaseDao.getTonight()
        if (night?.endTimeMilli != night?.startTimeMilli) {
            night = null
        }
        return night
    }
    fun onStartTracking()
    {
        viewModelScope.launch {
            val newNight=SleepNight()
            insert(newNight)
            tonight.value=getTonightFromDatabase()
        }
    }
    private suspend fun insert(night: SleepNight){
        databaseDao.insert(night)
    }

    fun onStopTracking(){
        viewModelScope.launch {
            val oldNight=tonight?.value ?: return@launch
            oldNight.endTimeMilli= System.currentTimeMillis()
            update(oldNight)
            _navigateToSleepQuality.value = oldNight
        }
    }
    private suspend fun update(night: SleepNight) {
        databaseDao.update(night)
    }
    fun onClear() {
        viewModelScope.launch {
            clear()
            tonight.value = null
        }
    }

    suspend fun clear() {
        databaseDao.clear()
        _showSnackbarEvent.value = true
    }
    private val _navigateToSleepDataQuality = MutableLiveData<Long?>()
    val navigateToSleepDataQuality
        get() = _navigateToSleepDataQuality

    fun onSleepNightClicked(id: Long){
        _navigateToSleepDataQuality.value = id
    }
    fun onSleepDataQualityNavigated() {
        _navigateToSleepDataQuality.value = null
    }
}
