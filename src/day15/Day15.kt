package day15

import Point2D
import readLines
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

private fun parseInput(input: List<String>): Map<Point2D, Point2D> {
    val pattern = "x=(-?\\d+), y=(-?\\d+)".toRegex()
    val map = mutableMapOf<Point2D, Point2D>()

    for (line in input) {
        val matches = pattern.findAll(line).iterator()

        // Sensor
        val first = matches.next()
        val x1 = first.groupValues[1].toInt()
        val y1 = first.groupValues[2].toInt()
        val sensor = Point2D(x1, y1)

        // Beacon
        val second = matches.next()
        val x2 = second.groupValues[1].toInt()
        val y2 = second.groupValues[2].toInt()
        val beacon = Point2D(x2, y2)

        map[sensor] = beacon
    }

    return map
}

private fun part1(input: List<String>): Int {
    val map = parseInput(input)

    var minX = Integer.MAX_VALUE
    var maxX = Integer.MIN_VALUE
    var maxDistance = Integer.MIN_VALUE

    map.forEach {
        val sensor = it.key
        val beacon = it.value
        minX = min(minX, sensor.x)
        maxX = max(maxX, beacon.x)
        maxDistance = max(maxDistance, abs(sensor.x - beacon.x) + abs(sensor.x - beacon.y))
    }

    var count = 0

    for (i in minX - maxDistance..maxX + maxDistance) {
        for ((sensor, beacon) in map.entries) {
            val distance = abs(sensor.x - beacon.x) + abs(sensor.y - beacon.y)

            if (i == sensor.x && sensor.y == 2_000_000 || i == beacon.x && beacon.y == 2_000_000) {
                break
            }

            if (abs(i - sensor.x) + abs(2_000_000 - sensor.y) <= distance) {
                count += 1
                break
            }
        }
    }

    return count
}

private fun part2(input: List<String>): Long {
    val sensorToBeaconMap = parseInput(input)
    val sensorToBeaconDistance = mutableMapOf<Point2D, Int>()

    // Create new map of (Sensor, ManhattanDistance + 1). If we imagine a diamond perimeter
    // around each sensor, the distress beacon will be +1 away from one of the perimeters.
    for ((sensor, beacon) in sensorToBeaconMap.entries) {
        val distance = abs(sensor.x - beacon.x) + abs(sensor.y - beacon.y)
        sensorToBeaconDistance[sensor] = distance + 1
    }

    // Valid range for the distress beacon to be
    val distressBeaconRange = 0..4_000_000

    return sensorToBeaconDistance.firstNotNullOf {(sensor, distance) ->
        (-distance..distance).firstNotNullOfOrNull { delta ->
            listOf(
                Point2D(sensor.x + delta, sensor.y + (distance - delta)),
                Point2D(sensor.x + delta, sensor.y + (distance + delta))
            ).filter {
                it.x in distressBeaconRange && it.y in distressBeaconRange
            }.firstOrNull { point ->
                sensorToBeaconDistance.all { (sensor, distance) ->
                    abs(sensor.x - point.x) + abs(sensor.y - point.y) >= distance - 1
                }
            }
        }?.let { it.x.toLong() * 4_000_000 + it.y }
    }
}

fun main() {
    println(part1(readLines("day15/input")))
    println(part2(readLines("day15/input")))
}