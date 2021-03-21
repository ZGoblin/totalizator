package com.kvad.totalizator.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kvad.totalizator.data.EventRepository
import com.kvad.totalizator.data.models.Event
import com.kvad.totalizator.detail.model.EventDetail
import com.kvad.totalizator.shared.ResultWrapper
import com.kvad.totalizator.tools.ErrorState
import com.kvad.totalizator.tools.State
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

typealias eventDetailState = State<List<EventDetail>, ErrorState>


class EventDetailViewModel @Inject constructor(
    private val eventRepository: EventRepository,
    private val mapEventToDetailUiModel: MapEventToDetailUiModel
) : ViewModel() {

    companion object{
        const val delay: Long = 3000L
    }

    private val _commentLiveData = MutableLiveData<eventDetailState>()
    val commentLiveData: LiveData<eventDetailState> = _commentLiveData

    private lateinit var data: ResultWrapper<Event>

    fun uploadData(eventId: String) {

        viewModelScope.launch {
            _commentLiveData.value = State.Loading
            while (true) {
                data = eventRepository.getEventById(eventId)
                _commentLiveData.value = when (data) {
                    is ResultWrapper.Success -> {
                        State.Content(mapEventToDetailUiModel.map((data as ResultWrapper.Success<Event>).value))
                    }
                    else -> {
                        State.Error(ErrorState.LOADING_ERROR)
                    }
                }

                delay(delay)
            }
        }

    }
}
