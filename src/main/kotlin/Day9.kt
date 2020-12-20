
fun main() {
    val input = InputUtils.getLongs("day09.txt")
    val day9 = Day9()
    val weakness = day9.part1(input, preamble = 25)
    println(weakness)
    println(day9.part2(input, weakness))
}

class Day9 {

    fun part1(xmas: List<Long>, preamble: Int = 25) : Long {
        val firstInvalid = xmas.indices
            .drop(preamble)
            .first { !isValid(xmas, it, preamble) }
        return xmas[firstInvalid]
    }

    fun part2(xmas: List<Long>, weakness: Long) : Long {
        return xmas.indices
            .map { sumSequence(xmas, it, weakness) }
            .first { it != -1L }
    }

    private fun sumSequence(input: List<Long>, from: Int, goal: Long) : Long {
        var count = 0L
        val sequence = mutableListOf<Long>()
        for (i in from until input.size) {
            val element = input[i]
            count += element
            sequence.add(element)
            if (count == goal) {
                sequence.sort()
                return sequence.first() + sequence.last()
            } else if (count > goal) {
                return -1
            }
        }
        return -1
    }

    private fun isValid(input: List<Long>, index: Int, preamble: Int) : Boolean {
        val sum = input[index]
        val range = input.drop(index - preamble).take(preamble)
        return range.flatMap { number ->
            range.map { it + number }
                .filter { it != number * 2 }
        }.distinct().any { it == sum }
    }

}