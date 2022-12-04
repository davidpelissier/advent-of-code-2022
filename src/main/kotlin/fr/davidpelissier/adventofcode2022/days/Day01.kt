package fr.davidpelissier.adventofcode2022.days

import fr.davidpelissier.adventofcode2022.Day

object Day01 : Day(1, "Calorie Counting") {

    private const val SEPARATOR = "\n"

    private val calories =  inputString
        .split("$SEPARATOR$SEPARATOR")           // Creating sublist by elf
        .map { it.split(SEPARATOR).sumOf(String::toInt) }  // Summing calories by elf

    override fun part1() = calories.max()

    override fun part2() = calories.sorted().takeLast(3).sum()

}
