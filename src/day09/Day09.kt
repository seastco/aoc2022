package day09

import readLines
import kotlin.math.abs

data class Knot(var x: Int, var y: Int)

enum class Direction(val x: Int, val y: Int) {
    R(1, 0),
    L(-1, 0),
    U(0, 1),
    D(0, -1)
}

fun tailVisitedCount(input: List<String>, ropeLength: Int): Int {
    val rope = ArrayList<Knot>()
    (1..ropeLength).map { rope.add(Knot(0, 0)) }
    val set = HashSet<Pair<Int, Int>>()
    set.add(Pair(0, 0))

    for (move in input) {
        val direction = Direction.valueOf(move.substringBefore(" "))
        var distance = move.substringAfter(" ").toInt()

        while (distance > 0) {
            rope[0].x += direction.x
            rope[0].y += direction.y

            for (i in 1 until ropeLength) {
                val knot1 = rope[i-1]
                val knot2 = rope[i]
                val xDistance = knot1.x - knot2.x
                val yDistance = knot1.y - knot2.y

                if (abs(xDistance) == 2 && abs(yDistance) == 2) {
                    knot2.x += xDistance / 2
                    knot2.y += yDistance / 2
                } else if (abs(yDistance) == 2) {
                    knot2.x = knot1.x
                    knot2.y = knot1.y + (yDistance / 2) * -1
                } else if (abs(xDistance) == 2) {
                    knot2.x = knot1.x + (xDistance / 2) * -1
                    knot2.y = knot1.y
                }
            }

            set.add(Pair(rope[ropeLength-1].x, rope[ropeLength-1].y))
            distance -= 1
        }
    }

    return set.size
}

fun part1(input: List<String>): Int {
    return tailVisitedCount(input, 2)
}

fun part2(input: List<String>): Int {
    return tailVisitedCount(input, 10)
}

fun main() {
    val input = readLines("day09/input")
    println(part1(input))
    println(part2(input))
}
