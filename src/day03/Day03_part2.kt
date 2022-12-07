package day03

import readLines
import kotlin.text.*

fun main() {
    fun part2(rucksacks: List<String>): Int {
        var total = 0
        val groups = rucksacks.chunked(3)
        for (group in groups) {
            val set1 = group.get(0).toSet()
            val set2 = group.get(1).toSet()
            val set3 = group.get(2).toSet()
            val sharedChar = set1.intersect(set2).intersect(set3).distinct()[0]
            val priority = if (sharedChar.isLowerCase()) sharedChar.code - 96 else sharedChar.code - 38
            total += priority
        }

        return total
    }

    println(part2(readLines("day03/test")))
    println(part2(readLines("day03/input")))
}