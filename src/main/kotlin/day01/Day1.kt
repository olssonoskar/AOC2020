package day01

fun main() {
    val day1 = Day1()
    println(day1.part1())
    println(day1.part2())
}

class Day1 {

    fun part1() : Int {
        val input = InputUtils.getLines("day01.txt").map { it.toInt() }
        for (first in input.indices) {
            for (second in (first + 1) until input.size) {
                if (input[first] + input[second] == 2020) {
                    return input[first] * input[second]
                }
            }
        }
        return -1
    }

    fun part2() : Int {
        val input = InputUtils.getLines("day01.txt").map { it.toInt() }
        for (first in input.indices) {
            for (second in first until input.size) {
                for (third in second until input.size) {
                    if (input[first] + input[second] + input[third] == 2020) {
                        return input[first] * input[second] * input[third]
                    }
                }
            }
        }
        return -1
    }

}