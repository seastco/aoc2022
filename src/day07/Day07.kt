package day07

import readLines
import java.lang.Math.random
import java.util.*

fun main() {
    fun getDirectorySizes(input: List<String>): HashMap<String, Int> {
        val directories = Stack<String>()
        val directorySizes = HashMap<String, Int>()

        for (line in input) {
            val splitLine = line.split(" ")

            if (splitLine[1] == "cd") {
                if (splitLine[2] == "..") {
                    directories.pop()
                } else {
                    directories.push(splitLine[2] + "_" + random())
                }
            }
            else if (splitLine[0].toIntOrNull() != null) { // if file size
                val size = splitLine[0].toInt()

                for (directory in directories) {
                    if (directorySizes.containsKey(directory)) {
                        directorySizes[directory] = directorySizes.getValue(directory) + size
                    } else {
                        directorySizes[directory] = size
                    }
                }
            }
        }

        return directorySizes
    }

    fun part1(input: List<String>): Int {
        val directorySizes = getDirectorySizes(input)
        return directorySizes.values.filter { it <= 100000 }.sum()
    }

    fun part2(input: List<String>): Int {
        val directorySizes = getDirectorySizes(input)
        val rootDirectorySize = directorySizes.values.max()
        val unusedSpace = 70000000 - rootDirectorySize
        val neededSpace = 30000000 - unusedSpace

        return directorySizes.values.filter { it - neededSpace > 0 }.min()
    }

    println(part1(readLines("day07/test")))
    println(part1(readLines("day07/input")))
    println(part2(readLines("day07/test")))
    println(part2(readLines("day07/input")))
}