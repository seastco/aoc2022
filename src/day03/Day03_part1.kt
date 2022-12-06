package day03

import readInput

fun main() {
    fun part1(rucksacks: List<String>): Int {
        var total = 0

        for (rucksack in rucksacks) {
            val length = rucksack.length
            val firstCompartment = rucksack.slice(0 until length/2)
            val secondCompartment = rucksack.slice(length/2 until rucksack.length)
            val sharedChar = firstCompartment.toSet().intersect(secondCompartment.toSet()).distinct().get(0)
            val priority = if (sharedChar.isLowerCase()) sharedChar.code - 96 else sharedChar.code - 38
            total += priority
        }

        return total
    }

    println(part1(readInput("day03/test")))
    println(part1(readInput("day03/input")))
}
