package com.kvad.totalizator.detail

import androidx.lifecycle.ViewModel
import com.kvad.totalizator.data.EventRepository
import javax.inject.Inject

class EventDetailViewModel @Inject constructor(
    private val eventRepository: EventRepository
) : ViewModel(){


    fun uploadData(){
        eventRepository
    }
}
