package day13

import readText
import org.json.JSONArray
import readLines
import kotlin.math.min

class Packet(val packet: JSONArray) : Comparable<Packet> {

    override fun compareTo(other: Packet): Int {
        return compare(this.packet, other.packet)
    }

    /*
    The comparison algorithm is based on the following logic:
    - If both values are integers, the lower integer should come first. Meaning if b > a, input is in the right order.
    - If both values are lists, compare the values within the lists. If either list is exhausted, the input is in the
      right order if b.length() > a.length().
    - If exactly one value is an integer, convert the integer to a JSONArray and retry the comparison.
     */
    private fun compare(a: Any, b: Any): Int {
        if (a is Int && b is Int) {
            return b.compareTo(a)

        } else if (a is JSONArray && b is JSONArray) {
            (0 until min(a.length(), b.length())).forEach {
                val result = compare(a[it], b[it])
                if (result != 0) {
                    return result
                }
            }
            return b.length().compareTo(a.length())

        } else {
            if (a is Int) {
                return compare(JSONArray("[$a]"), b)
            } else {
                return compare(a, JSONArray("[$b]"))
            }
        }
    }
}

fun part1(input: String): Int {
    val pairs = input
        .split("\n\n")
        .map { it.split("\n").map { packet -> Packet(JSONArray(packet)) } }.toMutableList()

    val sum = pairs
        .map { pair -> pair[0].compareTo(pair[1]) }
        .withIndex()
        .sumOf { if (it.value == 1) it.index+1 else 0 }

    return sum
}

fun part2(input: List<String>): Int {
    val packets = input
        .filter { it.isNotEmpty() }
        .flatMap { it.split("\n").map { packet -> Packet(JSONArray(packet)) } }
        .toMutableList()

    val dividerPacket2 = Packet(JSONArray("[[2]]"))
    val dividerPacket6 = Packet(JSONArray("[[6]]"))
    packets.add(dividerPacket2)
    packets.add(dividerPacket6)
    val sortedPackets = packets.sortedDescending()

    return (sortedPackets.indexOf(dividerPacket2) + 1) * (sortedPackets.indexOf(dividerPacket6) + 1)
}

fun main() {
    println(part1(readText("day13/test")))
    println(part2(readLines("day13/test")))
    println(part1(readText("day13/input")))
    println(part2(readLines("day13/input")))
}