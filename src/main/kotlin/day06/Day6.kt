package day06

fun main() {
    val input = InputUtils.getString("day06.txt")
    val day6 = Day6()
    println(day6.part1(input))
    println(day6.part2(input))
}

class Day6 {

    fun part1(input: String): Int {
        val groups = input.split("\n\r\n")
        var count = 0
        for (answers in groups) {
            val groupAnswer = parseAnswer(answers)
            count += groupAnswer.size
        }
        return count
    }

    fun part2(input: String): Int {
        val groups = input.split("\n\r\n")
        var count = 0
        for (answers in groups) {
            var inCommon = CharRange('a', 'z').toSet()
            val individualAnswers = answers.split("\n")
            for (answer in individualAnswers) {
                inCommon = inCommon.intersect(parseAnswer(answer))
            }
            count += inCommon.size
        }
        return count
    }

    private fun parseAnswer(input: String): Set<Char> {
        return input
            .filter { c -> c != '\n' && c != '\r' }
            .toSet()
    }
}