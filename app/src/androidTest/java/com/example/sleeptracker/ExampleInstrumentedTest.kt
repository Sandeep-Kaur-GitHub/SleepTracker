package com.example.sleeptracker

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.sleeptracker.database.SleepDatabase
import com.example.sleeptracker.database.SleepDatabaseDao
import com.example.sleeptracker.database.SleepNight
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import java.io.IOException
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    private lateinit var sleepDatabaseDao: SleepDatabaseDao
    private lateinit var sleepDatabase: SleepDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        sleepDatabase = Room.inMemoryDatabaseBuilder(context, SleepDatabase::class.java)
            .allowMainThreadQueries().build()
        sleepDatabaseDao = sleepDatabase.sleepDatabaseDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        sleepDatabase.close()
    }

    @Test
    @Throws(Exception::class)
    suspend fun insertAndGetNight() {
        val night = SleepNight()
        sleepDatabaseDao.insert(night)
        val tonight = sleepDatabaseDao.getTonight()
        assertEquals(tonight?.sleepQuality, -1)
    }
}