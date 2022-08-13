package com.example.sleeptracker.sleepquality

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sleeptracker.database.SleepDatabaseDao
import kotlinx.coroutines.launch

class SleepQualityViewModel(
    private val sleepNightKey: Long = 0L,
    val database: SleepDatabaseDao
) : ViewModel() {
    fun onSetSleepQuality(quality: Int) {
        viewModelScope.launch {
            val tonight = database.get(sleepNightKey) ?: return@withContext
            tonight.sleepQuality = quality
            database.update(tonight)
            _navigateToSleepTracker.value = true
        }
    }
}