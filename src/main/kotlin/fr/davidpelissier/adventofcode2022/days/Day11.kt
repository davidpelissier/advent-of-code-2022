package fr.davidpelissier.adventofcode2022.days

import fr.davidpelissier.adventofcode2022.Day

object Day11 : Day(11, "Monkey in the Middle") {

    override fun part1(): Long = parseMonkeys().play(20, 3).calculateBusiness()

    override fun part2(): Long = parseMonkeys().play(10000, 1).calculateBusiness()

    private const val BLANK_LINE = "\n\n"

    data class Test(val value: Int, private val onTrue: Int, private val onFalse: Int) {

        fun applyTo(initialValue: Long): Int {
            return when(initialValue % value == 0L) {
                true -> onTrue
                false -> onFalse
            }
        }
    }

    class Operation(private val operator: Char, private val value: String) {

        fun applyTo(initialValue: Long): Long {
            return when(operator) {
                '*' -> times(initialValue)
                '+' -> plus(initialValue)
                else -> initialValue
            }
        }

        operator fun times(initialValue: Long): Long {
            return if (value == "old") initialValue * initialValue else  initialValue * value.toInt()
        }

        operator fun plus(initialValue: Long): Long {
            return if (value == "old") initialValue + initialValue else  initialValue + value.toInt()
        }
    }

    data class Monkey(val items: MutableList<Long> = mutableListOf(),
                      val operation: Operation,
                      val test: Test,
                      var itemsInspected: Long = 0L) {

        fun inspectItems(monkeys: List<Monkey>, relief: Int, pgcd: Int) {
            items.forEach {
                val new = if (relief == 1) operation.applyTo(it) % pgcd else operation.applyTo(it) / relief
                monkeys[test.applyTo(new)].items.add(new)
                itemsInspected++
            }
            items.clear()
        }
    }

    private fun parseMonkeys(): List<Monkey> {
        return inputString
            .split(BLANK_LINE)
            .map { it.lines() }
            .map { Monkey(
                items = it[1].split(": ")[1].split(", ").map { it.toLong() }.toMutableList(),
                operation = Operation(
                    it[2].split("= ")[1].split(" ")[1].first(),
                    it[2].split("= ")[1].split(" ")[2],
                ),
                test = Test(
                    it[3].split(" ")[5].toInt(),
                    it[4].split(" ")[9].toInt(),
                    it[5].split(" ")[9].toInt(),
                )
            ) }
    }

    private fun List<Monkey>.play(rounds: Int, relief: Int): List<Monkey> {
        val pgcd = this.map { it.test.value }.reduce(Int::times)
        repeat(rounds) { this.forEach { it.inspectItems(this, relief, pgcd) } }
        return this
    }

    private fun List<Monkey>.calculateBusiness(): Long {
        return this.map { it.itemsInspected }
            .sorted()
            .takeLast(2)
            .reduce(Long::times)
    }
}