package com.kvad.totalizator.event.feed

import android.content.Context
import android.util.AttributeSet
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.AbstractComposeView
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.kvad.totalizator.event.data.model.Event
import com.kvad.totalizator.tools.PMYellow
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
                event = event,
                modifier = Modifier
                    .width(340.dp)
                    .fillMaxHeight()
                    .padding(4.dp)
                    .clickable { onEventClick(event) }
            )
        }
    }
}

@Composable
fun NoEvents() {
    val infiniteTransition = rememberInfiniteTransition()
    val firstColor by infiniteTransition.animateColor(
        initialValue = Color.Transparent,
        targetValue = Color.Black,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    val secondColor by infiniteTransition.animateColor(
        initialValue = Color.Black,
        targetValue = Color.Transparent,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )


    Box {
        Text(
            text = "No events :(",
            color = Color.PMYellow,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            firstColor,
                            secondColor
                        )
                    ),
                    shape = CircleShape
                )
                .padding(bottom = 16.dp, top = 16.dp, start = 24.dp, end = 24.dp)
                .align(Alignment.Center)
        )
    }
}

@Composable
fun Loading() {

    val roadOffset = 36.dp

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val offsetFirstBox = DpOffset(maxWidth / 2 - roadOffset / 2, maxHeight / 2)
        val offsetSecondBox = DpOffset(maxWidth / 2 - roadOffset / 2, maxHeight / 2 + roadOffset)

        val infiniteTransition = rememberInfiniteTransition()
        val firstBoxAnimation by infiniteTransition.animateValue(
            initialValue = offsetFirstBox,
            targetValue = offsetFirstBox,
            animationSpec = infiniteRepeatable(
                animation = keyframes {
                    offsetFirstBox at 0
                    DpOffset(offsetFirstBox.x + roadOffset, offsetFirstBox.y) at 250
                    DpOffset(offsetFirstBox.x + roadOffset, offsetFirstBox.y + roadOffset) at 500
                    DpOffset(offsetFirstBox.x, offsetFirstBox.y + roadOffset) at 750
                    offsetFirstBox at 1000
                    durationMillis = 1000
                },
                repeatMode = RepeatMode.Restart
            ),
            typeConverter = DpOffset.VectorConverter
        )
        val secondBoxAnimation by infiniteTransition.animateValue(
            initialValue = offsetSecondBox,
            targetValue = offsetSecondBox,
            animationSpec = infiniteRepeatable(
                animation = keyframes {
                    offsetSecondBox at 0
                    DpOffset(offsetSecondBox.x, offsetSecondBox.y - roadOffset) at 250
                    DpOffset(offsetSecondBox.x + roadOffset, offsetSecondBox.y - roadOffset) at 500
                    DpOffset(offsetSecondBox.x + roadOffset, offsetSecondBox.y) at 750
                    offsetSecondBox at 1000
                    durationMillis = 1000
                },
                repeatMode = RepeatMode.Restart
            ),
            typeConverter = DpOffset.VectorConverter
        )

        Box(
           modifier = Modifier
               .offset(firstBoxAnimation.x, firstBoxAnimation.y)
               .background(Color.PMYellow)
               .border(2.dp, Color.Black)
               .padding(8.dp)
        )
        Box(
            modifier = Modifier
                .offset(secondBoxAnimation.x, secondBoxAnimation.y)
                .background(Color.PMYellow)
                .border(2.dp, Color.Black)
                .padding(8.dp)
        )
    }
}
