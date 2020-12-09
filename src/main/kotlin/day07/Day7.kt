package day07

import java.lang.IllegalArgumentException


fun main() {
    val input = InputUtils.getStrings("day07.txt")
    val day7 = Day7(input)
    println(day7.part1())
    println(day7.part2())
}

// Works, but should be a better way to solve this
class Day7(input: List<String>) {

    private var bags = mapBags(input)

    fun part1(): Int {
        return bagsHolding("shiny gold").size - 1
    }

    private fun bagsHolding(bag: String): Set<String> {
        return bags.values
            .filter { it.inside.containsKey(bag)}
            .map { it.color }
            .flatMap { bagsHolding(it) }
            .toSet() + bag
    }

    fun part2(): Int {
        return countInside(getBag("shiny gold"))
    }

    private fun countInside(bag: Bag): Int {
        return bag.inside
            .filter { bag.inside.isNotEmpty() }
            .map { colorAndAmount -> colorAndAmount.value * (1 + countInside(getBag(colorAndAmount.key))) }
            .sum()
    }

    private fun parseRule(input: String): Bag {
        val bagAndContains = input.split("contain")
        val bag = bagAndContains[0].trim().split(" ")
        val contains = parseContaining(bagAndContains[1])
        return Bag(color = "${bag[0]} ${bag[1]}", contains)
    }

    private fun parseContaining(containing: String): Map<String, Int> {
        if (containing.contains("no other")){
            return emptyMap()
        }
        return containing.split(",")
            .map { bag ->
                val words = bag.trim().split(" ")
                "${words[1]} ${words[2]}" to words[0].toInt()
            }.toMap()
    }

    private fun mapBags(inputs: List<String>): Map<String, Bag> {
        return inputs
            .map { parseRule(it) }
            .associateBy { bag -> bag.color }
            .toMap()
    }

    private fun getBag(color: String): Bag {
        return bags[color] ?: throw IllegalArgumentException("No bag of color: $color")
    }

    data class Bag(
        val color: String,
        val inside: Map<String, Int>
    )
}