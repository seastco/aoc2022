package day05

import readText
import java.util.*

fun main() {
    fun getCrateStacks(input: List<String>): MutableList<Stack<Char>> {
        var crates = input[0].split("\n")
        val numStacks = crates.last().last()
        val stacks = MutableList<Stack<Char>>(numStacks.digitToInt()) { Stack() }
        crates = crates.dropLast(1).reversed()

        for (row in crates) {
            // skip over any whitespace
            var i = row.indexOfFirst { !it.isWhitespace() }

            while (i < row.length) {
                // hop 4 indices at a time to check for a value
                while (row[i].isWhitespace()) {
                    i += 4
                }

                val crate = row[i + 1]
                stacks[i/4].push(crate)
                i += 4
            }
        }

        return stacks
    }

    fun part1(input: List<String>): String {
        var stacks = getCrateStacks(input)
        var instructions = input[1].split("\n")

        for (instruction in instructions) {
            val splitInstruction = instruction.split(" ")
            var amount = splitInstruction[1].toInt()
            val stack1 = splitInstruction[3].toInt() - 1 // translate to index
            val stack2 = splitInstruction[5].toInt() - 1 // translate to index

            while (amount > 0) {
                var crate = stacks[stack1].pop()
                stacks[stack2].push(crate)
                amount -= 1
            }
        }

        var answer = ""
        for (stack in stacks) {
            answer += stack.peek()
        }

        return answer
    }

    fun part2(input: List<String>): String {
        var stacks = getCrateStacks(input)
        var instructions = input[1].split("\n")

        for (instruction in instructions) {
            val splitInstruction = instruction.split(" ")
            var amount = splitInstruction[1].toInt()
            val stack1 = splitInstruction[3].toInt() - 1 // translate to index
            val stack2 = splitInstruction[5].toInt() - 1 // translate to index
            var tempStack = Stack<Char>()

            while (amount > 0) {
                var crate = stacks[stack1].pop()
                tempStack.push(crate)
                amount -= 1
            }

            while (tempStack.isNotEmpty()) {
                stacks[stack2].push(tempStack.pop())
            }
        }

        var answer = ""
        for (stack in stacks) {
            answer += stack.peek()
        }

        return answer
    }

    var testInput = readText("day05/test").split("\n\n")
    println(part1(testInput))
    println(part2(testInput))

    var input = readText("day05/input").split("\n\n")
    println(part1(input))
    println(part2(input))
}
