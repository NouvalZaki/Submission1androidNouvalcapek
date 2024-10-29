package com.example.submission1androidnouval

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submission1androidnouval.data.database.FavoriteEntity
import com.example.submission1androidnouval.data.response.DetailEvent
import com.example.submission1androidnouval.data.response.FavoriteRepository
import com.example.submission1androidnouval.data.response.ListEvent
import com.example.submission1androidnouval.data.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Response

class DetailActivityViewModel(private val repository: FavoriteRepository) : ViewModel() { // Updated here
    private val apiService = ApiConfig.getApiService()
    private val _eventDetail = MutableLiveData<DetailEvent>()
    val eventDetail: LiveData<DetailEvent> get() = _eventDetail
    private val _event = MutableLiveData<List<ListEvent>>()
    val event: LiveData<List<ListEvent>> get() = _event // Added 'get()' here for consistency
    private val loading = MutableLiveData<Boolean>()
    val load : LiveData<Boolean> = loading

    fun insertFavorite(event: FavoriteEntity){
        viewModelScope.launch {
            repository.insert(event)
        }
    }
    fun isItemExists(event: Int): LiveData<Boolean> {
        return repository.isItemExists(event)
    }

    fun deleteFavorite(event: FavoriteEntity){
        viewModelScope.launch {
            repository.delete(event)
        }
    }


    fun fetchDetailEvent(eventId: String) {
        viewModelScope.launch {
            loading.value = true
            Log.d("DetailViewModel", "Loading started")  // Debug log
            try {
                val response: Response<DetailEvent> = apiService.getDetail(eventId)
                if (response.isSuccessful) {
                    _eventDetail.value = response.body()
                } else {
                    Log.e("DetailViewModel", "Error fetching event detail: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("DetailViewModel", "Exception: ${e.message}")
            } finally {
                loading.value = false
                Log.d("DetailViewModel", "Loading finished")
            }
        }
    }
}
