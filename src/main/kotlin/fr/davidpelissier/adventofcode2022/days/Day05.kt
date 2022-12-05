package fr.davidpelissier.adventofcode2022.days

import fr.davidpelissier.adventofcode2022.Day

data class Dock(
    private val stacks: MutableList<MutableList<Char>>,
    private val instructions: List<List<Int>>
) {

    fun takeLatest() = this.stacks.map { s -> s.takeLast(1) }.flatten().toList().joinToString("")

    fun move(reverse: Boolean): Dock {
        instructions.forEach { (nb, source, dest) -> newStep(nb, source-1, dest-1, reverse) }
        return this
    }

    private fun newStep(nb: Int, source: Int, dest: Int, reverse: Boolean): Dock {
        stacks[dest].addAll(stacks[source].reversed().subList(0, nb).let { if (reverse) it.reversed() else it })
        repeat(nb) { stacks[source].removeLast() }
        return this
    }
}

object Day05 : Day(5, "Supply Stacks") {

    private fun String.parseStacks(): List<Char> {
        return this
            .lines()
            .asSequence()
            .map { s -> s.split("[", "] [", "]").filter { it.isNotEmpty() } }
            .flatten()
            .map { l -> l.chunked(4).filter { (it.length == 1 && it.isNotBlank()) || it.length > 1 } }
            .flatten()
            .map { it.trim() }
            .map { try { it.first() } catch (e: NoSuchElementException) { ' ' } }
            .toList()
    }

    private fun String.parseInstructions(): List<List<Int>> {
        return this
            .split("\\D".toRegex())
            .filter { it.isNotEmpty() }
            .map { it.toInt() }
            .chunked(3)
    }

    private fun buildStacks(stacks: List<Char>): MutableList<MutableList<Char>> {
        val columns = stacks.count { it.isDigit() }
        val piles = MutableList(columns) { mutableListOf<Char>() }
        stacks
            .filter { !it.isDigit() }
            .mapIndexed { index, letter -> piles[index % columns].add(letter) }
        return piles.map { it.filter { c -> c.isLetter() }.reversed().toMutableList() }.toMutableList()
    }

    override fun part1(): String {
        val (stacks, instructions) = inputString.split("\n\n")
        return Dock(buildStacks(stacks.parseStacks()), instructions.parseInstructions())
            .move(false)
            .takeLatest()
    }

    override fun part2(): String {
        val (stacks, instructions) = inputString.split("\n\n")
        return Dock(buildStacks(stacks.parseStacks()), instructions.parseInstructions())
            .move(true)
            .takeLatest()
    }
}