package fr.davidpelissier.adventofcode2022.days

import fr.davidpelissier.adventofcode2022.Day
import fr.davidpelissier.adventofcode2022.days.Day10.Instruction.Addx
import fr.davidpelissier.adventofcode2022.days.Day10.Instruction.Noop

object Day10 : Day(10, "Cathode-Ray Tube") {

    override fun part1() = CPU().process()

    override fun part2() = CPU().display()

    sealed class Instruction() {

        class Addx(val value: Int) : Instruction()

        class Noop : Instruction()

        fun toOperation(): List<Int> = when(this) {
            is Addx -> listOf(0, this.value)
            is Noop -> listOf(0)
        }
    }


    class CPU {

        private var registry = 1

        private var signalStrength: MutableMap<Int, Int> = mutableMapOf()

        private var cycles = 1

        private var CRT = mutableListOf<Char>()

        private val instructions = inputList.map { it.parseInstruction().toOperation() }.flatten()

        fun display(): List<String> {
            instructions.forEachIndexed { index, it ->
                if ((registry - 1..registry + 1).contains(index%40)) CRT.add('#') else  CRT.add('.')
                registry += it
            }
            return CRT.chunked(40).map { it.joinToString("") }
        }

        fun process(): Int {
            instructions.forEach {
                registry += it
                cycles++
                if (cycles == 20 || (cycles + 20)%40 == 0) signalStrength[cycles] = registry
            }
            return signalStrength.entries.sumOf { (k, v) -> k * v }
        }
    }

    private fun String.parseInstruction() = when {
        startsWith("addx") -> Addx(this.split(' ')[1].toInt())
        startsWith("noop") -> Noop()
        else -> { throw IllegalStateException("Bad instruction") }
    }
}
