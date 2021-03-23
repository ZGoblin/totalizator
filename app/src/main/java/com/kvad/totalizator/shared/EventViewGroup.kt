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

    companion object {
        private const val defValuePlayersNameTextSize = 12
        private const val defValueBetAmountTextSize = 16
    }

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

    fun hideDrawBet(hide: Boolean) {
        if (hide) {
            binding.tvDraw.visibility = View.GONE
        } else {
            binding.tvDraw.visibility = View.VISIBLE
        }
    }

    fun setFirstPlayerImg(url: String?) {
        url?.let {
            Glide.with(this)
                .load(url)
                .error(R.drawable.player_image_not_found)
                .into(binding.ivFirstPlayer)
        }
    }

    fun setSecondPlayerImg(url: String?) {
        url?.let {
            Glide.with(this)
                .load(url)
                .error(R.drawable.player_image_not_found)
                .into(binding.ivSecondPlayer)
        }
    }

    fun setBetScale(betAmountForEachOutcome: BetAmountForEachOutcome) {
        binding.tvFirstPlayerBetAmount.text = resources.getString(
            R.string.bet_currency,
            betAmountForEachOutcome.firstPlayerWinBetAmount
        )
        binding.tvSecondPlayerBetAmount.text = resources.getString(
            R.string.bet_currency,
            betAmountForEachOutcome.secondPlayerWinBetAmount
        )
        binding.tvDraw.text = resources.getString(
            R.string.draw_bet_amount,
            betAmountForEachOutcome.draw
        )

        binding.bettingScale.setupData(betAmountForEachOutcome)
    }
}
