package day09

import readLines
import kotlin.math.absoluteValue
import kotlin.math.sign

/**
 * Credit goes to tginsberg; experimenting with his solutions to better learn functional programming in Kotlin.
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

private fun followPath(knots: Int, path: String): Int {
    val rope = Array(knots) { Point() }
    val tailVisits = mutableSetOf(Point())

    path.forEach { direction ->
        rope[0] = rope[0].move(direction)
        rope.indices.windowed(2, 1) { (head, tail) ->
            if (!rope[head].touches(rope[tail])) {
                rope[tail] = rope[tail].moveTowards(rope[head])
            }
        }
        tailVisits += rope.last()
    }
    return tailVisits.size
}

private fun followPathToBeRefactored(path: String): Int {
    var head = Point()
    var tail = Point()
    val tailVisits = mutableSetOf(Point())

    path.forEach { direction ->
        head = head.move(direction)
        if (!head.touches(tail)) {
            tail = tail.moveTowards(head)
        }
        tailVisits += tail
    }
    return tailVisits.size
}

private fun part1(input: List<String>): Int {
    val path: String = parseInput(input)
    return 0
}

fun part2(input: String) {

}

private fun parseInput(input: List<String>): String =
    input.joinToString("") { row -> // for each row, translate "U 4" to "UUUU"
        val direction = row.substringBefore(" ")
        val numberOfMoves = row.substringAfter(' ').toInt()
        direction.repeat(numberOfMoves) // U.repeat(4) --> UUUU
    }

fun main() {
    part1(readLines("day09/test1"))
}