package day11

import readLines

/**
 * Credit goes to tginsberg (https://github.com/tginsberg/advent-2022-kotlin)
 * I'm experimenting with his solutions to better learn functional programming in Kotlin.
 * Files without the _functional suffix are my original solutions.
 */

private class MonkeyV2(
    val items: MutableList<Long>,
    val operation: (Long) -> Long,
    val test: Long,
    val trueMonkey: Int,
    val falseMonkey: Int
) {

    var interactions: Long = 0

    fun inspectItems(monkeys: List<MonkeyV2>, changeToWorryLevel: (Long) -> Long) {
        items.forEach { item ->
            val worry = changeToWorryLevel(operation(item))
            val target = if (worry % test == 0L) trueMonkey else falseMonkey
            monkeys[target].items.add(worry)
        }
        interactions += items.size
        items.clear()
    }

    companion object {
        fun of(input: List<String>): MonkeyV2 {
            val items = input[1].substringAfter(": ").split(", ").map { it.toLong() }.toMutableList()

            val operationValue = input[2].substringAfterLast(" ")
            val operation: (Long) -> Long = when {
                operationValue == "old" -> ({ it * it })
                '*' in input[2] -> ({ it * operationValue.toLong() })
                else -> ({ it + operationValue.toLong() })
            }

            val test = input[3].substringAfterLast(" ").toLong()
            val trueMonkey = input[4].substringAfterLast(" ").toInt()
            val falseMonkey = input[5].substringAfterLast(" ").toInt()

            return MonkeyV2(
                items,
                operation,
                test,
                trueMonkey,
                falseMonkey
            )
        }
    }
}

private fun List<MonkeyV2>.business(): Long =
    sortedByDescending { it.interactions }.let { it[0].interactions * it[1].interactions }

private fun rounds(monkeys: List<MonkeyV2>, numRounds: Int, changeToWorryLevel: (Long) -> Long) {
    repeat(numRounds) {
        monkeys.forEach { it.inspectItems(monkeys, changeToWorryLevel) }
    }
}

fun part1(input: List<String>): Long {
    val monkeys: List<MonkeyV2> = input.chunked(7).map { MonkeyV2.of(it) }
    rounds(monkeys, 20) { it / 3 }
    return monkeys.business()
}

fun part2(input: List<String>): Long {
    val monkeys: List<MonkeyV2> = input.chunked(7).map { MonkeyV2.of(it) }
    val testProduct: Long = monkeys.map { it.test }.reduce(Long::times)
    rounds(monkeys, 10_000) { it % testProduct }
    return monkeys.business()
}

fun main(){
    println(part1(readLines("day11/test")))
    println(part2(readLines("day11/test")))
    println(part1(readLines("day11/input")))
    println(part2(readLines("day11/input")))
}