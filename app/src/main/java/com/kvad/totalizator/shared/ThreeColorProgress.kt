package com.kvad.totalizator.shared

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.kvad.totalizator.R

class ThreeColorProgress @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null
) : View(context, attr) {

    private var progressItems: Array<ProgressItemKt> = emptyArray()
        set(value) {
            field = value
            calculateSum()
        }

    private var sumOfPercent: Float = 0F

    fun setupData(betAmountForEachOutcome: BetAmountForEachOutcome) {

        progressItems = arrayOf(
            ProgressItemKt(betAmountForEachOutcome.firstPlayerWinBetAmount, R.color.blue),
            ProgressItemKt(betAmountForEachOutcome.draw, R.color.light_grey),
            ProgressItemKt(betAmountForEachOutcome.secondPlayerWinBetAmount, R.color.red)
        )

        this.invalidate()
    }

    private fun calculateSum(){
        if (progressItems.isNotEmpty()){
            sumOfPercent = progressItems.sumBy { it.percent }.toFloat()
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.let {
            drawRectangle(it)
        }
    }

    private fun drawRectangle(canvas: Canvas) {
        if (progressItems.isNotEmpty()) {
            var lastProgressX = 0
            var progressItemWidth: Int
            var progressItemRight: Int

            for (progressItem in progressItems) {
                val progressPaint = Paint().apply {
                    color = ContextCompat.getColor(context, progressItem.color)
                }

                progressItemWidth = calculateItemWidth(progressItem.percent)
                progressItemRight = lastProgressX + progressItemWidth

                val progressRect = Rect(
                    lastProgressX,
                    0,
                    progressItemRight,
                    height
                )
                canvas.drawRect(progressRect, progressPaint)
                lastProgressX = progressItemRight
            }
        }
    }

    private fun calculateItemWidth(itemPercent: Int): Int{
        return (itemPercent * width.toFloat() / sumOfPercent).toInt()
    }

    private data class ProgressItemKt(
        val percent: Int,
        @ColorInt val color: Int
    )

}

data class BetAmountForEachOutcome(
    val firstPlayerWinBetAmount: Int,
    val draw: Int,
    val secondPlayerWinBetAmount: Int,
)
