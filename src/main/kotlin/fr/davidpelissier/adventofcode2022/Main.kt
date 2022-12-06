package fr.davidpelissier.adventofcode2022

import fr.davidpelissier.adventofcode2022.days.Day01
import fr.davidpelissier.adventofcode2022.days.Day02
import fr.davidpelissier.adventofcode2022.days.Day03
import fr.davidpelissier.adventofcode2022.days.Day04
import fr.davidpelissier.adventofcode2022.days.Day05
import fr.davidpelissier.adventofcode2022.days.Day06

object Main {

    private val days = listOf(
        Day01,
        Day02,
        Day03,
        Day04,
        Day05,
        Day06,
//        Day07,
//        Day08,
//        Day09,
//        Day10,
//        Day11,
//        Day12,
//        Day13,
//        Day14,
//        Day15,
//        Day16,
//        Day17,
//        Day18,
//        Day19,
//        Day20,
//        Day21,
//        Day22,
//        Day23,
//        Day24,
//        Day25,
    )

    @JvmStatic
    fun main(args: Array<String>) {
        days.forEach { it.display() }
    }
}
