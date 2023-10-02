package com.example.iconfinder.ui.iconsets

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.iconfinder.data.IconFinderRepository
import com.example.iconfinder.models.IconSetResponse
import com.example.iconfinder.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class IconSetsViewModel @Inject constructor(
    private val iconFinderRepository: IconFinderRepository
) : ViewModel() {

    private val _iconSetsResponse = MutableLiveData<Resource<IconSetResponse?>>()
    val iconSetsResponse: LiveData<Resource<IconSetResponse?>> = _iconSetsResponse

    private val _identifier = MutableLiveData<String>()
    val identifier: LiveData<String> = _identifier

    fun getIconCategorySets(identifier: String) {
        _iconSetsResponse.postValue(Resource.Loading())
        viewModelScope.launch {
            try {
                val response = iconFinderRepository.getIconSets(identifier)
                if (response?.isSuccessful == true){
                    _iconSetsResponse.value=Resource.Success(data = response.body())
                }else{
                    _iconSetsResponse.value=Resource.Error(message = "some error occured")
                }
            }catch (e:Exception){
                Timber.d("exception occurred ${e.message}")
            }

        }
    }

    fun setIdentifier(identifier: String){
        _identifier.value=identifier
    }
}