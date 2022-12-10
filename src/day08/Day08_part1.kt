package day08

import readLines

data class Tree(val height: Int, var visible: Boolean)

fun traverseBorders(forest: List<List<Tree>>): Int {
    var count = 0

    for (i in forest.indices) {
        for (j in forest[i].indices) {
            if (i == 0 || j == 0 || i == forest.size - 1 || j == forest[i].size - 1) {
                forest[i][j].visible = true
                count += 1
            }
        }
    }

    return count
}

fun traverseLeftRight(forest: List<List<Tree>>): Int {
    var count = 0

    for (i in 1 until forest.size-1) {
        var max = forest[i][0].height

        for (j in 1 until forest[i].size-1) {
            if (forest[i][j].height > max) {
                max = forest[i][j].height

                if (forest[i][j].visible) {
                    continue
                } else {
                    forest[i][j].visible = true
                    count += 1
                }
            }
        }
    }

    return count
}

fun traverseRightLeft(forest: List<List<Tree>>): Int {
    var count = 0

    for (i in 1 until forest.size-1) {
        var max = forest[i][forest[i].size-1].height

        for (j in forest[i].size-2 downTo 1) {
            if (forest[i][j].height > max) {
                max = forest[i][j].height

                if (forest[i][j].visible) {
                    continue
                } else {
                    forest[i][j].visible = true
                    count += 1
                }
            }
        }
    }

    return count
}

fun traverseUpDown(forest: List<List<Tree>>): Int {
    var count = 0

    for (i in 1 until forest.size-1) {
        var max = forest[0][i].height

        for (j in 1 until forest.size-1) {
            if (forest[j][i].height > max) {
                max = forest[j][i].height

                if (forest[j][i].visible) {
                    continue
                } else {
                    forest[j][i].visible = true
                    count += 1
                }
            }
        }
    }

    return count
}

fun traverseDownUp(forest: List<List<Tree>>): Int {
    var count = 0

    for (i in 1 until forest.size-1) {
        var max = forest[forest[i].size-1][i].height

        for (j in forest[i].size-2 downTo 1) {
            if (forest[j][i].height > max) {
                max = forest[j][i].height

                if (forest[j][i].visible) {
                    continue
                } else {
                    forest[j][i].visible = true
                    count += 1
                }
            }
        }
    }

    return count
}

private fun part1(forest: List<List<Tree>>): Int {
    var count = 0

    count += traverseBorders(forest)
    count += traverseLeftRight(forest)
    count += traverseRightLeft(forest)
    count += traverseUpDown(forest)
    count += traverseDownUp(forest)

    return count
}

fun main() {
    val input = readLines("day08/input")
    val forest = input.map { it.toCharArray().map { char -> Tree(char.digitToInt(), false) } }
    println(part1(forest))
}