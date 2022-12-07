package day01

import readText

fun main() {
    fun parseInput(input: String): List<List<Int>> {
        return input.split("\n\n").map { elf -> elf.lines().map { it.toInt() }}
    }

    fun List<List<Int>>.topNElves(n: Int): Int {
        return map { it.sum() }
            .sortedDescending()
            .take(n)
            .sum()
    }

    fun part1(input: String): Int {
        val data = parseInput(input)
        return data.topNElves(1)
    }

    fun part2(input: String): Int {
        val data = parseInput(input)
        return data.topNElves(3)
    }

    println(part1(readText("day01/test")))
    println(part1(readText("day01/input")))
    println(part2(readText("day01/test")))
    println(part2(readText("day01/input")))
}