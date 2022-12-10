package day08

import readLines

fun traverseLeft(forest: List<List<Int>>, i: Int, j: Int): Int {
    var count = 0

    for (curr in j-1 downTo 0) {
        count += 1

        if (forest[i][j] <= forest[i][curr]) {
            break
        }
    }

    return count
}

fun traverseRight(forest: List<List<Int>>, i: Int, j: Int): Int {
    var count = 0

    for (curr in j+1 until forest[i].size) {
        count += 1

        if (forest[i][j] <= forest[i][curr]) {
            break
        }
    }

    return count
}

fun traverseUp(forest: List<List<Int>>, i: Int, j: Int): Int {
    var count = 0

    for (curr in i-1 downTo 0) {
        count += 1

        if (forest[i][j] <= forest[curr][j]) {
            break
        }
    }

    return count
}

fun traverseDown(forest: List<List<Int>>, i: Int, j: Int): Int {
    var count = 0

    for (curr in i+1 until forest.size) {
        count += 1

        if (forest[i][j] <= forest[curr][j]) {
            break
        }
    }

    return count
}

private fun part2(forest: List<List<Int>>): Int {
    var max = 0

    for (i in forest.indices) {
        for (j in forest[i].indices) {
            val r = traverseRight(forest, i, j)
            val l = traverseLeft(forest, i, j)
            val u = traverseUp(forest, i, j)
            val d = traverseDown(forest, i, j)

            max = max.coerceAtLeast(l * r * u * d)
        }
    }

    return max
}

fun main() {
    val input = readLines("day08/input")
    val forest = input.map { it.toCharArray().map { char -> char.digitToInt() }}
    println(part2(forest))
}
