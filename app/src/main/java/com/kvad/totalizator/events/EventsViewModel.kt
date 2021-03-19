package com.kvad.totalizator.events

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kvad.totalizator.data.EventRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class EventsViewModel @Inject constructor(
    private val eventRepository: EventRepository
) : ViewModel() {
    private val _eventsLiveData = MutableLiveData<List<EventResponse>>()
    val eventsLiveData = _eventsLiveData

    fun getEvents() {
        viewModelScope.launch {
            _eventsLiveData.postValue(eventRepository.getEvents())
        }
    }
}
