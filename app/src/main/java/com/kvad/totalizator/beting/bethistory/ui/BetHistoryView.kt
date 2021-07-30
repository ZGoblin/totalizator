package com.kvad.totalizator.beting.bethistory.ui

import android.content.Context
import android.util.AttributeSet
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.AbstractComposeView
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kvad.totalizator.beting.bethistory.model.BetHistoryDetailModel
import com.kvad.totalizator.event.feed.Loading
import com.kvad.totalizator.event.feed.NoEvents
import com.kvad.totalizator.tools.State

class BetHistoryView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AbstractComposeView(context, attrs, defStyleAttr) {

    @Composable
    override fun Content() {
        BetHistory()
    }

}

@Composable
fun BetHistory(
    viewModel: BetHistoryViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val items by viewModel.betHistoryLiveData.observeAsState()

    when (items) {
        is State.Content -> BetHistoryList(
            items = (items as State.Content).data
        )
        is State.Loading -> Loading()
        else -> NoEvents()
    }
}

@Composable
fun BetHistoryList(
    items: List<BetHistoryDetailModel>
) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(items = items) { item ->
            BetHistoryItem(uiModel = item)
        }
    }
}


@Composable
fun BetHistoryItem(
    uiModel: BetHistoryDetailModel
) {
    Card(
        elevation = 2.dp,
        backgroundColor = Color.White,
        shape = RoundedCornerShape(corner = CornerSize(32.dp))
    ){
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(uiModel.teamConfrontation, Modifier.size(14.dp))
            Text(uiModel.choice)
            Text(uiModel.eventStartTime)
            Text(uiModel.betStartTime)
            Text(uiModel.amount.toString())
            Text(text = uiModel.status, fontWeight = FontWeight.Bold)
        }
    }
}

@Preview
@Composable
fun SimplePreview() {
    BetHistoryItem(
        BetHistoryDetailModel(
            id = "1",
            teamConfrontation = "A vs B",
            choice = "X",
            eventStartTime = "01.01.2001",
            betStartTime = "01.01.2001",
            amount = 200.0,
            status = "Active"
        )
    )
}

//@Preview
//@Composable
//fun SimplePreview() {
//    BetHistoryList(
//        listOf(
//            BetHistoryDetailModel(
//                id = "1",
//                teamConfrontation = "A vs B",
//                choice = "X",
//                eventStartTime = "01.01.2001",
//                betStartTime = "01.01.2001",
//                amount = 200.0,
//                status = "Active"
//            ),
//            BetHistoryDetailModel(
//                id = "1",
//                teamConfrontation = "A vs B",
//                choice = "X",
//                eventStartTime = "01.01.2001",
//                betStartTime = "01.01.2001",
//                amount = 200.0,
//                status = "Active"
//            ),
//            BetHistoryDetailModel(
//                id = "1",
//                teamConfrontation = "A vs B",
//                choice = "X",
//                eventStartTime = "01.01.2001",
//                betStartTime = "01.01.2001",
//                amount = 200.0,
//                status = "Active"
//            ),
//            BetHistoryDetailModel(
//                id = "1",
//                teamConfrontation = "A vs B",
//                choice = "X",
//                eventStartTime = "01.01.2001",
//                betStartTime = "01.01.2001",
//                amount = 200.0,
//                status = "Active"
//            ),
//        )
//    )
//}





