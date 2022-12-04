package fr.davidpelissier.adventofcode2022.days

import fr.davidpelissier.adventofcode2022.Day

object Day04 : Day(4, "Camp Cleanup") {

    private fun List<String>.toRangedPair() = this
        .asSequence()
        .map { it.split(',') }
        .map { it.flatMap { it.split('-') } }
        .map { Pair((it[0].toInt()..it[1].toInt()).toList(), (it[2].toInt()..it[3].toInt()).toList())}

    private fun rangeContainsAll(list1: List<Int>, list2: List<Int>) =
        list1.containsAll(list2) || list2.containsAll(list1)

    private fun rangeContainsAtLeastOne(list1: List<Int>, list2: List<Int>) =
        list1.map { list2.contains(it) }.reduce { acc, current -> acc || current }

    override fun part1() = inputList
        .toRangedPair()
        .map { rangeContainsAll(it.first, it.second) }
        .count { it }

    override fun part2() = inputList
        .toRangedPair()
        .map { rangeContainsAtLeastOne(it.first, it.second) }
        .count { it }

}