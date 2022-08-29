package com.globalhiddenodds.mobilenews.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkInfo
import com.globalhiddenodds.mobilenews.domain.DownloadHitsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

//Pattern Observer
@HiltViewModel
class DownloadHitsViewModel @Inject constructor(
    private val downloadHitsUseCase: DownloadHitsUseCase
) :
    ViewModel() {
    val workInfo: LiveData<List<WorkInfo>> = downloadHitsUseCase.workInfo

    fun downHits() {
        viewModelScope.launch {
            downloadHitsUseCase.downHits()
        }
    }

}