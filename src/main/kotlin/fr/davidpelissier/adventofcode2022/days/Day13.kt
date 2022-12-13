package fr.davidpelissier.adventofcode2022.days

import com.fasterxml.jackson.databind.ObjectMapper
import fr.davidpelissier.adventofcode2022.Day

object Day13 : Day(13, "Distress Signal") {

    override fun part1() = inputList
        .asSequence()
        .chunked(3)
        .map { it.parseAsPair() }
        .map { compare(it.first, it.second) }
        .mapIndexed { index, it -> if(it == -1) index + 1 else 0 }
        .sum()

    override fun part2(): Int {
        val newInputList = buildList {
            addAll(inputList.filter { it != "" })
            addAll(listOf("[[2]]", "[[6]]"))
        }
        val sorted = newInputList
            .map { it.parseAsList() }
            .sortedWith(::compare)
        return sorted.indexOf("[[2]]") * sorted.indexOf("[[6]]")
    }

    private fun List<List<*>>.indexOf(value: String) = this.indexOfFirst { it.toString() == value } + 1


    private fun List<String>.parseAsPair(): Pair<List<*>, List<*>> {
        val objectMapper = ObjectMapper()
        val left = objectMapper.readValue(this[0], List::class.java)
        val right = objectMapper.readValue(this[1], List::class.java)
        return Pair(left, right)
    }

    private fun String.parseAsList(): List<*> {
        return ObjectMapper().readValue(this, List::class.java)
    }

    @Suppress("UNCHECKED_CAST")
    private fun compare(a: Any, b: Any): Int {
        return when {
            areIntegers(a, b) -> compareIntegersPair(a as Int, b as Int)
            a is Int -> compare(listOf(a), b)
            b is Int -> compare(a, listOf(b))
            else -> {
                val left = a as List<Any>
                val right = b as List<Any>
                for (i in 0 until minOf(left.size, right.size)) {
                    compare(left[i], right[i]).let { if (it != 0) return it }
                }
                compare(left.size, right.size)
            }
        }
    }

    private fun compareIntegersPair(a: Int, b: Int) = if (a < b) -1 else if (a > b) 1 else 0

    private fun areIntegers(a: Any, b: Any) = a is Int && b is Int

}