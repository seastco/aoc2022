package day15

import Point2D
import readLines
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

private class Sensor(val location: Point2D, val closestBeacon: Point2D) {
    var distance: Int = location.distanceTo(closestBeacon)
}

private fun parseInput(input: List<String>): Set<Sensor> {
    val pattern = "x=(-?\\d+), y=(-?\\d+)".toRegex()
    val set = mutableSetOf<Sensor>()

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

        set.add(Sensor(sensor, beacon))
    }

    return set
}

private fun part1(input: List<String>): Int {
    val set = parseInput(input)

    var minX = Integer.MAX_VALUE
    var maxX = Integer.MIN_VALUE
    var maxDistance = Integer.MIN_VALUE

    set.forEach {
        val sensor = it.location
        minX = min(minX, sensor.x)
        maxX = max(maxX, sensor.x)
        maxDistance = max(maxDistance, it.distance)
    }

    var count = 0

    for (i in minX - maxDistance..maxX + maxDistance) {
        for (sensor in set) {
            // Exclude any sensor or beacon found on the 2,000,000th row
            if (i == sensor.location.x && sensor.location.y == 2_000_000 ||
                i == sensor.closestBeacon.x && sensor.closestBeacon.y == 2_000_000) {
                break
            }

            // If the distance between sensor and coordinate is less than the sensor's manhattan
            // distance, a beacon cannot be at this coordinate
            if (abs(i - sensor.location.x) + abs(2_000_000 - sensor.location.y) <= sensor.distance) {
                count += 1
                break
            }
        }
    }

    return count
}

private fun part2(input: List<String>): Long {
    val sensorSet = parseInput(input)

    // Given that there's only ONE possible solution, the distress beacon is guaranteed
    // to be +1 away from a perimeter. So we add +1 to each distance, which will help us
    // calculate all possible coordinates for the distress beacon.
    sensorSet.map {
        it.distance += 1
    }

    // The problem statement specifies that the distress beacon is within this range.
    val distressBeaconRange = 0..4_000_000

    return sensorSet.firstNotNullOf { sensor ->
        (-sensor.distance..sensor.distance).firstNotNullOfOrNull { delta ->
            listOf(
                // Imagine traversing a diamond perimeter from left to right. To do so, we move across the
                // x-axis from the leftmost point to the rightmost point while grabbing the corresponding +/- y axis.
                Point2D(sensor.location.x + delta, (sensor.location.y - sensor.distance) - delta),
                Point2D(sensor.location.x + delta, (sensor.location.y + sensor.distance) + delta)
            ).filter {
                // Filter out anything that isn't in the distress beacon range
                it.x in distressBeaconRange && it.y in distressBeaconRange
            }.firstOrNull { point ->
                // We found the distress beacon IF its abs distance to each sensor is >= the manhattan distance + 1
                // (remember we added + 1 to each distance so we could traverse the perimeter)
                sensorSet.all { sensor ->
                    abs(sensor.location.x - point.x) + abs(sensor.location.y - point.y) >= sensor.distance
                }
            }
        }?.let {
            // Calculate the tuning frequency
            it.x.toLong() * 4_000_000 + it.y
        }
    }
}

fun main() {
    //println(part1(readLines("day15/test")))
    //println(part1(readLines("day15/test")))
    println(part1(readLines("day15/input")))
    println(part2(readLines("day15/input")))
}