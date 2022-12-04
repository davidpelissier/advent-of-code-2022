package fr.davidpelissier.adventofcode2022.days

import fr.davidpelissier.adventofcode2022.Day

class Rucksack(
    private val compartment1: Set<Char>,
    private val compartment2: Set<Char>
) {
    fun findSharedItem() = try { compartment1.intersect(compartment2).first() } catch (e: NoSuchElementException) { '?' }
}

object Day03 : Day(3, "Rucksack Reorganization") {

    private fun determinePriority(item: Char) = when (item.code) {
        in 'a'.code .. 'z'.code -> item.code - 96
        in 'A'.code .. 'Z'.code -> item.code - 38
        else -> 0
    }

    override fun part1() = inputList
        .map {
            Rucksack(
                it.substring(0, it.length / 2).toSet(),
                it.substring(it.length / 2, it.length).toSet()
            )
        }
        .map { it.findSharedItem() }
        .sumOf { determinePriority(it) }

    override fun part2() = inputList
        .chunked(3)
        .map { (l1, l2, l3) -> l1.toList().intersect(l2.toSet()).intersect(l3.toSet()).first() }
        .sumOf { determinePriority(it) }

}