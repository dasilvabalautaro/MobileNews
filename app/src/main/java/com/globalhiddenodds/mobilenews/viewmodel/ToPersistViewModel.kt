package com.globalhiddenodds.mobilenews.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.*
import com.globalhiddenodds.mobilenews.R
import com.globalhiddenodds.mobilenews.datasource.database.data.Hit
import com.globalhiddenodds.mobilenews.datasource.database.data.toHitView
import com.globalhiddenodds.mobilenews.domain.ToPersistUseCase
import com.globalhiddenodds.mobilenews.ui.data.HitView
import com.globalhiddenodds.mobilenews.ui.data.elapse
import com.globalhiddenodds.mobilenews.workers.DownloadHitsWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

//Pattern Observer
@SuppressLint("StaticFieldLeak")
@HiltViewModel
class ToPersistViewModel @Inject constructor(
    private val toPersistUseCase: ToPersistUseCase,
    @ApplicationContext val context: Context
) :
    ViewModel() {
    private val taskResultMutableLive = MutableLiveData<String>()
    val taskResult: LiveData<String> = taskResultMutableLive
    val hitsView: LiveData<List<HitView>> by lazy {
        toPersistUseCase.hits!!.switchMap {
            liveData { emit(transformHit(it)) }
        }
    }

    private suspend fun transformHit(list: List<Hit>):
            List<HitView> {
        val listHitView = withContext(
            viewModelScope
                .coroutineContext + Dispatchers.IO
        ) {
            val listView = mutableListOf<HitView>()
            list.map { listView.add(it.toHitView()) }
            listView.map { it.elapse() }
            return@withContext listView
        }
        return listHitView
    }

    fun getHits() {
        toPersistUseCase.getHits()
    }

    fun insert() {
        viewModelScope.launch {
            try {
                val result = toPersistUseCase.insert()
                if (result.isSuccess) {
                    DownloadHitsWorker.listHitCloud.clear()
                    taskResultMutableLive.value = context
                        .getString(R.string.msg_success_insert)
                }

            } catch (throwable: Throwable) {
                taskResultMutableLive.value = throwable.message
            }
        }
    }

    fun updateState(id: String) {
        viewModelScope.launch {
            try {
                val result = toPersistUseCase.updateState(id)
                if (result.isSuccess) {
                    taskResultMutableLive.value = context
                        .getString(R.string.msg_update)
                }
            } catch (throwable: Throwable) {
                taskResultMutableLive.value = throwable.message
            }
        }
    }
}