package com.kvad.totalizator.events

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kvad.totalizator.data.EventRepository
import com.kvad.totalizator.data.models.Event
import com.kvad.totalizator.tools.ErrorState
import com.kvad.totalizator.tools.ResultWrapper
import com.kvad.totalizator.tools.State
import kotlinx.coroutines.launch
import java.lang.Error
import javax.inject.Inject

typealias EventState = State<List<Event>, ErrorState>

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
        eventRepository.getEvents().doOnResult(
            onSuccess = ::onSuccess,
            onError = ::onError
        )
    }

    private fun onSuccess(eventList: List<Event>){
        _eventsLiveData.value = State.Content(eventList)
    }

    private fun onError(error: ResultWrapper.Error){
        Log.d("ERROR_TAG", error.msg)
        _eventsLiveData.value = State.Error(ErrorState.LOADING_ERROR)
    }
}
