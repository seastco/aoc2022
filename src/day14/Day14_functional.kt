package day14

import Point2D
import readLines

/**
 * Credit goes to tginsberg (https://github.com/tginsberg/advent-2022-kotlin)
 * I'm experimenting with his solutions to better learn functional programming in Kotlin.
 * Files without the _functional suffix are my original solutions.
 */

private fun Point2D.down(): Point2D = Point2D(x, y + 1)
private fun Point2D.downLeft(): Point2D = Point2D(x - 1, y + 1)
private fun Point2D.downRight(): Point2D = Point2D(x + 1, y + 1)

private fun parseInput(input: List<String>): MutableSet<Point2D> {
    return input.flatMap { row ->
        row.split(" -> ") // for each row, split by -> to get a list of coordinates
            .map { Point2D.of(it) } // for each coordinate, translate to Point2D
            .zipWithNext() // zipWithNext returns a list of pairs of each two adjacent elements, similar to windowed(2,1)
            .flatMap { (from, to) -> from.lineTo(to) } // for each zipped pair get the line points between the two
    }.toMutableSet()
}

private fun dropSand(cave: MutableSet<Point2D>, voidStartsAt: Int): Int {
    val sandSource = Point2D(500, 0)
    var start = sandSource
    var landed = 0

    while (true) {
        // if none of those are free, the grain of sand has come to rest, so we’ll return null.
        val next = listOf(start.down(), start.downLeft(), start.downRight()).firstOrNull { it !in cave }

        start = when {
            // case (1) - if next == null and we're back at the source, return landed count
            next == null && start == sandSource -> return landed

            // case (2) - if next == null and we're NOT back at the source, we know the grain of sand has come to rest
            // we add the previous state (start) to the cave, increment the landed counter, and set the start to the
            // sandSource so the next time through the loop start dropping a new grain of sand
            next == null -> {
                cave.add(start)
                landed += 1
                sandSource
            }

            // case (3) if we have fallen into the void, return landed count
            next.y == voidStartsAt -> return landed

            // case (4) set start as next valid point
            else -> next
        }
    }
}

private fun part1(input: List<String>): Int {
    val cave: MutableSet<Point2D> = parseInput(input)
    val maxY: Int = cave.maxOf { it.y }
    return dropSand(cave, maxY + 1)
}

private fun part2(input: List<String>): Int {
    val cave: MutableSet<Point2D> = parseInput(input)
    val maxY: Int = cave.maxOf { it.y }
    val minX: Int = cave.minOf { it.x }
    val maxX: Int = cave.maxOf { it.x }
    cave.addAll(Point2D(minX - maxY - maxY, maxY + 2).lineTo(Point2D(maxX + maxY + maxY, maxY + 2)))

    return dropSand(cave, maxY + 3) + 1
}

fun main() {
    println(part1(readLines("day14/test")))
    println(part2(readLines("day14/test")))
    println(part1(readLines("day14/input")))
    println(part2(readLines("day14/input")))
}