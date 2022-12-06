package day01

import readInput
import kotlin.math.max

fun main() {
    fun part1(input: List<String>): Int {
        var max = 0
        var current = 0

        for (calorie in input) {
            if (calorie.isEmpty()) {
                max = max(max, current)
                current = 0
            } else {
                current += calorie.toInt()
            }
        }

        return max
    }

    println(part1(readInput("day01/test")))
    println(part1(readInput("day01/input")))
}
