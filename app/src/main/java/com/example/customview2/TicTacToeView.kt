package com.example.customview2

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import kotlin.math.max
import kotlin.math.min
import kotlin.properties.Delegates

class TicTacToeView(
    context: Context,
    attributeSet: AttributeSet?,
    defStyleAttr: Int,
    defStyleRes: Int
) : View(context, attributeSet, defStyleAttr, defStyleRes) {

    private val fieldRect = RectF(0f, 0f, 0f, 0f)
    private var cellSize: Float = 0f
    private var cellPadding: Float = 0f

    private var playerColor1 by Delegates.notNull<Int>()
    private var playerColor2 by Delegates.notNull<Int>()
    private var gridColor by Delegates.notNull<Int>()

    private lateinit var player1Paint: Paint
    private lateinit var player2Paint: Paint
    private lateinit var gridPaint: Paint

    private var ticTacToeField: TicTacToeField? = null
        set(value) {
            field?.listeners?.remove(listener)
            field = value
            value?.listeners?.add(listener)
            updateViewSize()
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
        iniPaint()
    }

    private fun iniPaint() {
        player1Paint = Paint(Paint.ANTI_ALIAS_FLAG)
        player1Paint.color = playerColor1
        player1Paint.style = Paint.Style.STROKE
        player1Paint.strokeWidth =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3f, resources.displayMetrics)

        player2Paint = Paint(Paint.ANTI_ALIAS_FLAG)
        player2Paint.color = playerColor2
        player2Paint.style = Paint.Style.STROKE
        player2Paint.strokeWidth =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3f, resources.displayMetrics)


        gridPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        gridPaint.color = gridColor
        gridPaint.style = Paint.Style.STROKE
        gridPaint.strokeWidth =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1f, resources.displayMetrics)
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

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        ticTacToeField?.listeners?.add(listener)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        ticTacToeField?.listeners?.remove(listener)
    }

    private val listener: OnFieldChangedListener = {}

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        updateViewSize()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (ticTacToeField == null) return
        if (cellSize == 0f) return
        if (fieldRect.width() <= 0) return
        if (fieldRect.height() <= 0) return

        drawGrid(canvas)
        drawCells(canvas)
    }

    private fun drawGrid(canvas: Canvas) {
        val field = this.ticTacToeField ?: return

        val startX = fieldRect.left
        val stopX = fieldRect.right
        for (i in 0..field.rows) {
            val y = fieldRect.top + cellSize * i
            canvas.drawLine(startX, y, stopX, y, gridPaint)
        }

        val startY = fieldRect.top
        val stopY = fieldRect.bottom
        for (i in 0..field.columns) {
            val x = fieldRect.left + cellSize * i
            canvas.drawLine(x, startY, x, stopY, gridPaint)
        }
    }

        private fun drawCells(canvas: Canvas) {

        }

        override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
            val minWidth = suggestedMinimumWidth + paddingLeft + paddingRight
            val minHeight = suggestedMinimumHeight + paddingBottom + paddingTop

            val desiredCellSizeInPixels = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                DESIRED_CELL_SIZE, resources.displayMetrics
            ).toInt()

            val rows = ticTacToeField?.rows ?: 0
            val columns = ticTacToeField?.columns ?: 0

            val desiredWidth =
                max(minWidth, columns * desiredCellSizeInPixels + paddingRight + paddingLeft)
            val desiredHeight =
                max(minHeight, rows * desiredCellSizeInPixels + paddingTop + paddingBottom)

            setMeasuredDimension(
                resolveSize(desiredWidth, widthMeasureSpec),
                resolveSize(desiredHeight, heightMeasureSpec)
            )
        }

        private fun updateViewSize() {
            val field = this.ticTacToeField ?: return

            val safeWidth = width - paddingLeft - paddingRight
            val safeHeight = height - paddingTop - paddingBottom

            val cellWidth = safeWidth / field.columns.toFloat()
            val cellHeight = safeHeight / field.rows.toFloat()

            cellSize = min(cellWidth, cellHeight)
            cellPadding = cellSize * 0.2f

            val fieldWidth = cellSize * field.columns
            val fieldHeight = cellSize * field.rows

            fieldRect.left = paddingLeft + (safeWidth - fieldWidth) / 2
            fieldRect.top = paddingTop + (safeHeight - fieldHeight) / 2
            fieldRect.bottom = fieldRect.top + fieldHeight
            fieldRect.right = fieldRect.left + fieldWidth
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

            const val DESIRED_CELL_SIZE = 50f
        }
    }

