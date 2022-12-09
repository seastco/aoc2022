package day01

import readText

fun main() {
    fun parseInput(input: String): List<Int> {
        return input
            .trim()
            .split("\n\n")
            .map { it.lines().sumOf(String::toInt) }
            .sortedDescending()
    }


    fun part1(input: String): Int {
        val calories = parseInput(input)
        return calories.first()
    }

    fun part2(input: String): Int {
        val calories = parseInput(input)
        return calories.take(3).sum()
    }

    println(part1(readText("day01/test")))
    println(part1(readText("day01/input")))
    println(part2(readText("day01/test")))
    println(part2(readText("day01/input")))
}