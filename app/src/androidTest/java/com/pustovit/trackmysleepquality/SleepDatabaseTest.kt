package com.pustovit.trackmysleepquality

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.pustovit.trackmysleepquality.database.SleepDatabase
import com.pustovit.trackmysleepquality.database.SleepNight
import com.pustovit.trackmysleepquality.database.SleepNightDao
import org.junit.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class SleepDatabaseTest {

    private lateinit var sleepDao:SleepNightDao
    private lateinit var sleepDatabase: SleepDatabase

    @Before
    fun createDatabase(){
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        sleepDatabase = Room.inMemoryDatabaseBuilder(context,SleepDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        sleepDao = sleepDatabase.getSleepNightDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDatabase(){
        sleepDatabase.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetNight(){
        val night = SleepNight()
        sleepDao.insert(night)

        val tonight = sleepDao.getTonight()
        assertEquals(tonight?.sleepQuality,-1)
    }
}