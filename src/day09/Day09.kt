package day09

import readLines

data class Knot(var x: Int, var y: Int)

enum class Direction(val x: Int, val y: Int) {
    R(1, 0),
    L(-1, 0),
    U(0, 1),
    D(0, -1)
}

fun part1(input: List<String>): Int {
    val set = HashSet<Pair<Int, Int>>()
    set.add(Pair(0, 0))
    val head = Knot(0, 0)
    val tail = Knot(0, 0)

    for (move in input) {
        val direction = Direction.valueOf(move.substringBefore(" "))
        var distance = move.substringAfter(" ").toInt()

        while (distance > 0) {
            head.x += direction.x
            head.y += direction.y

            val xDistance = head.x - tail.x
            val yDistance = head.y - tail.y

            if (yDistance == -2) {
                tail.x = head.x
                tail.y = head.y + 1
            } else if (yDistance == 2) {
                tail.x = head.x
                tail.y = head.y - 1
            } else if (xDistance == -2) {
                tail.x = head.x + 1
                tail.y = head.y
            } else if (xDistance == 2) {
                tail.x = head.x - 1
                tail.y = head.y
            }

            set.add(Pair(tail.x, tail.y))
            distance -= 1
        }
    }

    return set.size
}

fun part2(input: List<String>): Int {
    val rope = ArrayList<Knot>()
    (1..10).map { rope.add(Knot(0, 0)) }

    val set = HashSet<Pair<Int, Int>>()
    set.add(Pair(0, 0))

    for (move in input) {
        val direction = Direction.valueOf(move.substringBefore(" "))
        var distance = move.substringAfter(" ").toInt()

        while (distance > 0) {
            rope[0].x += direction.x
            rope[0].y += direction.y

            for (i in 1..9) {
                val knot1 = rope[i-1]
                val knot2 = rope[i]

                val xDistance = knot1.x - knot2.x
                val yDistance = knot1.y - knot2.y

                if (xDistance == 2 && yDistance == 2) {
                    knot2.x += 1
                    knot2.y += 1
                } else if (xDistance == -2 && yDistance == -2) {
                    knot2.x -= 1
                    knot2.y -= 1
                } else if (xDistance == -2 && yDistance == 2) {
                    knot2.x -= 1
                    knot2.y += 1
                } else if (xDistance == 2 && yDistance == -2) {
                    knot2.x += 1
                    knot2.y -= 1
                } else if (yDistance == -2) {
                    knot2.x = knot1.x
                    knot2.y = knot1.y + 1
                } else if (yDistance == 2) {
                    knot2.x = knot1.x
                    knot2.y = knot1.y - 1
                } else if (xDistance == -2) {
                    knot2.x = knot1.x + 1
                    knot2.y = knot1.y
                } else if (xDistance == 2) {
                    knot2.x = knot1.x - 1
                    knot2.y = knot1.y
                } else {
                    continue
                }

                if (i == 9) {
                    set.add(Pair(knot2.x, knot2.y))
                }
            }

            distance -= 1
        }
    }

    return set.size
}

fun main() {
    val input = readLines("day09/input")
    println(part1(input))
    println(part2(input))
}
