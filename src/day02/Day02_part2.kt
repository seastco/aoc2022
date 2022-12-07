package day02

import readLines

fun main() {
    val outcomes: MutableMap<String, Int> = hashMapOf(
        "A X" to 3,
        "A Y" to 4,
        "A Z" to 8,
        "B X" to 1,
        "B Y" to 5,
        "B Z" to 9,
        "C X" to 2,
        "C Y" to 6,
        "C Z" to 7
    )

    fun part2(input: List<String>): Int {
        var score = 0

        for (game in input) {
            score += outcomes.getValue(game)
        }

        return score
    }

    println(part2(readLines("day02/test")))
    println(part2(readLines("day02/input")))
}
