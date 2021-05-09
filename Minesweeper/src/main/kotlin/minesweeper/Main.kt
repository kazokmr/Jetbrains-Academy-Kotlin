package minesweeper

import minesweeper.Minesweeper.Symbol.FREE
import minesweeper.Minesweeper.Symbol.MARKED
import minesweeper.Minesweeper.Symbol.MINE
import minesweeper.Minesweeper.Symbol.UNEXPLORED
import kotlin.random.Random

fun main() = Minesweeper.startGame()

class Minesweeper(private val numOfMines: Int) {
    private val sizeOfRow = 9
    private val sizeOfCol = 9
    private val mineField = Array(sizeOfRow) { CharArray(sizeOfCol) }
    private val gameField = Array(sizeOfRow) { CharArray(sizeOfCol) { UNEXPLORED.symbol } }
    private val aroundRows = intArrayOf(-1, -1, -1, 0, 0, 1, 1, 1)
    private val aroundCols = intArrayOf(-1, 0, 1, -1, 1, -1, 0, 1)
    private var numOfExploredCells: Int = 0

    // set MineField
    init {
        // set Mines
        repeat(numOfMines) {
            while (true) {
                val row = Random.nextInt(mineField.size)
                val col = Random.nextInt(mineField[row].size)
                if (mineField[row][col] == MINE.symbol) {
                    continue
                }
                mineField[row][col] = MINE.symbol
                break
            }
        }
        // set Hints
        for (row in mineField.indices) {
            for (col in mineField[row].indices) {
                if (mineField[row][col] == MINE.symbol) continue
                var count = 0
                for (i in 0 until 8) {
                    val nextRow = row + aroundRows[i]
                    val nextCol = col + aroundCols[i]
                    if (nextRow in 0 until sizeOfRow && nextCol in 0 until sizeOfCol) {
                        if (mineField[nextRow][nextCol] == MINE.symbol) count++
                    }
                }
                mineField[row][col] = count.toString()[0]
            }
        }
    }

    fun move(x: Int, y: Int, command: String): Boolean {
        val row = y - 1
        val col = x - 1
        // Nothing to do, if the cell has already set a number.
        if (gameField[row][col] in '1'..'8') {
            println("There is a number here!")
            return true
        }
        // Make the cell whether marked or unmarked, if the command is "mine".
        if (command == "mine") {
            gameField[row][col] = if (gameField[row][col] == MARKED.symbol) UNEXPLORED.symbol else MARKED.symbol
            return true
        }
        // Step on mine, if the command is "free" and the cell has mine.
        if (mineField[row][col] == MINE.symbol) {
            for (curRow in mineField.indices) {
                for (curCol in mineField[curRow].indices) {
                    if (mineField[curRow][curCol] == MINE.symbol) {
                        gameField[curRow][curCol] = MINE.symbol
                    }
                }
            }
            return false
        }
        // Make the cell unexplored, if it's set free or marked.
        if (gameField[row][col] != UNEXPLORED.symbol) {
            gameField[row][col] = UNEXPLORED.symbol
            if (mineField[row][col] != MINE.symbol) numOfExploredCells--
            return true
        }
        // Search around of the cell, if it is not a mine.
        gameField[row][col] = if (mineField[row][col] == '0') FREE.symbol else mineField[row][col]
        numOfExploredCells++
        checkAround(row, col)
        return true
    }

    fun canExplored() = numOfExploredCells < sizeOfRow * sizeOfCol - numOfMines

    private fun checkAround(row: Int, col: Int) {
        for (i in 0 until 8) {
            val nextRow = row + aroundRows[i]
            val nextCol = col + aroundCols[i]
            if (nextRow !in 0 until sizeOfRow || nextCol !in 0 until sizeOfCol) {
                continue
            }
            if (mineField[nextRow][nextCol] == MINE.symbol) return
        }

        for (i in 0 until 8) {
            val nextRow = row + aroundRows[i]
            val nextCol = col + aroundCols[i]
            if (nextRow !in 0 until sizeOfRow || nextCol !in 0 until sizeOfCol) {
                continue
            }
            if (gameField[nextRow][nextCol] == UNEXPLORED.symbol || gameField[nextRow][nextCol] == MARKED.symbol) {
                gameField[nextRow][nextCol] =
                    if (mineField[nextRow][nextCol] == '0') FREE.symbol else mineField[nextRow][nextCol]
                numOfExploredCells++
                checkAround(nextRow, nextCol)
            }
        }
    }

    fun printGameField() {
        print("\n |")
        for (i in 1..sizeOfCol) print(i)
        print("|\n")
        println("-|" + "-".repeat(sizeOfCol) + "|")
        for (row in gameField.indices) println("${row + 1}|${gameField[row].joinToString("")}|")
        println("-|" + "-".repeat(sizeOfCol) + "|")
    }

    companion object {
        private var minesweeper: Minesweeper

        init {
            println("How many mines do you want on the field?")
            minesweeper = Minesweeper(readLine()!!.toInt())
            minesweeper.printGameField()
        }

        fun startGame() {
            while (minesweeper.canExplored()) {
                println("Set/unset mines marks or claim a cell as free:")
                val (x, y, command) = readLine()!!.split(" ")
                val goNext = minesweeper.move(x.toInt(), y.toInt(), command)
                minesweeper.printGameField()
                if (!goNext) {
                    println("You stepped on a mine and failed!")
                    return
                }
            }
            println("Congratulations! You found all the mines!")
        }
    }

    enum class Symbol(val symbol: Char) {
        MINE('X'),
        UNEXPLORED('.'),
        MARKED('*'),
        FREE('/');
    }
}
