package com.globalhiddenodds.mobilenews.repository

import androidx.room.*
import com.globalhiddenodds.mobilenews.datasource.database.data.Hit
import kotlinx.coroutines.flow.Flow

@Dao
interface HitDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(hit: Hit)
    @Query("SELECT * FROM hit WHERE state = 1 ORDER BY created_at_i ASC")
    fun getHits(): Flow<List<Hit>>
    @Query("UPDATE hit SET state = 0 WHERE objectID LIKE :id")
    suspend fun updateState(id: String)
    @Query("SELECT * FROM hit WHERE objectID LIKE :id")
    suspend fun getHitById(id: String): List<Hit>?
    @Update
    suspend fun update(hit: Hit)
}