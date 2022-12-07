package com.example.customview2

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import kotlin.properties.Delegates

class TicTacToeView(
    context: Context,
    attributeSet: AttributeSet?,
    defStyleAttr: Int,
    defStyleRes: Int
) : View(context, attributeSet, defStyleAttr, defStyleRes) {

    private var playerColor1 by Delegates.notNull<Int>()
    private var playerColor2 by Delegates.notNull<Int>()
    private var gridColor by Delegates.notNull<Int>()

    var ticTacToeField: TicTacToeField? = null
        set(value) {
            field = value
            requestLayout()
            invalidate()
        }

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : this(
        context,
        attributeSet,
        defStyleAttr,
        R.style.DefaultTicTacToeFieldStyle
    )

    constructor(context: Context, attributeSet: AttributeSet?) : this(
        context,
        attributeSet,
        R.attr.TicTacToeFieldStyle
    )

    constructor(context: Context) : this(context, null)

    init {
        if (attributeSet != null) {
            initAttributes(attributeSet, defStyleAttr, defStyleRes)
        } else {
            initDefaultColors()
        }
    }

    private fun initAttributes(attributeSet: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        val typedArray = context.obtainStyledAttributes(
            attributeSet, R.styleable.TicTacToeView,
            defStyleAttr, defStyleRes
        )
        playerColor1 = typedArray.getColor(
            R.styleable.TicTacToeView_playerColor1,
            PLAYER1_DEFAULT_COLOR
        )
        playerColor2 = typedArray.getColor(
            R.styleable.TicTacToeView_playerColor2,
            PLAYER2_DEFAULT_COLOR
        )
        gridColor = typedArray.getColor(R.styleable.TicTacToeView_gridColor, GRID_DEFAULT_COLOR)

        typedArray.recycle()
    }

    private fun initDefaultColors() {
        playerColor1 = PLAYER1_DEFAULT_COLOR
        playerColor2 = PLAYER2_DEFAULT_COLOR
        gridColor = GRID_DEFAULT_COLOR
    }

    companion object {
        const val PLAYER1_DEFAULT_COLOR = Color.RED
        const val PLAYER2_DEFAULT_COLOR = Color.BLUE
        const val GRID_DEFAULT_COLOR = Color.GRAY
    }
}

