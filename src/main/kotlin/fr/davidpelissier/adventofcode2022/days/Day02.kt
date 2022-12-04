package fr.davidpelissier.adventofcode2022.days

import fr.davidpelissier.adventofcode2022.Day
import fr.davidpelissier.adventofcode2022.days.Points.Companion.determineResult
import fr.davidpelissier.adventofcode2022.days.Shape.Companion.determineShape

enum class Points(val value: Int) {
    LOOSE(0),
    NUL(3),
    WIN(6);

    companion object {
        fun determineResult(value: Int) = Points.values().first { it.ordinal == value }
    }
}

sealed class Shape {
    abstract val value: Int
    abstract fun round(versus: Shape): Int
    abstract fun play(result: Points): Shape

    companion object {
        fun determineShape(value: Int) = when(value) {
            1 -> Rock
            2 -> Scissor
            else -> Paper
        }
    }
}

object Rock : Shape() {
    override val value = 1

    override fun round(versus: Shape): Int {
        return when(versus) {
            is Rock -> Points.NUL.value
            is Scissor -> Points.WIN.value
            is Paper -> Points.LOOSE.value
        }
    }

    override fun play(result: Points) = when(result) {
        Points.NUL -> Rock
        Points.WIN -> Paper
        Points.LOOSE -> Scissor
    }
}

object Scissor : Shape() {
    override val value = 2

    override fun round(versus: Shape) = when(versus) {
        is Rock -> Points.LOOSE.value
        is Scissor -> Points.NUL.value
        is Paper -> Points.WIN.value
    }

    override fun play(result: Points) = when(result) {
        Points.NUL -> Scissor
        Points.WIN -> Paper
        Points.LOOSE -> Rock
    }
}

object Paper : Shape() {
    override val value = 3

    override fun round(versus: Shape) = when(versus) {
        is Rock -> Points.WIN.value
        is Scissor -> Points.LOOSE.value
        is Paper -> Points.NUL.value
    }

    override fun play(result: Points) = when(result) {
        Points.NUL -> Paper
        Points.LOOSE -> Scissor
        Points.WIN -> Rock
    }
}
object Day02 : Day(2, "Rock Paper Scissors") {


    private fun determineShapes(player1: String, player2: String) =
        Pair(determineShape("ABC".indexOf(player1) + 1), determineShape("XYZ".indexOf(player2) + 1))

    override fun part1() = inputList
        .map { it.split(" ")}
        .map { determineShapes(it[0], it[1]) }
        .sumOf { it.second.value + it.first.round(it.second) }

    override fun part2() = inputList
        .map { it.split(" ") }
        .map { Pair(determineShape("ABC".indexOf(it[0]) + 1), determineResult("XYZ".indexOf(it[1]))) }
        .map { Pair(it.first, it.first.play(it.second)) }
        .sumOf { it.second.value + it.first.round(it.second) }
}