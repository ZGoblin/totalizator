package com.kvad.totalizator.events

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kvad.totalizator.data.EventRepository
import com.kvad.totalizator.data.models.Event
import com.kvad.totalizator.shared.ResultWrapper
import com.kvad.totalizator.tools.ErrorState
import com.kvad.totalizator.tools.State
import kotlinx.coroutines.launch
import javax.inject.Inject

typealias EventState = State<List<Event>, ErrorState.LoadingError>

class EventsViewModel @Inject constructor(
    private val eventRepository: EventRepository
) : ViewModel() {
    private val _eventsLiveData = MutableLiveData<EventState>()
    val eventsLiveData = _eventsLiveData

    fun getEvents() {
        viewModelScope.launch {
            updateEvents()
        }
    }

    private suspend fun updateEvents() {
        when (val result = eventRepository.getEvents()) {
            is ResultWrapper.Success -> _eventsLiveData.postValue(State.Content(result.value))
            is ResultWrapper.DataLoadingError -> _eventsLiveData.postValue(State.Error(ErrorState.LoadingError))
        }
    }
}
