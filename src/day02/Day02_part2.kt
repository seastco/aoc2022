package day02

import readLines

fun main() {
    val outcomes: Map<String, Int> = java.util.Map.of(
        "A X", 3,
        "A Y", 4,
        "A Z", 8,
        "B X", 1,
        "B Y", 5,
        "B Z", 9,
        "C X", 2,
        "C Y", 6,
        "C Z", 7,
    )

    fun part2(input: List<String>): Int {
        var score = 0

        for (game in input) {
            score += outcomes.getOrDefault(game, 0)
        }

        return score
    }

    println(part2(readLines("day02/test")))
    println(part2(readLines("day02/input")))
}
