package day06

import readText

private fun String.findStartMarker(startMarkerSize: Int): Int =
    withIndex()
        .windowed(startMarkerSize, 1) // sliding window
        .first { window -> // find the first window that has a set of 4 characters
            window.map { it.value }.toSet().size == startMarkerSize
        }
        .last().index + 1 // grab the last character of that window and get its position by doing index + 1

fun part1(input: String): Int {
    return input.findStartMarker(4)
}

fun part2(input: String): Int {
    return input.findStartMarker(14)
}

fun main() {
    println(part1(readText("day06/test")))
    println(part1(readText("day06/input")))
    println(part2(readText("day06/test")))
    println(part2(readText("day06/input")))
}