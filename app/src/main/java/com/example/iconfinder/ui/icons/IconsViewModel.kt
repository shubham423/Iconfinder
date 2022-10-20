package com.example.iconfinder.ui.icons

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.iconfinder.data.IconFinderRepository
import com.example.iconfinder.models.IconsResponse
import com.example.iconfinder.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class IconsViewModel @Inject constructor(
    private val iconFinderRepository: IconFinderRepository
) : ViewModel() {

    private val _query = MutableLiveData<String>()
    val query: LiveData<String> = _query

    private val _iconsResponse = MutableLiveData<Resource<IconsResponse?>>()
    val iconsResponse: LiveData<Resource<IconsResponse?>> = _iconsResponse

    fun searchIcons() {
        _iconsResponse.postValue(Resource.Loading())
        viewModelScope.launch {
            try {
                val response = _query.value?.let { iconFinderRepository.searchIcons(it) }
                if (response?.isSuccessful == true){
                    _iconsResponse.value=Resource.Success(data = response.body())
                }else{
                    _iconsResponse.value=Resource.Error(message = "some error occured")
                }
            }catch (e:Exception){
                Timber.d("exception occurred ${e.message}")
            }

        }
    }

    fun getIconsFromIconSets(id:Int) {
        _iconsResponse.postValue(Resource.Loading())
        viewModelScope.launch {
            try {
                val response = iconFinderRepository.getIconFromIconSets(id)
                if (response?.isSuccessful == true){
                    _iconsResponse.value=Resource.Success(data = response.body())
                }else{
                    _iconsResponse.value=Resource.Error(message = "some error occured")
                }
            }catch (e:Exception){
                Timber.d("exception occurred ${e.message}")
            }

        }
    }

    fun setQuery(query: String){
        _query.value=query
    }
}