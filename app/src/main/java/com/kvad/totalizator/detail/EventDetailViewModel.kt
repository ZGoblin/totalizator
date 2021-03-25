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
import javax.inject.Inject

typealias eventDetailState = State<List<EventDetail>, Unit>

class EventDetailViewModel @Inject constructor(
    private val eventRepository: EventRepository,
    private val mapEventToDetailUiModel: MapEventToDetailUiModel
) : ViewModel() {

    private val _eventDetailLiveData = MutableLiveData<eventDetailState>()
    val eventDetailLiveData: LiveData<eventDetailState> = _eventDetailLiveData

    fun uploadData(eventId: String) {
        _eventDetailLiveData.value = State.Loading
        viewModelScope.launch {
            eventRepository.getEventId(eventId)
                .map { it.mapSuccess(mapEventToDetailUiModel::map) }
                .collect { result ->
                    result.doOnResult(
                        onSuccess = ::doOnSuccess,
                        onError = ::doOnError
                    )
                }
        }
    }

    private fun doOnSuccess(event: List<EventDetail>) {
        _eventDetailLiveData.value = State.Content(event)
    }

    private fun doOnError(error: ApiResultWrapper.Error) {
        Log.d("ErrorBody", error.msg)
        _eventDetailLiveData.value = State.Error(Unit)
    }
}
