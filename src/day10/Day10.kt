package day10

import readLines

private fun part1(input: List<String>): Int {
    var cycle = 0
    var register = 1
    val map = mutableMapOf<Int, Int>()

    input.forEach {
        val instruction = it.substringBefore(" ")

        if (instruction == "addx") {
            val number = it.substringAfter(" ").toInt()
            cycle += 1 // cycle one
            map[cycle] = register
            cycle += 1 // cycle two
            map[cycle] = register
            register += number
        } else {
            cycle += 1
            map[cycle] = register
        }
    }

    return (20..220 step 40).sumOf {
        it * map.getOrDefault(it, 0)
    }
}

private fun incrementCycle(sprite: IntRange, cycle: Int): Int {
    if (sprite.contains(cycle % 40)) {
        print("#")
    } else {
        print(".")
    }

    val incrementedCycle = cycle + 1

    if (incrementedCycle % 40 == 0) {
        println()
    }

    return incrementedCycle
}

private fun part2(input: List<String>) {
    var cycle = 0
    var register = 1
    var sprite = IntRange(0, 2)

    input.forEach {
        val instruction = it.substringBefore(" ")

        if (instruction == "addx") {
            val number = it.substringAfter(" ").toInt()
            cycle = incrementCycle(sprite, cycle)
            cycle = incrementCycle(sprite, cycle)
            register += number
            sprite = IntRange(register-1, register+1)
        } else {
            cycle = incrementCycle(sprite, cycle)
        }
    }
}

fun main() {
    println(part1(readLines("day10/input")))
    part2(readLines("day10/input"))
}