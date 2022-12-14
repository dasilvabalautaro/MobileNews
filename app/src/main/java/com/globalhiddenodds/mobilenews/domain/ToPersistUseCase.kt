package com.globalhiddenodds.mobilenews.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.globalhiddenodds.mobilenews.datasource.database.data.Hit
import com.globalhiddenodds.mobilenews.datasource.network.data.toHit
import com.globalhiddenodds.mobilenews.repository.HitDao
import com.globalhiddenodds.mobilenews.workers.DownloadHitsWorker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

//Pattern Adapter
//Pattern Observer
class ToPersistUseCase @Inject constructor(
    private val hitDao: HitDao
) {
    var hits: LiveData<List<Hit>>? = null

    fun getHits() {
        hits = hitDao.getHits().asLiveData()
    }

    suspend fun insert(): Result<Boolean> {
        val listHit = DownloadHitsWorker.listHitCloud

        return withContext(Dispatchers.IO) {
            try {
                if (listHit.isNotEmpty()){
                    listHit.forEach {
                        val hit = hitDao.getHitById(it.objectId)
                        if (hit.isEmpty()) hitDao.insert(it.toHit())
                        else if (hit[0].state == 1) hitDao.update(it.toHit())
                    }
                    return@withContext Result.success(true)
                }
                else {
                    return@withContext Result.failure(Throwable("List empty"))
                }

            } catch (throwable: Throwable) {
                throw throwable
            }
        }
    }

    suspend fun updateState(id: String): Result<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                hitDao.updateState(id)
                return@withContext Result.success(true)
            } catch (throwable: Throwable) {
                throw throwable
            }
        }
    }
}