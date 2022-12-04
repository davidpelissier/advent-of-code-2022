package fr.davidpelissier.adventofcode2022

import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.Test

abstract class DayTest<T : Day> {

    abstract val day: T
    abstract val expectPartOne: Number
    abstract val expectPartTwo: Number

    @Test
    fun `part 1 sample is ok`() {
        assertThat(expectPartOne).isEqualTo(day.part1())
    }

    @Test
    fun `part 2 sample is ok`() {
        assertThat(expectPartTwo).isEqualTo(day.part2())
    }
}
