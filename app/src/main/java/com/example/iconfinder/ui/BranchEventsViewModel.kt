package com.example.iconfinder.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.iconfinder.data.BranchRepository
import com.example.iconfinder.models.BranchEventRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class BranchEventsViewModel @Inject constructor(
    private val branchRepository: BranchRepository
) : ViewModel() {

    fun logStandardEvent(branchEventRequest: BranchEventRequest){
        viewModelScope.launch {
            branchRepository.logStandardEvent(branchEventRequest)
        }
    }

    fun logCustomEvent(branchEventRequest: BranchEventRequest){
        viewModelScope.launch {
            val response=branchRepository.logCustomEvent(branchEventRequest)
            Timber.d("logCustomEvent response $response")
        }
    }
}