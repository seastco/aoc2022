package day16

import com.github.shiguruikai.combinatoricskt.combinations
import com.github.shiguruikai.combinatoricskt.permutations
import readLines

private data class ValveRoom(val name: String, val flowRate: Int, val paths: List<String>) {
    companion object {
        fun of(input: String): ValveRoom =
            // e.g. Valve AV has flow rate=0; tunnels lead to valves AX, PI
            ValveRoom(
                input.substringAfter(" ").substringBefore(" "), // AV
                input.substringAfter("=").substringBefore(";").toInt(), // 0
                input.substringAfter("valve").substringAfter(" ").split(", ") // ["AX", "PI"]
            )
    }
}

private operator fun Map<String, MutableMap<String, Int>>.set(key1: String, key2: String, value: Int) {
    getValue(key1)[key2] = value
}

private operator fun Map<String, Map<String, Int>>.get(key1: String, key2: String, defaultValue: Int = 100000): Int =
    get(key1)?.get(key2) ?: defaultValue

// Find the cost of moving from one room to any other room, but only for rooms that have a flow rate > 0 and are
// reachable by AA
private fun calculateShortestPaths(rooms: Map<String, ValveRoom>): Map<String, Map<String, Int>> {

    // Create a Map<String, Map<String, Int>> where the key to the first map is the name of a room, and the value is
    // another map whose key is a destination room, and its value is the cost to move to that room and turn on the valve.
    // e.g. {"AA": {"DD": 1, "II": 1", "BB": 1}}
    var shortestPaths = mutableMapOf<String, MutableMap<String, Int>>()
    for (room in rooms.values) {
        val paths = room.paths.associateWith { 1 }.toMutableMap()
        shortestPaths[room.name] = paths
    }

    // A simple way to get the shortest path from one room to any other room is to generate permutations of all room
    // names in groups of 3. Destructure these into "waypoint", "to", and "from". If we can go from "to" -> "waypoint" and
    // from "waypoint" to "from" cheaper than our previous answer of going directly from "to" to "from", take and store
    // that in the shortestPath map.
    shortestPaths.keys.permutations(3).forEach { (waypoint, from, to) ->

        shortestPaths[from, to] = minOf(
            shortestPaths[from, to], // existing path
            shortestPaths[from, waypoint] + shortestPaths[waypoint, to] // new path
        )
    }

    // Find all the zero flow rooms and remove them; we don't care about these as they will never be a final destination
    val zeroFlowRooms = rooms.values.filter { it.flowRate == 0}.map { it.name }.toSet()
    shortestPaths.values.forEach { it.keys.removeAll(zeroFlowRooms) }

    // Filter out anything that isn't AA itself, or accessible from AA
    val accessibleFromAA: Set<String> = shortestPaths.getValue("AA").keys
    return shortestPaths.filter { it.key in accessibleFromAA || it.key == "AA" }
}

private fun searchPaths(rooms: Map<String, ValveRoom>,
                        paths: Map<String, Map<String, Int>>,
                        room: String,
                        timeAllowed: Int,
                        seen: Set<String> = emptySet(),
                        timeTaken: Int = 0,
                        totalFlow: Int = 0): Int {

    var possibleNextRooms = paths.getValue(room)
        // find a room we haven't already been to
        .filterNot { (nextRoom, _) -> nextRoom in seen }
        // make sure we can get to the room in time (< and not <= because if we get there at exactly 30 the flow rate is irrelevant)
        .filter { (_, traversalCost) -> timeTaken + traversalCost + 1 < timeAllowed }

    var totalFlow = possibleNextRooms.maxOfOrNull { (nextRoom, traversalCost) -> // recursively call searchPath from each next room
            searchPaths(
                rooms,
                paths,
                nextRoom,
                timeAllowed,
                seen + nextRoom, // add this room to seen set
                timeTaken + traversalCost + 1, // increment time taken by how long it will take to get there + 1 for opening the valve
                totalFlow + ((timeAllowed - timeTaken - traversalCost - 1) * rooms.getValue(nextRoom).flowRate) // calculate flow given remaining time and flow rate of next room
            )
        } ?: totalFlow // eventually we'll exhaust all entries in the map and return the total flow

    return totalFlow
}

private fun part1(input: List<String>): Int {
    val rooms = input.map { ValveRoom.of(it) }.associateBy { it.name }
    val paths: Map<String, Map<String, Int>> = calculateShortestPaths(rooms)
    return searchPaths(rooms, paths, "AA", 30)
}

private fun part2(input: List<String>): Int {
    val rooms = input.map { ValveRoom.of(it) }.associateBy { it.name }
    val paths: Map<String, Map<String, Int>> = calculateShortestPaths(rooms)

    return paths.keys
        .combinations(paths.size / 2)
        .map { it.toSet() }
        .maxOf { halfOfTheRooms ->
            searchPaths(rooms, paths, "AA", 26, halfOfTheRooms) +
            searchPaths(rooms, paths,"AA", 26, paths.keys - halfOfTheRooms)
        }

}
fun main() {
    println(part1(readLines("day16/test")))
    println(part1(readLines("day16/input")))
    println(part2(readLines("day16/test")))
    println(part2(readLines("day16/input")))
}