package day06

import readInput

fun main() {
    fun find_marker(input: String, distinctCharCount: Int): Int {
        var charSet = HashSet<Char>()
        var count = 0
        var left = 0

        for ((index, char) in input.withIndex()) {
            if (charSet.contains(char)) {
                while (input[left] != char) {
                    charSet.remove(input[left])
                    left += 1
                    count -= 1
                }
                charSet.remove(input[left])
                left += 1
                count -= 1
            }

            charSet.add(char)
            count += 1

            if (count == distinctCharCount) {
                return index + 1
            }
        }

        return -1
    }

    fun find_marker_enhanced(input: String, distinctCharCount: Int): Int {
        val marker = input.toCharArray().toList()
            .windowed(4, 1, false)
            .first { window -> window.toSet().size == 4 }
            .joinToString("");

        return input.indexOf(marker) + distinctCharCount
    }

    println(find_marker(readInput("day06/test")[0], 4))
    println(find_marker(readInput("day06/input")[0], 4))
    println(find_marker(readInput("day06/test")[0], 14))
    println(find_marker(readInput("day06/input")[0], 14))
}
