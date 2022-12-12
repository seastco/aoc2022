package day12

import readLines

fun List<CharArray>.findEndpoints(): Pair<Pair<Int, Int>?, Pair<Int, Int>?> {
    var start: Pair<Int, Int>? = null
    var end: Pair<Int, Int>? = null

    for (i in this.indices) {
        for (j in this[i].indices) {
            if (this[i][j] == 'E') {
                start = Pair(i, j)
            }
            if (this[i][j] == 'S') {
                end = Pair(i, j)
            }
        }
    }

    return Pair(start, end)
}

fun validMove(oldChar: Char, newChar: Char): Boolean {
    return oldChar.code - newChar.code <= 1
}

enum class Part {
    PART1,
    PART2
}

fun bfs(input: List<CharArray>, part: Part): Int {
    val nodes = mutableListOf<Pair<Int,Int>>()
    val seen = mutableSetOf<Pair<Int,Int>>()
    val (S, E) = input.findEndpoints()
    input[S!!.first][S.second] = 'z'
    input[E!!.first][E.second] = 'a'
    nodes.add(S)
    seen.add(S)

    val dirs = listOf(Pair(1,0), Pair(0,1), Pair(-1,0), Pair(0,-1))
    var pathLength = 0

    while (nodes.isNotEmpty()) {
        var count = nodes.size

        while (count > 0) {
            val (i, j) = nodes.removeFirst()

            if (part == Part.PART1 && i == E.first && j == E.second ||
                part == Part.PART2 && input[i][j] == 'a') {
                return pathLength
            }

            for ((x, y) in dirs) {
                val newI = i+y
                val newJ = j+x
                val validPosition = newI < input.size && newI >= 0 && newJ < input[i].size && newJ >= 0

                if (validPosition && !seen.contains(Pair(newI, newJ)) && validMove(input[i][j], input[newI][newJ])) {
                    nodes.add(Pair(newI, newJ))
                    seen.add(Pair(newI, newJ))
                }
            }

            count--
        }

        pathLength++
    }

    throw error ("End position not found!")
}

fun part1(input: List<CharArray>): Int {
    return bfs(input, Part.PART1)
}

fun part2(input: List<CharArray>): Int {
    return bfs(input, Part.PART2)
}

fun main() {
    println(part1(readLines("day12/test").map { it.toCharArray()}))
    println(part2(readLines("day12/test").map { it.toCharArray()}))
    println(part1(readLines("day12/input").map { it.toCharArray()}))
    println(part2(readLines("day12/input").map { it.toCharArray()}))
}
