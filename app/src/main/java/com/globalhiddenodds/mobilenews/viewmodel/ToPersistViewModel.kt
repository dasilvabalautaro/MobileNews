package com.globalhiddenodds.mobilenews.viewmodel

import androidx.lifecycle.*
import com.globalhiddenodds.mobilenews.datasource.database.data.Hit
import com.globalhiddenodds.mobilenews.datasource.database.data.toHitView
import com.globalhiddenodds.mobilenews.domain.ToPersistUseCase
import com.globalhiddenodds.mobilenews.ui.data.HitView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

//Pattern Observer
@HiltViewModel
class ToPersistViewModel @Inject constructor(
    private val toPersistUseCase: ToPersistUseCase
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
                toPersistUseCase.insert()
            } catch (throwable: Throwable) {
                taskResultMutableLive.value = throwable.message
            }
        }
    }

    fun updateState(id: String) {
        viewModelScope.launch {
            toPersistUseCase.updateState(id)
            toPersistUseCase.getHits()
        }
    }
}