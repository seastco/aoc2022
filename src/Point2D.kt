import kotlin.math.absoluteValue
import kotlin.math.sign

data class Point2D(val x: Int = 0, val y: Int = 0) {

    companion object {
        fun of(input: String): Point2D =
            input.split(",").let { (x, y) -> Point2D(x.toInt(), y.toInt()) }
    }

    fun cardinalNeighbors(): Set<Point2D> =
        setOf(
            copy(x = x - 1),
            copy(x = x + 1),
            copy(y = y - 1),
            copy(y = y + 1)
        )

    fun lineTo(other: Point2D): List<Point2D> {
        val xDelta = (other.x - x).sign
        val yDelta = (other.y - y).sign
        val steps = maxOf((x - other.x).absoluteValue, (y - other.y).absoluteValue)
        return (1..steps).scan(this) { last, _ -> Point2D(last.x + xDelta, last.y + yDelta) }
    }

    fun distanceTo(other: Point2D): Int =
        (x - other.x).absoluteValue + (y - other.y).absoluteValue
}
