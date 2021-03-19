package com.kvad.totalizator.shared

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.kvad.totalizator.R
import com.kvad.totalizator.databinding.EventViewGroupBinding

class EventViewGroup @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defAttrStyle: Int = 0
) : ConstraintLayout(context, attributeSet, defAttrStyle) {

    private val defValuePlayersNameTextSize = 12
    private val defValueBetAmountTextSize = 16

    private val binding: EventViewGroupBinding =
        EventViewGroupBinding.bind(inflate(context, R.layout.event_view_group, this))

    init {
        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.EventViewGroup)

        val nameTextSize =
            typedArray.getInt(
                R.styleable.EventViewGroup_playersNameTextSize,
                defValuePlayersNameTextSize
            ).toFloat()
        val betTextSize =
            typedArray.getInt(
                R.styleable.EventViewGroup_betAmountTextSize,
                defValueBetAmountTextSize
            ).toFloat()

        binding.apply {

            tvDraw.setTextSize(TypedValue.COMPLEX_UNIT_SP, nameTextSize)
            tvFirstPlayerName.setTextSize(TypedValue.COMPLEX_UNIT_SP, nameTextSize)
            tvSecondPlayerName.setTextSize(TypedValue.COMPLEX_UNIT_SP, nameTextSize)

            tvFirstPlayerBetAmount.setTextSize(TypedValue.COMPLEX_UNIT_SP, betTextSize)
            tvSecondPlayerBetAmount.setTextSize(TypedValue.COMPLEX_UNIT_SP, betTextSize)

            tvDraw.visibility =
                if (typedArray.getBoolean(R.styleable.EventViewGroup_containDraw, true))
                    View.VISIBLE
                else View.GONE
        }

        typedArray.recycle()
    }

    fun setFirstPlayerName(name: String) {
        binding.tvFirstPlayerName.text = name
    }

    fun setSecondPlayerName(name: String) {
        binding.tvSecondPlayerName.text = name
    }

    fun setFirstPlayerBet(betAmount: Int) {
        binding.tvFirstPlayerBetAmount.text = resources.getString(R.string.bet_currency, betAmount)
    }

    fun setSecondPlayerBet(betAmount: Int) {
        binding.tvSecondPlayerBetAmount.text = resources.getString(R.string.bet_currency, betAmount)
    }

    fun setDrawBet(betAmount: Int) {
        binding.tvDraw.text = resources.getString(R.string.draw_bet_amount, betAmount)
    }

    fun setFirstPlayerImg(url: String) {
        Glide.with(this).load(url).into(binding.ivFirstPlayer)
    }

    fun setSecondPlayerImg(url: String) {
        Glide.with(this).load(url).into(binding.ivSecondPlayer)
    }

    fun setBetScale(betAmountForEachOutcome: BetAmountForEachOutcome) {
        binding.bettingScale.setupData(betAmountForEachOutcome)
    }
}
