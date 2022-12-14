package day14

import readLines
import kotlin.math.max
import kotlin.math.min

private class Grid(var grid: Array<Array<String>>, val minX: Int, val maxX: Int, val minY: Int, val maxY: Int) {

    companion object {
        fun of(input: List<String>, part2: Boolean): Grid {
            val rockPaths = input.map { line ->
                line.split("->").map { pair ->
                    pair.trim()
                    val x = pair.substringBefore(",").trim().toInt()
                    val y = pair.substringAfter(",").trim().toInt()
                    Pair(x, y)
                }
            }.map {
                it.windowed(2, 1)
            }

            var minX = Integer.MAX_VALUE
            var maxX = Integer.MIN_VALUE
            var minY = 0
            var maxY = Integer.MIN_VALUE
            var grid: Array<Array<String>>?

            rockPaths.forEach { path ->
                path.forEach { windowedPair ->
                    windowedPair.forEach { pair ->
                        minX = min(minX, pair.first)
                        maxX = max(maxX, pair.first)
                        maxY = max(maxY, pair.second)
                    }
                }
            }

            grid = Array(maxY + 1) { Array(maxX - minX + 1) { "." } }
            grid[0][500 - minX] = "+"

            if (part2) {
                grid = Array(maxY + 2) { Array(maxX - minX + 1000) { "." } } // 1000 is our "infinite" X axis
                grid[0][500 - minX] = "."
            }

            rockPaths.forEach { path ->
                path.forEach { windowedPair ->
                    for (col in rangeBetween(windowedPair.first().first, windowedPair.last().first)) {
                        for (row in rangeBetween(windowedPair.first().second, windowedPair.last().second)) {
                            val i = row - (if (part2) 0 else minY)
                            val j = col - (if (part2) 0 else minX)
                            grid[i][j] = "#"
                        }
                    }
                }
            }

            return Grid(grid, minX, maxX, minY, maxY)
        }

        private fun rangeBetween(a: Int, b: Int) = min(a, b)..max(a, b)
    }
}

private fun dropSand(grid: Array<Array<String>>, x: Int, y: Int, terminateOutOfBounds: Boolean): Pair<Boolean, Int> {
    if (y >= grid.size || y < 0 || x >= grid[0].size || x < 0) {
        return Pair(!terminateOutOfBounds, 0)
    }

    if (grid[y][x] != ".") {
        return Pair(true, 0)
    }

    val down = dropSand(grid, x, y + 1, terminateOutOfBounds)
    if (!down.first) {
        return Pair(false, down.second)
    }

    val left = dropSand(grid, x - 1, y + 1, terminateOutOfBounds)
    if (!left.first) {
        return Pair(false, down.second + left.second)
    }

    val right = dropSand(grid, x + 1, y + 1, terminateOutOfBounds)
    if (!right.first) {
        return Pair(false, down.second + left.second + right.second)
    }

    grid[y][x] = "o"
    return Pair(true, down.second + left.second + right.second + 1)
}

private fun part1(input: List<String>): Int {
    val grid = Grid.of(input, false)
    val result = dropSand(grid.grid, 500 - grid.minX, 1, true)
    return result.second
}

private fun part2(input: List<String>): Int {
    val grid = Grid.of(input, true)
    val result = dropSand(grid.grid, 500, 0, false)
    return result.second
}

fun main() {
    println(part1(readLines("day14/test")))
    println(part1(readLines("day14/input")))
    println(part2(readLines("day14/test")))
    println(part2(readLines("day14/input")))
}