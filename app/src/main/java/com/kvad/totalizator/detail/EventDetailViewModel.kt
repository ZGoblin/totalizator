package com.kvad.totalizator.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kvad.totalizator.data.EventRepository
import com.kvad.totalizator.data.models.Event
import com.kvad.totalizator.detail.model.EventDetail
import com.kvad.totalizator.shared.ResultWrapper
import com.kvad.totalizator.tools.State
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

typealias eventDetailState = State<List<EventDetail>, Unit>

class EventDetailViewModel @Inject constructor(
    private val eventRepository: EventRepository,
    private val mapEventToDetailUiModel: MapEventToDetailUiModel
) : ViewModel() {

    companion object {
        const val delay: Long = 3000L
    }

    private val _eventDetailLiveData = MutableLiveData<eventDetailState>()
    val eventDetailLiveData: LiveData<eventDetailState> = _eventDetailLiveData

    private lateinit var data: ResultWrapper<Event>

    fun uploadData(eventId: String) {
        viewModelScope.launch {
            _eventDetailLiveData.value = State.Loading
            while (true) {
                data = eventRepository.getEventById(eventId)
                _eventDetailLiveData.value = when (data) {
                    is ResultWrapper.Success -> {
                        State.Content(mapEventToDetailUiModel.map((data as ResultWrapper.Success<Event>).value))
                    }
                    else -> {
                        State.Error(Unit)
                    }
                }

                delay(delay)
            }
        }

    }
}
