package day04

import readLines

/**
 * Credit goes to tginsberg (https://github.com/tginsberg/advent-2022-kotlin)
 * I'm experimenting with his solutions to better learn functional programming in Kotlin.
 * Files without the _functional suffix are my original solutions.
 */

private fun String.asIntRange(): IntRange =
    substringBefore("-").toInt()..substringAfter("-").toInt()

private fun String.asRanges(): Pair<IntRange,IntRange> =
    substringBefore(",").asIntRange() to substringAfter(",").asIntRange()


private infix fun IntRange.fullyOverlaps(other: IntRange): Boolean =
    first <= other.first && last >= other.last

private infix fun IntRange.overlaps(other: IntRange): Boolean =
    first <= other.last && other.first <= last

private fun part1(input: List<String>): Int {
    val ranges: List<Pair<IntRange, IntRange>> = input.map { it.asRanges() }
    return ranges.count { it.first fullyOverlaps it.second || it.second fullyOverlaps it.first }
}

private fun part2(input: List<String>): Int {
    val ranges: List<Pair<IntRange, IntRange>> = input.map { it.asRanges() }
    return ranges.count { it.first overlaps it.second }
}

fun main() {
    println(part1(readLines("day04/test")))
    println(part1(readLines("day04/input")))
    println(part2(readLines("day04/test")))
    println(part2(readLines("day04/input")))
}
