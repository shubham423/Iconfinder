package com.example.iconfinder.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.iconfinder.data.IconFinderRepository
import com.example.iconfinder.models.CategorySetResponse
import com.example.iconfinder.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val iconFinderRepository: IconFinderRepository
) : ViewModel() {

    private val _iconCategorySetResponse = MutableLiveData<Resource<CategorySetResponse?>>()
    val iconCategorySetResponse: LiveData<Resource<CategorySetResponse?>> = _iconCategorySetResponse

    fun getIconCategorySets() {
        _iconCategorySetResponse.postValue(Resource.Loading())
        viewModelScope.launch {
            try {
                val response =iconFinderRepository.getIconCategorySets()
                if (response.isSuccessful){
                    _iconCategorySetResponse.value=Resource.Success(data = response.body())
                }else{
                    _iconCategorySetResponse.value=Resource.Error(message = "some error occured")
                }
            }catch (e:Exception){
                Timber.d("exception occurred ${e.message}")
            }
        }
    }
}