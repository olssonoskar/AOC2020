package day03

import java.lang.IllegalArgumentException

fun main() {
    val input = InputUtils.getLines("day03.txt")
    val day3 = Day3()
    println(day3.part1(input))
    println(day3.part2(input))
}

class Day3 {

    fun part1(input: List<String>, speedX: Int = 3, speedY: Int = 1) : Long {
        var treesInPath = 0
        var x = 0
        for (index in speedY until input.size step speedY) {
            val row = input[index]
            x = (x + speedX) % row.length
            if ('#' == row[x]) {
                treesInPath++
            }
        }
        return treesInPath.toLong()
    }

    fun part2(input: List<String>) : Long {
        return listOf(1 to 1, 3 to 1, 5 to 1, 7 to 1, 1 to 2).stream()
            .map { xAndY -> part1(input, xAndY.first, xAndY.second) }
            .reduce { first, second -> first * second }
            .orElseThrow { IllegalArgumentException("No solution") }
    }

}