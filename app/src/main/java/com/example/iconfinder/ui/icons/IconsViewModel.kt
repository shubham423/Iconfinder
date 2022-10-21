package com.example.iconfinder.ui.icons

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.iconfinder.MyApplication
import com.example.iconfinder.data.IconFinderRepository
import com.example.iconfinder.models.Icon
import com.example.iconfinder.models.IconsResponse
import com.example.iconfinder.utils.Resource
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File
import javax.inject.Inject

@HiltViewModel
class IconsViewModel @Inject constructor(
    private val iconFinderRepository: IconFinderRepository
) : ViewModel() {

    private val _query = MutableLiveData<String>()

    lateinit var selectedIcon:Icon

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

    @SuppressLint("Range")
    fun download(icon: Icon, position: Int) {

        viewModelScope.launch {
            var downloadID: Long = 0
            val downloadManager = getApplication(MyApplication.applicationContext()).getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            val urlString = icon.rasterSizes[position].formats[0].previewUrl
            lateinit var request: DownloadManager.Request
            try {
                request = DownloadManager.Request(Uri.parse(urlString))
                    .setDescription("Downloading...")
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    .setAllowedOverMetered(true)
                    .setAllowedOverRoaming(false)
                    .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE or DownloadManager.Request.NETWORK_WIFI)
                    .setDestinationInExternalPublicDir(
                        Environment.DIRECTORY_PICTURES,
                        File.separator + icon.iconId.toString() + "_" + icon.tags[0] + ".png"
                    )
                downloadID = downloadManager.enqueue(request)

            } catch (e: Exception) {

            }

            val query = DownloadManager.Query().setFilterById(downloadID)
            var lastMsg = ""
            var downloading = true
            while (downloading) {
                val cursor: Cursor = downloadManager.query(query)
                cursor.moveToFirst()
                if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL or DownloadManager.STATUS_FAILED) {
                    downloading = false
                }
                val msg = when (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))) {
                    DownloadManager.STATUS_SUCCESSFUL -> "Download successful"
                    DownloadManager.STATUS_FAILED -> "Download failed"
                    else -> ""
                }
                delay(500L)
                if (msg != lastMsg) {
                    lastMsg = msg
                }
                cursor.close()
            }
        }
    }
}