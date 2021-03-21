package com.kvad.totalizator.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kvad.totalizator.data.EventRepository
import com.kvad.totalizator.detail.model.EventDetail
import com.kvad.totalizator.tools.State
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

typealias eventDetailState = State<EventDetail, IOException>

@Suppress("UnusedPrivateMember")
class EventDetailViewModel @Inject constructor(
    private val eventRepository: EventRepository
) : ViewModel() {

    private val _commentLiveData = MutableLiveData<eventDetailState>()
    val commentLiveData: LiveData<eventDetailState> = _commentLiveData

    /*fun uploadData(eventId: Int) {
        viewModelScope.launch {

            val redditResponse = eventRepository.getEvents()
            when (redditResponse) {
                is NetworkError -> showNetworkError()
                is GenericError-> showGenericError(redditResponse)
                is Success -> showSuccess(redditResponse.value)
            }
        }
        eventRepository
        eventId
    }*/
}
