package day01

import readInput

fun main() {
    fun part2(input: List<String>): Int {
        val totals = ArrayList<Int>()
        var total = 0

        for (calorie in input) {
            if (calorie.isEmpty()) {
                totals.add(total)
                total = 0
            } else {
                total += calorie.toInt()
            }
        }

        totals.sort()
        return totals.takeLast(3).sum()
    }

    println(part2(readInput("day01/test")))
    println(part2(readInput("day01/input")))
}