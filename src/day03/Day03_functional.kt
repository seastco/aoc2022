package day03

import readLines


/**
 * Credit goes to tginsberg; experimenting with his solutions to better learn functional programming in Kotlin.
 * Files without the _functional suffix are my original solutions.
 */

private fun Char.priority(): Int =
    when (this) {
        in 'a'..'z' -> (this - 'a') + 1
        in 'A'..'Z' -> (this - 'A') + 27
        else -> throw IllegalArgumentException("Letter not in range: $this")
    }

private fun List<String>.sharedItem(): Char =
    map { it.toSet() } // convert List<String> to List<Set<Char>>
        .reduce { left, right -> left intersect right } // intersect each set from left to right
        .first() // return the shared Char

private fun String.sharedItem(): Char =
    listOf(
        substring(0..length / 2),
        substring(length / 2)
    ).sharedItem()

private fun part1(input: List<String>): Int {
    return input.sumOf { it.sharedItem().priority() }
}

private fun part2(input: List<String>): Int {
    return input.chunked(3).sumOf { it.sharedItem().priority() }
}

fun main() {
    println(part1(readLines("day03/test")))
    println(part1(readLines("day03/input")))
    println(part2(readLines("day03/test")))
    println(part2(readLines("day03/input")))
}
