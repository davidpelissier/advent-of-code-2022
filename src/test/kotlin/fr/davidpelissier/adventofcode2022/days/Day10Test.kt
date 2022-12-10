package fr.davidpelissier.adventofcode2022.days

import fr.davidpelissier.adventofcode2022.DayTest

class Day10Test : DayTest<Day10>() {

    override val day = Day10
    override val expectPartOne = 13140
    override val expectPartTwo = listOf(
        "##..##..##..##..##..##..##..##..##..##..",
        "###...###...###...###...###...###...###.",
        "####....####....####....####....####....",
        "#####.....#####.....#####.....#####.....",
        "######......######......######......####",
        "#######.......#######.......#######.....")
}