package com.globalhiddenodds.mobilenews.domain

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.work.*
import com.globalhiddenodds.mobilenews.workers.ConstantsHit
import com.globalhiddenodds.mobilenews.workers.DownloadHitsWorker
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

//Pattern mediator
class DownloadHitsUseCase @Inject constructor(
    @ApplicationContext val context: Context
) {
    private val workManager = WorkManager.getInstance(context)
    val workInfo: LiveData<List<WorkInfo>> = workManager
        .getWorkInfosByTagLiveData(ConstantsHit.TAG_DOWN)

    fun downHits() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build()
        val downWork = OneTimeWorkRequest
            .Builder(DownloadHitsWorker::class.java)
            .setConstraints(constraints)
            .addTag(ConstantsHit.TAG_DOWN)
            .build()
        workManager.enqueue(downWork)
    }
}