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

private fun part2(input: List<String>): Int {
    val map = parseInput(input) // Map<Sensor, Beacon>
    val intervalPairs = mutableSetOf<Pair<IntRange, IntRange>>()

    // Create set of interval pairs
    for ((sensor, beacon) in map.entries) {
        val distance = abs(sensor.x - beacon.x) + abs(sensor.y - beacon.y)

        // Create xInterval (Sensor.x - distance, Sensor.y + distance)
        // Adjust interval if outside of 0 or 4_000_000
        var xInterval = sensor.x - distance..sensor.x + distance
        if (xInterval.first < 0) xInterval = 0..xInterval.last
        if (xInterval.last > 4_000_000) xInterval = xInterval.first..4_000_000

        // Create yInterval (Sensor.y - distance, Sensor.y + distance)
        // Adjust interval if outside of 0 or 4_000_000
        var yInterval = sensor.y - distance..sensor.y + distance
        if (yInterval.first < 0) yInterval = 0..yInterval.last
        if (yInterval.last > 4_000_000) yInterval = yInterval.first..4_000_000

        intervalPairs.add(Pair(xInterval, yInterval))
    }

    // For each interval...
    for (pair in intervalPairs) {
        var xInterval = pair.first
        var yInterval = pair.second

        for (x in xInterval) {
            // traverse the top perimeter with x, yInterval.last + 1
            var found = checkBorder(x, yInterval.last + 1, map)
            if (found) {
                return (x * 4_000_000) + (yInterval.last + 1)
            }

            // traverse the bottom perimeter with x, yInterval.first - 1
            found = checkBorder(x, yInterval.first - 1, map)
            if (found) {
                return (x * 4_000_000) + (yInterval.first - 1)
            }
        }

        for (y in yInterval) {
            // traverse the left perimeter with xInterval.first - 1, y
            var found = checkBorder(xInterval.first - 1, y, map)
            if (found) {
                return (xInterval.first - 1) * (4_000_000 + y)
            }

            // traverse the right perimeter with xInterval.last + 1, y
            found = checkBorder(xInterval.last + 1, y, map)
            if (found) {
                return (xInterval.last + 1) * (4_000_000 + y)
            }
        }

        // check corners?
        var found = checkBorder(xInterval.first-1, yInterval.first-1, map)
        if (found) {
            println("OH SHIT")
        }
        found = checkBorder(xInterval.last+1, yInterval.first-1, map)
        if (found) {
            println("OH SHIT")
        }
        found = checkBorder(xInterval.first-1, yInterval.last+1, map)
        if (found) {
            println("OH SHIT")
        }
        found = checkBorder(xInterval.first+1, yInterval.last+1, map)
        if (found) {
            println("OH SHIT")
        }
    }

    return -1
}

private fun checkBorder(x: Int, y: Int, map: Map<Point2D, Point2D>): Boolean {
    // Calculate distance for between point and sensor, and compare with distance between sensor and beacon.
    // If former is less than the latter, we fall within an existing perimeter.
    for ((sensor, beacon) in map.entries) {
        val sensorToBeaconDistance = abs(sensor.x - beacon.x) + abs(sensor.y - beacon.y)
        val pointToSensorDistance = abs(x - sensor.x) + abs(y - sensor.y)

        if (pointToSensorDistance <= sensorToBeaconDistance) {
            return false
        }
    }

    return true
}

fun main() {
    println(part1(readLines("day15/input")))
    println(part2(readLines("day15/input")))
}