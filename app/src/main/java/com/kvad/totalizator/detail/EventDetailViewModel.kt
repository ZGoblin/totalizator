package com.kvad.totalizator.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kvad.totalizator.data.EventRepository
import com.kvad.totalizator.detail.model.EventDetail
import com.kvad.totalizator.tools.State
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
        eventRepository.setIdForEventRequest(id = eventId)

        viewModelScope.launch {
            _eventDetailLiveData.value = State.Loading

            eventRepository.latestEvent
                .map { it.mapSuccess(mapEventToDetailUiModel::map) }
                .collect {
                    it.doOnResult(
                        onSuccess = { event ->
                            _eventDetailLiveData.value =
                                State.Content(event)
                        },
                        onError = { _eventDetailLiveData.value = State.Error(Unit) }
                    )
                }
        }

    }

}
