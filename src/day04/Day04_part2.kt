package day04

import readLines

fun main() {
    fun part2(pairs: List<String>): Int {
        var total = 0

        // e.g. 10-15,3-10
        for (pair in pairs) {

            // ["10-15", "3-10"]
            val assignments = pair.split(",")

            // [10,15]
            val assignment1 = assignments[0].split("-").map { string -> string.toInt() }

            // [3,10]
            val assignment2 = assignments[1].split("-").map { string -> string.toInt() }

            // find the assignment with the first starting point
            // first == [3,10], second == [10,15]
            val firstAssignment = if (assignment1[0] <= assignment2[0]) assignment1 else assignment2
            val secondAssignment = if (assignment1[0] <= assignment2[0]) assignment2 else assignment1

            // now, check for overlap by comparing 10 >= 10
            total += if (firstAssignment[1] >= secondAssignment[0]) 1 else 0
        }

        return total
    }

    println(part2(readLines("day04/test")))
    println(part2(readLines("day04/input")))
}
