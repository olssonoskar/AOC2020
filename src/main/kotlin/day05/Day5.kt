package day05

import java.lang.IllegalArgumentException
import java.util.SortedSet
import kotlin.math.roundToInt

fun main() {
    val inputs = InputUtils.getStrings("day05.txt")
    val day5 = Day5()
    println(day5.part1(inputs))
    println(day5.part2(inputs))
}

class Day5 {

    private val rows = 127
    private val seats = 7

    fun part1(inputs: List<String>): String {
        val ids = generateSeatIds(inputs)
        return ids.last().toString()
    }

    fun part2(inputs: List<String>): String {
        val ids = generateSeatIds(inputs)
        var previous = 0
        for (id in ids) {
            if (id != ids.first() && id != previous + 1) {
                return (previous + 1).toString()
            }
            previous = id
        }
        return "ID was not found"
    }

    private fun generateSeatIds(inputs: List<String>): SortedSet<Int> {
        val allIds = sortedSetOf<Int>()
        for (input in inputs) {
            val row = decodeRow(input)
            val seat = decodeSeat(input)
            allIds.add(seatId(row, seat))
        }
        return allIds
    }

    private fun decodeRow(row: String) : Int {
        var min = 0
        var max = rows
        for (i in 0..5) {
            when (row[i]) {
                'F' -> max -= diff(min, max)
                'B' -> min += diff(min, max)
                else -> throw IllegalArgumentException("Not a valid input:${row[i]}")
            }
        }
        return when (val last = (row[6])) {
            'F' -> min
            'B' -> max
            else -> throw IllegalArgumentException("Not a valid input:$last")
        }
    }

    private fun decodeSeat(row: String) : Int {
        var min = 0
        var max = seats
        for (i in 7..8) {
            when (row[i]) {
                'L' -> max -= diff(min, max)
                'R' -> min += diff(min, max)
                else -> throw IllegalArgumentException("Not a valid input:${row[i]}")
            }
        }
        return when (val last = (row[9])) {
            'L' -> min
            'R' -> max
            else -> throw IllegalArgumentException("Not a valid input:$last")
        }
    }

    private fun seatId(row: Int, seat: Int) = row * 8 + seat

    private fun diff(min: Int, max: Int) : Int {
        return ((max - min) / 2.0).roundToInt()
    }
}