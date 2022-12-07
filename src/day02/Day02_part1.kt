package day02

import readLines

fun main() {
    val outcomes: Map<String, Int> = java.util.Map.of(
        "A X", 4,
        "A Y", 8,
        "A Z", 3,
        "B X", 1,
        "B Y", 5,
        "B Z", 9,
        "C X", 7,
        "C Y", 2,
        "C Z", 6,
    )

    fun part1(input: List<String>): Int {
        var score = 0

        for (game in input) {
            score += outcomes.getOrDefault(game, 0)
        }

        return score
    }

    println(part1(readLines("day02/test")))
    println(part1(readLines("day02/input")))
}
