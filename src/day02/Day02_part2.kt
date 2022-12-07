package day02

import readLines

fun main() {
    val outcomes: Map<String, Int> = mapOf(
        "A X" to 3 + 0,
        "A Y" to 1 + 3,
        "A Z" to 2 + 6,
        "B X" to 1 + 0,
        "B Y" to 2 + 3,
        "B Z" to 3 + 6,
        "C X" to 2 + 0,
        "C Y" to 3 + 3,
        "C Z" to 1 + 6
    )

    fun part2(input: List<String>): Int {
        return input.sumOf { outcomes[it] ?: error("Unknown input: $it")}
    }

    println(part2(readLines("day02/test")))
    println(part2(readLines("day02/input")))
}
