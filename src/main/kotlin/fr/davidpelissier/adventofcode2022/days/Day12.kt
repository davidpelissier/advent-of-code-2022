package fr.davidpelissier.adventofcode2022.days

import fr.davidpelissier.adventofcode2022.Day
import java.util.PriorityQueue

object Day12 : Day(12, "Hill Climbing Algorithm") {

    override fun part1(): Int {
        val map: ElevationMap = inputList.createMap()
        return map.shortestPath(map.start(), map.end())
    }

    override fun part2(): Int {
        val map: ElevationMap = inputList.createMap()
        val starts = map.elevations.filter { it.height == 0 }
        return starts.minOf { map.shortestPath(it, map.end()) }
    }

    private fun List<String>.createMap(): ElevationMap {
        val elevations = flatMapIndexed { y, row ->
            row.mapIndexed { x, char ->
                when (char) {
                    'S' -> Point('S', x, y, 0)
                    'E' -> Point('E', x, y, 25)
                    else -> Point(char, x, y, char.code - 'a'.code)
                }
            }
        }
        return ElevationMap(elevations)
    }

    private fun isAccessible(from: Point, to: Point): Boolean {
        return to.height - from.height <= 1
    }

    class ElevationMap(val elevations: List<Point>) {

        fun start() = elevations.first { it.name == 'S'}

        fun end() = elevations.first { it.name == 'E'}

        fun shortestPath(start: Point, end: Point): Int {
            var result = 0;
            val visited = mutableSetOf<Point>()
            val queue = PriorityQueue<PathCost>().apply { add(PathCost(start, 0)) }
            while (queue.isNotEmpty()) {
                val nextPoint = queue.poll()
                if (nextPoint.point !in visited) {
                    visited += nextPoint.point
                    val adjacents = nextPoint.point.adjacents(elevations)
                        .filter { isAccessible(nextPoint.point, it) }
                    if (adjacents.any { it == end }) result = nextPoint.cost + 1
                    queue.addAll(adjacents.map { PathCost(it, nextPoint.cost + 1) })
                }
            }
            return result
        }
    }

    data class Point(val name: Char, val x: Int, val y: Int, val height: Int) {

        fun adjacents(elevations: List<Point>): Set<Point> {
            return listOf(Pair(x - 1, y), Pair(x + 1, y), Pair(x, y - 1), Pair(x, y + 1))
                .mapNotNull { pair -> elevations.find { it.x == pair.first && it.y == pair.second } }
                .toSet()
        }

    }

    data class PathCost(val point: Point, val cost: Int) : Comparable<PathCost> {
        override fun compareTo(other: PathCost) = this.cost.compareTo(other.cost)
    }
}