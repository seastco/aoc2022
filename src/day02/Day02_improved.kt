package day02

import day02.Outcome.*
import day02.Gesture.*
import readLines

enum class Gesture(val points: Int) {
    ROCK(1),
    PAPER(2),
    SCISSORS(3)
}

fun Gesture.beats(): Gesture {
    return when (this) {
        ROCK -> SCISSORS
        PAPER -> ROCK
        SCISSORS -> PAPER
    }
}

fun Gesture.beatenBy(): Gesture {
    return when (this) {
        ROCK -> PAPER
        PAPER -> SCISSORS
        SCISSORS -> ROCK
    }
}

fun Char.toGesture(): Gesture {
    return when (this) {
        'A', 'X' -> ROCK
        'B', 'Y' -> PAPER
        'C', 'Z' -> SCISSORS
        else -> error ("Unknown input $this")
    }
}

enum class Outcome(val points: Int) {
    LOSS(0),
    DRAW(3),
    WIN(6)
}

fun Char.toOutcome(): Outcome {
    return when (this) {
        'X' -> LOSS
        'Y' -> DRAW
        'Z' -> WIN
        else -> error ("Unknown input $this")
    }
}

fun gestureForDesiredOutcome(opponentGesture: Gesture, desiredOutcome: Outcome): Gesture {
    return when (desiredOutcome) {
        DRAW -> opponentGesture
        LOSS -> opponentGesture.beats()
        WIN -> opponentGesture.beatenBy()
    }
}

fun calculateOutcome(opponentGesture: Gesture, yourGesture: Gesture): Outcome = when {
    opponentGesture == yourGesture -> DRAW
    yourGesture.beats() == opponentGesture -> WIN
    else -> LOSS
}

fun calculateScore(opponentGesture: Gesture, yourGesture: Gesture): Int {
    val outcome = calculateOutcome(opponentGesture, yourGesture)
    return yourGesture.points + outcome.points
}

fun getInput(file: String): List<Pair<Char, Char>> {
    return readLines("day02/$file").map {
        val (a, b) = it.split(" ")
        a.first() to b.first()
    }
}

fun part1(file: String): Int {
    return getInput(file).sumOf { (opponent, you) ->
        calculateScore(opponent.toGesture(), you.toGesture())
    }
}

fun part2(file: String): Int {
    return getInput(file).sumOf { (opponent, you) ->
        val yourGesture = gestureForDesiredOutcome(opponent.toGesture(), you.toOutcome())
        calculateScore(opponent.toGesture(), yourGesture)
    }
}

fun main() {
    println(part1("test"))
    println(part1("input"))
    println(part2("test"))
    println(part2("input"))
}
