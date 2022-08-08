package com.example.sleeptracker.sleeptracker

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.sleeptracker.database.SleepDatabase
import com.example.sleeptracker.database.SleepDatabaseDao

class SleepTrackerViewModel (val databaseDao: SleepDatabaseDao,application: Application):AndroidViewModel(application)