package fr.davidpelissier.adventofcode2022

abstract class Day(val number: Int, val title: String) {

    protected val inputString by lazy { InputReader.readAsString(number) }

    protected val inputList by lazy { InputReader.readAsList(number) }

    abstract fun part1(): Any

    abstract fun part2(): Any

    fun display() {
        val header = "===== Day ${number.toString().padStart(2, '0')}: ${title} ====="
        val footer = "=".repeat(header.length)
        println(header)
        println("--> Part 1: ${part1()}")
        println("--> Part 2: ${part2()}")
        println(footer)
        println()
    }
}
