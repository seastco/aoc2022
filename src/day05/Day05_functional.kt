package day05

import readLines

/**
 * Credit goes to tginsberg (https://github.com/tginsberg/advent-2022-kotlin)
 * I'm experimenting with his solutions to better learn functional programming in Kotlin.
 * Files without the _functional suffix are my original solutions.
 */

private fun Iterable<Iterable<Char>>.tops(): String =
    map { it.first() }.joinToString("")

private fun createStacks(input: List<String>): List<MutableList<Char>> {
    val stackRows = input.takeWhile { it.contains('[') }

    return (1..stackRows.last().length step 4).map { index -> // take index 1-9
        stackRows // iterate over stackRows
            .mapNotNull { it.getOrNull(index) } // lookup index for each stack row
            .filter { it.isUpperCase() } // grab anything with uppercase, i.e. the crate
            .toMutableList() // convert to mutable list
    }
}

data class Instruction(val amount: Int, val source: Int, val target: Int)

private fun parseInstructions(input: List<String>): List<Instruction> =
    input
        .dropWhile { !it.startsWith("move") } // skip until you get to the instructions
        .map { row ->
            row.split(" ").let { parts -> // split each row and use "let" to pass as argument into next block
                Instruction(parts[1].toInt(), parts[3].toInt() - 1, parts[5].toInt() - 1)
            }
        }

private fun performInstructions(stacks: List<MutableList<Char>>, instructions: List<Instruction>, reverse: Boolean) {
    instructions.forEach { (amount, source, destination) -> // unpack each Instruction
        // take the first n=amount elements from the source stack
        val toBeMoved = stacks[source].take(amount)

        // remove the first element from the source stack n=amount times
        repeat(amount) { stacks[source].removeFirst() }

        // add all the elements from toBeMoved in reverse order to the destination stack
        stacks[destination].addAll(0, if (reverse) toBeMoved.reversed() else toBeMoved)
    }
}

private fun part1(input: List<String>): String {
    var stacks: List<MutableList<Char>> = createStacks(input)
    var instructions: List<Instruction> = parseInstructions(input)
    performInstructions(stacks, instructions, true)
    return stacks.tops()
}

private fun part2(input: List<String>): String {
    var stacks: List<MutableList<Char>> = createStacks(input)
    var instructions: List<Instruction> = parseInstructions(input)
    performInstructions(stacks, instructions, false)
    return stacks.tops()
}

fun main() {
    val testInput = readLines("day05/test")
    println(part1(testInput))
    println(part2(testInput))

    val input = readLines("day05/input")
    println(part1(input))
    println(part2(input))
}