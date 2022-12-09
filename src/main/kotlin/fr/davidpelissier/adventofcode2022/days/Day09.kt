package fr.davidpelissier.adventofcode2022.days

import fr.davidpelissier.adventofcode2022.Day
import kotlin.math.abs

object Day09 : Day(9, "Rope Bridge") {

    override fun part1() = Snake(1).run().display().visitedByLastTail()

    override fun part2() = Snake(9).run().display().visitedByLastTail()

    class Snake(nbTails: Int) {

        var matrix = MutableList(size()) { MutableList(size()) { '.' } }

        private var moves = inputList.map { it.split(' ') }.map { Pair(it[0].first(), it[1].toInt()) }

        private val start = size()/2

        private var head = Point(start, start, 'H')

        private var tails = buildList { (0 until nbTails).forEach { add(Point(start, start, it.toString().first() + 1)) } }

        fun visitedByLastTail() = matrix.map { lines -> lines.filter { it == tails.size.toString().first() } }.flatten().count()

        fun run(): Snake {
            moves.map { m ->
                repeat(m.second) {
                    head.move(m.first)
                    tails[0].follow(head).markAsVisited()
                    for(i in 1 until tails.size) tails[i].follow(tails[i-1]).markAsVisited()
                }
            }
            return this
        }

        fun display(): Snake {
            val toPrint = matrix.reversed()
            for (i in toPrint.indices) {
                println(toPrint[i].toString())
            }
            return this
        }

        inner class Point(private var x: Int, private var y: Int, private val subject: Char) {

            fun move(direction: Char): Point {
                when(direction) {
                    'U' -> y++
                    'D' -> y--
                    'L' -> x--
                    'R' -> x++
                }
                return this
            }

            fun follow(point: Point): Point {
                if (abs(point.x - this.x) > 1 || abs(point.y - this.y) > 1) {
                    when {
                        point.y - this.y > 0 -> this.move('U')
                        point.y - this.y < 0 -> this.move('D')
                    }
                    when {
                        point.x - this.x < 0 -> this.move('L')
                        point.x - this.x > 0 -> this.move('R')
                    }
                }
                return this
            }

            fun markAsVisited() {
                if (subject == tails.size.toString().first()) matrix[y][x] = subject
            }
        }

        companion object {

            private fun size(): Int {
                return inputList.map { it.split(' ') }.maxOf { it[1].toInt() } * 100
            }

        }
    }
}