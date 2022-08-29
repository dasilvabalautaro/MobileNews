package com.globalhiddenodds.mobilenews

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.globalhiddenodds.mobilenews.datasource.database.AppRoomDatabase
import com.globalhiddenodds.mobilenews.datasource.database.data.Hit
import com.globalhiddenodds.mobilenews.repository.HitDao
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class DatabaseTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var hitDao: HitDao
    private lateinit var db: AppRoomDatabase
    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppRoomDatabase::class.java
        ).allowMainThreadQueries().build()
        hitDao = db.hitDao()
    }
    @After
    fun teardown() {
        db.close()
    }
    @Test
    fun insertHit() = runTest {
        val hit = Hit("32601160", 32601049.0,
            32600677.0, "Password delisting",
            "stryan", "https://password.community",
            "2022-08-25T23:03:04.000Z", 1661470910.0)
        hitDao.insert(hit)
        val hitInserted = hitDao.getHits().asLiveData().getOrAwaitValue()
        assertThat(hitInserted.size).isEqualTo(1)
    }
}