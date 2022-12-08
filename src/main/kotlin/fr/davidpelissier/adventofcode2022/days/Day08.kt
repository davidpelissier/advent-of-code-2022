package fr.davidpelissier.adventofcode2022.days

import fr.davidpelissier.adventofcode2022.Day

object Day08 : Day(8, "Treetop Tree House") {

    override fun part1() = edges() + inner().count()

    override fun part2() = computeScenicScore(inputList.map { it.chunked(1) }, inner())

    data class Tree(val name: Int, val positionX: Int, val positionY: Int) {

        fun computeScenicScoreForOneTree(map: List<List<String>>) =
            viewToTop(map)
                .times(viewToBottom(map))
                .times(viewToLeft(map))
                .times(viewToRight(map))

        private fun viewToTop(map: List<List<String>>): Int {
            var count = 0;
            for(i in 1..this.positionY) {
                if (map[this.positionY - i][this.positionX].toInt() < this.name) {
                    count++
                } else {
                    count++
                    break
                }
            }
            return count
        }

        private fun viewToBottom(map: List<List<String>>): Int {
            var count = 0;
            for(i in 1 until map.size - this.positionY) {
                if (map[this.positionY + i][this.positionX].toInt() < this.name) {
                    count++
                } else {
                    count++
                    break
                }
            }
            return count
        }

        private fun viewToLeft(map: List<List<String>>): Int {
            var count = 0;
            for(i in 1 .. this.positionX) {
                if (map[this.positionY][this.positionX - i].toInt() < this.name) {
                    count++
                } else {
                    count++
                    break
                }
            }
            return count
        }

        private fun viewToRight(map: List<List<String>>): Int {
            var count = 0;
            for(i in 1 until map.size - this.positionX) {
                if (map[this.positionY][this.positionX + i].toInt() < this.name) {
                    count++
                } else {
                    count++
                    break
                }
            }
            return count
        }
    }

    private fun edges(): Int {
        return inputList.mapIndexed { index, line ->
            when (index) {
                0, inputList.size -1 -> line.length
                else -> 2
            }
        } .sum()
    }

    private fun inner(): Set<Tree> {
        return fromLeft(inputList)
            .union(fromRight(inputList).toSet())
            .union(fromTop(inputList).toSet())
            .union(fromBottom(inputList).toSet())
    }

    private fun fromLeft(patch: List<String>): List<Tree> {
        val map = patch.drop(1).dropLast(1).map { it.chunked(1) }
        return buildList {
            for (x in map.indices) {
                val line = map[x]
                var max = line[0].toInt()
                for (y in 0.. line.size - 3) {
                    val current = line[y + 1].toInt()
                    if (current > max) {
                        add(Tree(current, y + 1, x + 1))
                        max = current
                    }
                }
            }
        }
    }

    private fun fromRight(patch: List<String>): List<Tree> {
        val map = patch.drop(1).dropLast(1).map { it.chunked(1) }
        return buildList {
            for (x in map.indices) {
                val line = map[x].reversed()
                var max = line[0].toInt()
                for (y in 0.. line.size - 3) {
                    val current = line[y + 1].toInt()
                    if (current > max) {
                        add(Tree(current,line.size - 2 - y, x + 1))
                        max = current
                    }
                }
            }
        }
    }

    private fun fromTop(patch: List<String>): List<Tree> {
        val map = patch.map { it.chunked(1) }
        return buildList {
            for (x in 0..map.size - 3) {
                val line = map[x].drop(1).dropLast(1)
                var max = map[0][x + 1].toInt()
                for (y in line.indices) {
                    val current = map[y + 1][x + 1].toInt()
                    if (current > max) {
                        add(Tree(current, x + 1, y + 1))
                        max = current
                    }
                }
            }
        }
    }

    private fun fromBottom(patch: List<String>): List<Tree> {
        val map = patch.reversed().map { it.chunked(1) }
        return buildList {
            for (x in 0..map.size - 3) {
                val line = map[x].drop(1).dropLast(1)
                var max = map[0][x + 1].toInt()
                for (y in line.indices) {
                    val current = map[y + 1][x + 1].toInt()
                    if (current > max) {
                        add(Tree(current, x + 1, line.size - y))
                        max = current
                    }
                }
            }
        }
    }

    private fun computeScenicScore(map: List<List<String>>, trees: Set<Tree>): Int {
        return trees.maxOfOrNull { it.computeScenicScoreForOneTree(map) }!!
    }

}