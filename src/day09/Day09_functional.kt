package day09

import readLines
import kotlin.math.absoluteValue
import kotlin.math.sign




/**
 * Credit goes to tginsberg (https://github.com/tginsberg/advent-2022-kotlin)
 * I'm experimenting with his solutions to better learn functional programming in Kotlin.
 * Files without the _functional suffix are my original solutions.
 */

private data class Point(val x: Int = 0, val y: Int = 0) {
    fun move(direction: Char): Point =
        when (direction) {
            'U' -> copy(y = y + 1)
            'D' -> copy(y = y - 1)
            'L' -> copy(x = x - 1)
            'R' -> copy(x = x + 1)
            else -> throw IllegalArgumentException("Unknown Direction: $direction")
        }

    fun touches(other: Point): Boolean =
        (x - other.x).absoluteValue <= 1 && (y - other.y).absoluteValue <= 1

    fun moveTowards(other: Point): Point =
        Point(
            (other.x - x).sign + x,
            (other.y - y).sign + y
        )
}

private fun followPath(path: String, knots: Int): Int {
    val rope = Array(knots) { Point() } // initialize rope array with n=knots Points
    val tailVisits = mutableSetOf(Point())

    path.forEach { direction ->
        rope[0] = rope[0].move(direction)
        rope.indices.windowed(2, 1) { (head, tail) -> // use windowed to go through [0,1],[1,0]...[8,9]
            if (!rope[head].touches(rope[tail])) {
                rope[tail] = rope[tail].moveTowards(rope[head])
            }
        }
        tailVisits += rope.last()
    }
    return tailVisits.size
}

private fun part1(input: List<String>): Int {
    val path: String = parseInput(input)
    return followPath(path, 2)
}

private fun part2(input: List<String>): Int {
    val path: String = parseInput(input)
    return followPath(path, 10)
}

private fun parseInput(input: List<String>): String =
    input.joinToString("") { row -> // for each row, translate "U 4" to "UUUU"
        val direction = row.substringBefore(" ")
        val numberOfMoves = row.substringAfter(' ').toInt()
        direction.repeat(numberOfMoves) // U.repeat(4) --> UUUU
    }

fun main() {
    println(part1(readLines("day09/test1")))
    println(part2(readLines("day09/test2")))
}