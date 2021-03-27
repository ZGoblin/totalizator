package com.kvad.totalizator.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kvad.totalizator.data.EventRepository
import com.kvad.totalizator.detail.model.EventDetail
import com.kvad.totalizator.tools.State
import com.kvad.totalizator.tools.safeapicall.ApiResultWrapper
import com.kvad.totalizator.tools.safeapicall.mapSuccess
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import com.kvad.totalizator.data.model.Event
import javax.inject.Inject

typealias eventDetailState = State<List<EventDetail>, Unit>

class EventDetailViewModel @Inject constructor(
    private val eventRepository: EventRepository,
    private val mapEventToDetailUiModel: MapEventToDetailUiModel
) : ViewModel() {

    private val _eventDetailLiveData = MutableLiveData<eventDetailState>()
    val eventDetailLiveData: LiveData<eventDetailState> = _eventDetailLiveData

    var isLive = false
        private set

    fun uploadData(eventId: String) {
        _eventDetailLiveData.value = State.Loading
        viewModelScope.launch {
            eventRepository.getEventById(eventId)
                .collect { result ->
                    result.doOnResult(
                        onSuccess = ::doOnSuccess,
                        onError = ::doOnError
                    )
                }
        }
    }

    private fun doOnSuccess(event: Event) {
        isLive = event.isLive
        _eventDetailLiveData.value = State.Content(mapEventToDetailUiModel.map(event))
    }

    private fun doOnError(error: ApiResultWrapper.Error) {
        Log.d("ErrorBody", error.msg)
        _eventDetailLiveData.value = State.Error(Unit)
    }
}
