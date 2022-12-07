package day02

import readLines

fun main() {
    val outcomes: Map<String, Int> = mapOf(
        "A X" to 1 + 3,
        "A Y" to 2 + 6,
        "A Z" to 3 + 0,
        "B X" to 1 + 0,
        "B Y" to 2 + 3,
        "B Z" to 3 + 6,
        "C X" to 1 + 6,
        "C Y" to 2 + 0,
        "C Z" to 3 + 3
    )

    fun part1(input: List<String>): Int {
        return input.sumOf { outcomes[it] ?: error( "Unknown input: $it") }
    }

    println(part1(readLines("day02/test")))
    println(part1(readLines("day02/input")))
}
