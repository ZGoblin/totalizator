package com.kvad.totalizator.betfeature

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kvad.totalizator.betfeature.model.BetDetailModel
import com.kvad.totalizator.betfeature.model.ChoiceModel
import com.kvad.totalizator.tools.State
import java.io.IOException
import javax.inject.Inject

typealias BetDetailState = State<BetDetailModel, IOException>

class BetViewModel @Inject constructor(
    //todo
    //add repo
) : ViewModel() {

    private val _betDetailLiveData = MutableLiveData<BetDetailState>()
    val betDetailLiveData: LiveData<BetDetailState> = _betDetailLiveData
    private lateinit var choice: ChoiceState
    private var idEvent: Int = 0

    fun setupData(choice: ChoiceState, idEvent: Int) {
        this.choice = choice
        this.idEvent = idEvent
    }


    //TODO
//    fun sendBet(betAmount: Int) {
//        /*
//        betAmount : Int,
//        -go to repository for request to backend
//        -when state
//         */
//    }
    //TODO
//    private fun setupBetDetail(detailBet: ChoiceModel) {
//        when (detailBet.choiceState) {
//            ChoiceState.FIRST_PLAYER_WIN -> {
//                BetDetailModel(
//                    detailBet.commandFirst.name,
//                    "${detailBet.commandFirst.name} vs ${detailBet.commandSecond.name}",
//                    //setupCoefficient(detailBet)
//                )
//            }
//            ChoiceState.SECOND_PLAYER_WIN -> {
//                BetDetailModel(
//                    detailBet.commandSecond.name,
//                    "${detailBet.commandFirst.name} vs ${detailBet.commandSecond.name}",
//                    //setupCoefficient(detailBet)
//                )
//            }
//            else -> {
//                BetDetailModel(
//                    "DRAW",
//                    "${detailBet.commandFirst.name} vs ${detailBet.commandSecond.name}",
//                    //setupCoefficient(detailBet)
//                )
//            }
//        }
//    }

}
