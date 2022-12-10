package day08

import readLines


/**
 * Credit goes to tginsberg; experimenting with his solutions to better learn functional programming in Kotlin.
 * Files without the _functional suffix are my original solutions.
 */

private val input: List<String> = readLines("day08/input")
private val forest: Array<IntArray> = input.map { row -> row.map { it.digitToInt() }.toIntArray() }.toTypedArray()
private val rows: Int = forest.size
private val cols: Int = forest.first().size

private fun Array<IntArray>.isVisible(row: Int, col: Int): Boolean =
    viewFrom(row, col) // get list in each direction
        .any { direction -> // check for any direction...
            direction.all { it < this[row][col] } // are all values in list less than current position?
        }

private fun Array<IntArray>.viewFrom(row: Int, col: Int): List<List<Int>> =
    listOf(
        (row - 1 downTo 0).map { this[it][col] }, // Up
        (row + 1 until rows).map { this[it][col] }, // Down
        this[row].take(col).asReversed(), // Left
        this[row].drop(col + 1) // Right
    )

inline fun <T> Iterable<T>.takeUntil(predicate: (T) -> Boolean): List<T> {
    val list = ArrayList<T>()
    for (item in this) {
        list.add(item)
        if (predicate(item)) {
            break
        }
    }
    return list
}

fun Iterable<Int>.product(): Int = reduce { a, b -> a * b }

private fun Array<IntArray>.scoreAt(row: Int, col: Int): Int =
    viewFrom(row, col) // get list in each direction
        .map { direction -> direction // for each list/direction call takeUntil you find a bigger tree
            .takeUntil { it >= this[row][col] }.count() // count the result of takeUntil for direction
        }.product() // calculate the product of the results from the 4 directions

fun part1(): Int {
    var visibleCount =
        (1 until rows - 1).sumOf { r -> // for each row...
            (1 until cols - 1).count { c -> // for each col...
                forest.isVisible(r, c) // is edge visible from here?
            }
        }

    val perimeterCount = (2 * rows) + (2 * cols) - 4 // we skipped the perimeter from above, so calculate that here
    visibleCount += perimeterCount
    return visibleCount
}

fun part2(): Int {
    var scenicScore =
        (1 until rows - 1).maxOf { r -> // for each row...
            (1 until cols - 1).maxOf { c -> // for each col...
                forest.scoreAt(r, c) // calculate the scenic score. maxOf will return the max.
        }
    }

    return scenicScore
}

fun main() {
    println(part1())
    println(part2())
}
