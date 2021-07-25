package com.kvad.totalizator.event.feed

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.kvad.totalizator.R
import com.kvad.totalizator.event.data.model.BetPool
import com.kvad.totalizator.event.data.model.Event
import com.kvad.totalizator.event.data.requestmodels.Participant
import com.kvad.totalizator.tools.foreground

@Composable
fun EventOverview(
    onEventClick: (Event) -> Unit,
    event: Event
) {
    Box(
        modifier = Modifier
            .width(340.dp)
            .fillMaxHeight()
            .padding(8.dp)
            .clickable { onEventClick(event) }
    ) {
        ParticipantsPhotos(
            firstParticipant = event.firstParticipant,
            secondParticipant = event.secondParticipant
        )
        OverviewData(
            event = event,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun ParticipantsPhotos(
    firstParticipant: Participant,
    secondParticipant: Participant
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .foreground(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent,
                        Color.Black
                    )
                )
            )
    ) {
        ParticipantPhoto(
            image = firstParticipant.photoLink,
            background = R.drawable.yellow_player_background,
            modifier = Modifier.weight(1f)
        )
        ParticipantPhoto(
            image = secondParticipant.photoLink,
            background = R.drawable.red_player_background,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun ParticipantPhoto(
    image: String?,
    @DrawableRes background: Int,
    modifier: Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize(),
        )
        Image(
            painter = rememberImagePainter(
                request = ImageRequest.Builder(LocalContext.current)
                    .data(image)
                    .build()
            ),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize(),
        )
    }
}

@Composable
fun OverviewData(
    event: Event,
    modifier: Modifier
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            PlayerDataOverview(
                event.firstParticipant.name,
                event.betPool.firstPlayerBetAmount,
                Modifier.weight(1f).padding(start = 8.dp, end = 8.dp),
                Alignment.Start
            )
            PlayerDataOverview(
                event.secondParticipant.name,
                event.betPool.secondPlayerBetAmount,
                Modifier.weight(1f).padding(start = 8.dp, end = 8.dp),
                Alignment.End
            )
        }

        BetsProgress(
            betPool = event.betPool,
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp)
                .padding(8.dp)
        )
    }
}

@Composable
fun BetsProgress(
    betPool: BetPool,
    modifier: Modifier
) {
    val baseModifier = Modifier.fillMaxHeight()

    Row(
        modifier = modifier
    ) {
        Box(
            modifier = baseModifier
                .weight(betPool.firstPlayerBetAmount)
                .background(betPool.firstPlayerColorBar)
        )
        Box(
            modifier = baseModifier
                .weight(betPool.drawBetAmount)
                .background(betPool.drawColorBar)
        )
        Box(
            modifier = baseModifier
                .weight(betPool.secondPlayerBetAmount)
                .background(betPool.secondPlayerColorBar)
        )
    }
}

@Composable
fun PlayerDataOverview(
    name: String,
    bet: Float,
    modifier: Modifier = Modifier,
    horizontalAlignment: Alignment.Horizontal
) {
    Column(
        modifier = modifier,
        horizontalAlignment = horizontalAlignment
    ) {
        Text(
            text = name,
            color = Color.White,
            fontSize = 12.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "${bet.toInt()} UAH",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
