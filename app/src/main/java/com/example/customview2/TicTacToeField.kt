package com.example.customview2


enum class Cell {
    PLAYER_1,
    PLAYER_2,
    EMPTY
}

typealias OnFieldChangedListener = (field: TicTacToeField) -> Unit

class TicTacToeField(
    private val rows: Int,
    private val columns: Int
) {

    private val cells = Array(rows) { Array(columns) { Cell.EMPTY } }

    private val listeners = mutableSetOf<OnFieldChangedListener>()

    private fun getCell(rows: Int, columns: Int): Cell {
        if (rows < 0 || columns < 0 || rows >= rows || columns >= columns) return Cell.EMPTY
        return cells[rows][columns]
    }

    fun setCell(rows: Int, columns: Int, cell: Cell) {
        if (rows < 0 || columns < 0 || rows >= rows || columns >= columns) return
        if (getCell(rows, columns) != cell) {
            cells[rows][columns]
            listeners.forEach { it.invoke(this) }
        }
    }
}