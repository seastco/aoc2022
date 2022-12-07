package day04

import readLines

fun main() {
    fun part1(pairs: List<String>): Int {
        var total = 0

        for (pair in pairs) {
            val assignments = pair.split(",")

            val assignment1 = assignments[0]
            val sections1 = assignment1.split("-")
            val x1 = sections1[0].toInt()
            val y1 = sections1[1].toInt()

            val assignment2 = assignments[1]
            val sections2 = assignment2.split("-")
            val x2 = sections2[0].toInt()
            val y2 = sections2[1].toInt()

            val overlap = (x1 >= x2 && y1 <= y2) || (x1 <= x2 && y1 >= y2)
            total += if (overlap) 1 else 0
        }

        return total
    }

    println(part1(readLines("day04/test")))
    println(part1(readLines("day04/input")))
}
