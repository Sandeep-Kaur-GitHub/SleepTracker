package com.example.sleeptracker.sleeptracker

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope

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
    private var night = databaseDao.getAllNights()
    val nightsString = Transformations.map(night) { nights ->
        formatNights(nights, application.resources)
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
    }
}
