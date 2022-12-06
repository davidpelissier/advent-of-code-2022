package fr.davidpelissier.adventofcode2022.days

import fr.davidpelissier.adventofcode2022.Day

object Day06 : Day(6, "Tuning Trouble") {

    private fun findMarker(markerSize: Int) =
        inputString.indices.find { inputString.drop(it).take(markerSize).toSet().size == markerSize }!! + markerSize

    override fun part1() = findMarker(4)

    override fun part2() = findMarker(14)
}