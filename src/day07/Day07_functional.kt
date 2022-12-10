package day07

import readLines

/**
 * Credit goes to tginsberg; experimenting with his solutions to better learn functional programming in Kotlin.
 * Files without the _functional suffix are my original solutions.
 */

class Directory(val name: String) {
    private val subDirs: MutableMap<String, Directory> = mutableMapOf()
    private var sizeOfFiles: Int = 0

    val size: Int
        get() = sizeOfFiles + subDirs.values.sumOf { it.size }

    fun addFile(size: Int) {
        sizeOfFiles += size
    }

    fun traverse(dir: String): Directory =
        subDirs.getOrPut(dir) { Directory(dir) }

    fun find(predicate: (Directory) -> Boolean): List<Directory> =
        subDirs.values.filter(predicate) + // filter any value with size <= 100k
                subDirs.values.flatMap { it.find(predicate) } // call find(predicate) on subDirs
}

private fun parseInput(input: List<String>): Directory {
    val callStack = ArrayDeque<Directory>().apply { add(Directory("/")) }

    input.forEach { item ->
        when {
            item == "$ ls" -> {} // no-op
            item.startsWith("dir") -> {} // no-op
            item == "$ cd /" -> callStack.removeIf { it.name != "/" }
            item == "$ cd .." -> callStack.removeFirst()
            item.startsWith("$ cd") -> {
                val name = item.substringAfterLast(" ") // parse out directory name
                callStack.addFirst(callStack.first().traverse(name)) // add directory to subdirectory of parent
            }
            else -> {
                val size = item.substringBefore(" ").toInt()
                callStack.first().addFile(size) // add file size to current directory
            }
        }
    }

    return callStack.last() // return root directory
}

fun part1(input: List<String>): Int {
    val rootDirectory = parseInput(input)
    return rootDirectory.find { it.size <= 100_000 }.sumOf{ it.size }
}

fun part2(input: List<String>): Int {
    val rootDirectory = parseInput(input)
    val unusedSpace = 70_000_000 - rootDirectory.size
    val deficit = 30_000_000 - unusedSpace
    return rootDirectory.find { it.size >= deficit }.minBy { it.size }.size
}

fun main() {
    println(part1(readLines("day07/test")))
    println(part1(readLines("day07/input")))
    println(part2(readLines("day07/test")))
    println(part2(readLines("day07/input")))
}