
fun main() {
    val input = InputUtils.getLongs("day09.txt")
    val day9 = Day9()
    println(day9.part1(input, preamble = 25))
}

class Day9 {

    fun part1(xmas: List<Long>, preamble: Int = 25) : Long {
        val firstInvalid = xmas.indices
            .drop(preamble)
            .first { !isValid(xmas, it, preamble) }
        return xmas[firstInvalid]
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