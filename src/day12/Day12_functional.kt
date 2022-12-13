package day12

import readLines
import java.util.PriorityQueue

/**
 * Credit goes to tginsberg (https://github.com/tginsberg/advent-2022-kotlin)
 * I'm experimenting with his solutions to better learn functional programming in Kotlin.
 * Files without the _functional suffix are my original solutions.
 */

data class Point2D(val x: Int = 0, val y: Int = 0) {
    fun cardinalNeighbors(): Set<Point2D> =
        setOf(
            copy(x = x - 1),
            copy(x = x + 1),
            copy(y = y - 1),
            copy(y = y + 1)
        )
}

data class PathCost(val point: Point2D, val cost: Int) : Comparable<PathCost> {
    override fun compareTo(other: PathCost): Int = this.cost.compareTo(other.cost)
}

class HeightMap(val elevations: Map<Point2D, Int>, val start: Point2D, val end: Point2D) {

    fun shortestPath(begin: Point2D, isGoal: (Point2D) -> Boolean, canMove: (Int, Int) -> Boolean): Int {
        val seen = mutableSetOf<Point2D>()
        val queue = PriorityQueue<PathCost>().apply { add(PathCost(begin, 0)) }

        while (queue.isNotEmpty()) {
            val nextPoint = queue.poll()

            if (nextPoint.point !in seen) {
                seen += nextPoint.point
                val neighbors = nextPoint.point.cardinalNeighbors()
                    .filter { it in elevations } // filters OUT anything off the map
                    .filter { canMove(elevations.getValue(nextPoint.point), elevations.getValue(it)) } // gathers valid moves
                if (neighbors.any { isGoal(it) }) return nextPoint.cost + 1 // if we found what we're looking for, return cost+1
                queue.addAll(neighbors.map { PathCost(it, nextPoint.cost + 1) }) // else add PathCosts to queue
            }
        }

        throw IllegalStateException("No valid path from $start to $end")
    }
}

private fun parseInput(input: List<String>): HeightMap {
    var start: Point2D? = null
    var end: Point2D? = null
    val elevations = input.flatMapIndexed { y, row -> // flatMapIndexed takes a bunch of lists and flattens/combines them
        row.mapIndexed { x, char -> // for each (index, char) in row
            val here = Point2D(x, y)
            here to when (char) { // "here to" is creating a pair with the result ("height" of the char) from the when statement
                'S' -> 0.also { start = here }
                'E' -> 25.also { end = here }
                else -> char - 'a'
            }
        }
    }.toMap()
    return HeightMap(elevations, start!!, end!!)
}

fun part1(heightMap: HeightMap): Int {
    return heightMap.shortestPath(
        begin = heightMap.start,
        isGoal = { it == heightMap.end },
        canMove = { from, to -> to - from <= 1 }
    )
}

fun part2(heightMap: HeightMap): Int {
    return heightMap.shortestPath(
        begin = heightMap.end,
        isGoal = { heightMap.elevations[it] == 0 },
        canMove = { from, to -> from - to <= 1 }
    )
}

fun main() {
    println(part1(parseInput(readLines("day12/test"))))
    println(part2(parseInput(readLines("day12/test"))))
    println(part1(parseInput(readLines("day12/input"))))
    println(part2(parseInput(readLines("day12/input"))))
}