package day10

import readLines
import kotlin.math.absoluteValue

private fun parseInput(input: List<String>): List<Int> =
    buildList {
        add(1) // register starts at 1
        input.forEach { line ->
            add(0) // no-op and first cycle of addx will be 0
            if (line.startsWith("addx")) {
                add(line.substringAfter(" ").toInt())
            }
        }
    }

private fun List<Int>.sampleSignals(): List<Int> =
    (20 .. size step 40).map { cycle ->
        cycle * this[cycle - 1]
    }

private fun List<Int>.screen(): List<Boolean> =
    this.mapIndexed { pixel, register ->
        (register - (pixel % 40)).absoluteValue <= 1
    }

private fun List<Boolean>.print() {
    this.windowed(40, 40, false).forEach { row ->
        row.forEach { pixel ->
            print(if(pixel) '#' else ' ')
        }
        println()
    }
}

private fun part1(input: List<String>): Int {
    val signals = parseInput(input).runningReduce(Int::plus)
    return signals.sampleSignals().sum()
}

private fun part2(input: List<String>) {
    val signals = parseInput(input).runningReduce(Int::plus)
    signals.screen().print()
}

fun main() {
    println(part1(readLines("day10/input")))
    part2(readLines("day10/input"))
}