package day11

import readText

data class Monkey(var items: MutableList<Long>, val operation: List<String>, val divisor: Long, val trueMonkey: Int, val falseMonkey: Int) {
    var inspected = 0L

    fun getWorryLevel(item: Long): Long {
        val operator = operation[0]
        val number = if (operation[1] == "old") item else operation[1].toLong()

        return when (operator) {
            "+" -> item + number
            "*" -> item * number
            else -> 0L
        }
    }
}

private fun parseInput(input: String): Pair<List<Monkey>, Long> {
    val monkeyInput = input.split("\n\n")

    val monkeys = monkeyInput.map {
        val monkeyDescription = it.split("\n")

        val items = monkeyDescription[1]
            .substringAfter(":").trim()
            .split(",")
            .map { item -> item.trim().toLong() }.toMutableList()

        val operation = monkeyDescription[2]
            .split(" ")
            .takeLast(2)

        val divisor = monkeyDescription[3].split(" ").last().toLong()
        val trueMonkey = monkeyDescription[4].split(" ").last().toInt()
        val falseMonkey = monkeyDescription[5].split(" ").last().toInt()
        Monkey(items, operation, divisor, trueMonkey, falseMonkey)
    }

    val lcm = monkeys.map { it.divisor }.reduce { acc, bigInteger -> acc * bigInteger }
    return monkeys to lcm
}

private fun part1(input: String): Long {
    val (monkeys) = parseInput(input)

    repeat(20) {
        monkeys.forEach {
            for (i in it.items.indices) {
                val item = it.items[i]
                val worryLevel = it.getWorryLevel(item) / 3
                val nextMonkey = if (worryLevel % it.divisor == 0L) it.trueMonkey else it.falseMonkey
                monkeys[nextMonkey].items.add(worryLevel)
            }

            it.inspected += it.items.size
            it.items.clear()
        }
    }

    val monkeyBusiness = monkeys
        .sortedByDescending { it.inspected }
        .take(2)

    return monkeyBusiness[0].inspected * monkeyBusiness[1].inspected
}

private fun part2(input: String): Long {
    val (monkeys, lcm) = parseInput(input)

    repeat(10000) {
        monkeys.forEach {
            for (i in it.items.indices) {
                val item = it.items[i]
                val worryLevel = it.getWorryLevel(item) % lcm
                val nextMonkey = if (worryLevel % it.divisor == 0L) it.trueMonkey else it.falseMonkey
                monkeys[nextMonkey].items.add(worryLevel)
            }

            it.inspected += it.items.size
            it.items.clear()
        }
    }

    val monkeyBusiness = monkeys
        .sortedByDescending { it.inspected }
        .take(2)

    return monkeyBusiness[0].inspected * monkeyBusiness[1].inspected
}

fun main() {
    println(part1(readText("day11/test")))
    println(part1(readText("day11/input")))
    println(part2(readText("day11/test")))
    println(part2(readText("day11/input")))
}