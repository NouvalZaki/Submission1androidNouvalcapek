package com.example.submission1androidnouval.fragmen
import android.util.Log
import com.example.submission1androidnouval.data.response.JustResponse
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submission1androidnouval.data.response.ListEvent
import com.example.submission1androidnouval.data.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Response

class UpcomingViewmodel : ViewModel() {
    private val upComingEvent = MutableLiveData<List<ListEvent>>()
    val upcomingEvent : LiveData<List<ListEvent>> = upComingEvent
    private val loading = MutableLiveData<Boolean>()
    val load : LiveData<Boolean> = loading

init {
fetchUpcomingEvent()
}
private fun fetchUpcomingEvent() = viewModelScope.launch {
    loading.value = true
    try {
        val response: Response<JustResponse> = ApiConfig.getApiService().getEvents(active = 1)
        Log.d("UpcomingViewModel", "Response: $response")
        if (response.isSuccessful) {
    upComingEvent.value = response.body()?.event ?: listOf()
            loading.value = false
            } else {
    upComingEvent.value = listOf()
            loading.value = false
        }
    } catch (e: Exception) {
        upComingEvent.value = listOf()
        loading.value = false
        }
    }

}
