package com.kvad.totalizator.event.feed

import android.content.Context
import android.util.AttributeSet
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.AbstractComposeView
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.kvad.totalizator.event.data.model.Event
import com.kvad.totalizator.tools.State

class EventFeedView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0,
) : AbstractComposeView(context, attrs, defStyleAttr) {

    private var onEventClickCallback: (Event) -> Unit = { }

    @Composable
    override fun Content() {
        Feed(onEventClickCallback)
    }

    fun setOnEventClick(onEventClick: (Event) -> Unit) {
        onEventClickCallback = onEventClick
    }

}

@Composable
fun Feed(
    onEventClick: (Event) -> Unit,
    viewModel: EventsViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val events by viewModel.eventsLiveData.observeAsState()

    when (events) {
        is State.Content -> Feed(
            onEventClick = onEventClick,
            events = (events as State.Content).data
        )
        is State.Loading -> Loading()
        else -> NoEvents()
    }
}

@Composable
fun Feed(
    onEventClick: (Event) -> Unit,
    events: List<Event>
) {
    LazyRow {
        items(items = events) { event ->
            EventOverview(
                onEventClick = onEventClick,
                event = event
            )
        }
    }
}

@Composable
fun Loading() {
    //TODO impl Loading screen
}

@Composable
fun NoEvents() {
    //TODO impl Error screen
}
