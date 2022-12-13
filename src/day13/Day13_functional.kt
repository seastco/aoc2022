package day13

import readLines

/**
 * Credit goes to tginsberg (https://github.com/tginsberg/advent-2022-kotlin)
 * I'm experimenting with his solutions to better learn functional programming in Kotlin.
 * Files without the _functional suffix are my original solutions.
 */

sealed class PacketV2 : Comparable<PacketV2> {
    companion object {
        fun of(input: String): PacketV2 =
            of(
                input.split("""((?<=[\[\],])|(?=[\[\],]))""".toRegex()) // split the string on any of "[" or "]" or "," but keep them in the output
                    .filter { it.isNotBlank() } // filter out ""
                    .filter { it != "," } // filter out ","
                    .iterator() // "[1,1,3,1,1]" is now ["[", "1", "1", "3", "1", "1", "]"]
            )

        private fun of(input: Iterator<String>): PacketV2 {
            val packets = mutableListOf<PacketV2>()
            while (input.hasNext()) {
                when (val symbol = input.next()) {
                    "]" -> return ListPacket(packets)
                    "[" -> packets.add(of(input))
                    else -> packets.add(IntPacket(symbol.toInt()))
                }
            }
            return ListPacket(packets)
        }
    }
}

private class IntPacket(val amount: Int) : PacketV2() {
    fun asList(): PacketV2 = ListPacket(listOf(this))

    override fun compareTo(other: PacketV2): Int =
        when (other) {
            is IntPacket -> amount.compareTo(other.amount)
            is ListPacket -> asList().compareTo(other)
        }
}

private class ListPacket(val subPackets: List<PacketV2>) : PacketV2() {
    override fun compareTo(other: PacketV2): Int =
        when (other) {
            is IntPacket -> compareTo(other.asList())
            is ListPacket -> subPackets.zip(other.subPackets) // zip will pair together what it can, if list length isn't even those elements are left out
                .map { it.first.compareTo(it.second) } // compare values within pairs
                .firstOrNull { it != 0 } ?: subPackets.size.compareTo(other.subPackets.size) // if condition isn't found, compare list length
        }
}

private fun part1(input: List<String>): Int {
    val packets = input.filter { it.isNotBlank() }.map { PacketV2.of(it) }
    return packets.chunked(2).mapIndexed { index, pair -> // compare two packets at a time
        if (pair.first() < pair.last()) index + 1 else 0 // if first < second, add index+1 to sum result
    }.sum()
}

private fun part2(input: List<String>): Int {
    val packets = input.filter { it.isNotBlank() }.map { PacketV2.of(it) }
    val dividerPacket1 = PacketV2.of("[[2]]")
    val dividerPacket2 = PacketV2.of("[[6]]")
    val ordered = (packets + dividerPacket1 + dividerPacket2).sorted()
    return (ordered.indexOf(dividerPacket1) + 1) * (ordered.indexOf(dividerPacket2) + 1)
}

fun main() {
    println(part1(readLines("day13/test")))
    println(part2(readLines("day13/test")))
    println(part1(readLines("day13/input")))
    println(part2(readLines("day13/input")))
}