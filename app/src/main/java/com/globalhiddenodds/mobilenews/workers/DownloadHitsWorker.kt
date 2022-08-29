package com.globalhiddenodds.mobilenews.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.globalhiddenodds.mobilenews.datasource.network.NewsCloud
import com.globalhiddenodds.mobilenews.datasource.network.data.HitCloud
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

//Pattern Adapter
class DownloadHitsWorker @Inject constructor(
    @ApplicationContext val context: Context,
    workerParams: WorkerParameters
): CoroutineWorker(context, workerParams) {

    @Suppress("UNCHECKED_CAST")
    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                val response = NewsCloud.retrofitService.getNews()
                val jsonMap: MutableMap<String?, Any?> = response as MutableMap<String?, Any?>
                val hits = WorkerUtils.getHits(jsonMap)
                listHitCloud = WorkerUtils.setHits(hits)
                Result.success()
            }catch (throwable: Throwable) {
                Result.failure()
            }
        }
    }

    companion object {
        var listHitCloud = mutableListOf<HitCloud>()
    }
}