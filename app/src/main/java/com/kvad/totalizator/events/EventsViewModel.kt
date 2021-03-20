package com.kvad.totalizator.events

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kvad.totalizator.data.EventRepository
import com.kvad.totalizator.data.models.Event
import kotlinx.coroutines.launch
import javax.inject.Inject

class EventsViewModel @Inject constructor(
    private val eventRepository: EventRepository
) : ViewModel() {
    private val _eventsLiveData = MutableLiveData<List<Event>>()
    val eventsLiveData = _eventsLiveData

    fun getEvents() {
        viewModelScope.launch {
            updateEvents()
        }
    }

    private suspend fun updateEvents() {
        _eventsLiveData.postValue(eventRepository.getEvents())
    }
}
