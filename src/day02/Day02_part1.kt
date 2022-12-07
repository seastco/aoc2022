package day02

import readLines

fun main() {
    val outcomes: Map<String, Int> = hashMapOf(
        "A X" to 4,
        "A Y" to 8,
        "A Z" to 3,
        "B X" to 1,
        "B Y" to 5,
        "B Z" to 9,
        "C X" to 7,
        "C Y" to 2,
        "C Z" to 6
    )

    fun part1(input: List<String>): Int {
        var score = 0

        for (game in input) {
            score += outcomes.getValue(game)
        }

        return score
    }

    println(part1(readLines("day02/test")))
    println(part1(readLines("day02/input")))
}
