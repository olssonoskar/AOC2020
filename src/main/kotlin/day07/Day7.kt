package day07

import java.lang.IllegalArgumentException


fun main() {
    val input = InputUtils.getStrings("day07.txt")
    val day7 = Day7()
    println(day7.part1(input))
    println(day7.part2(input))
}

// Works, but should be a better way to solve this
class Day7 {

    private var bags = mapOf<String, Bag>()
    private var containsShiny = mutableSetOf<String>()

    fun part1(inputs: List<String>): Int {
        var count = 0
        mapBags(inputs)
        for (bag in bags.values) {
            if(canHoldShiny(bag)) {
                containsShiny.add(bag.color)
                count++
            }
        }
        return count
    }

    /**
     * Check bags recursively, adding to a global state to avoid some of the extra checks
     */
    private fun canHoldShiny(bag: Bag): Boolean {
        if (bag.inside.contains("shiny gold")) {
            return true
        }
        for (color in bag.inside.keys) {
            if (containsShiny.contains(color)) {
                return true
            }
        }
        return bag.inside.keys
            .map { color -> bags[color] }
            .filter { containedBag -> canHoldShiny(containedBag!!) }
            .any()
    }

    /**
    * Count all the bags inside the shiny (therefore removing 1 from result)
    */
    fun part2(inputs: List<String>): Int {
        mapBags(inputs)
        val shiny = bags["shiny gold"] ?: throw IllegalArgumentException("No shiny bag :(")
        return countInside(shiny) - 1
    }

    private fun countInside(bag: Bag): Int {
        if (bag.inside.isEmpty()) {
            return 1
        }
        return bag.inside
            .map { colorToAmount -> colorToAmount.value to bags[colorToAmount.key] }
            .map { amountToBag -> amountToBag.first * countInside(amountToBag.second!!) }
            .sum() + 1
    }

    private fun parseRule(input: String): Bag {
        val bagAndContains = input.split("contain")
        val bag = bagAndContains[0].trim().split(" ")
        val contains = parseContaining(bagAndContains[1])
        return Bag(color = "${bag[0]} ${bag[1]}", contains)
    }

    private fun parseContaining(contains: String): Map<String, Int> {
        if (contains.contains("no other")){
            return emptyMap()
        }
        return contains.split(",")
            .map { bag ->
                val words = bag.trim().split(" ")
                "${words[1]} ${words[2]}" to words[0].toInt()
            }.toMap()
    }

    private fun mapBags(inputs: List<String>) {
        bags = inputs
            .map { parseRule(it) }
            .associateBy { bag -> bag.color }
            .toMap()
    }

    data class Bag(
        val color: String,
        val inside: Map<String, Int>
    )
}