package day02

fun main() {
    val input = InputUtils.getLines("day02.txt")
    val day2 = Day2()
    println(day2.part1(input))
    println(day2.part2(input))
}

class Day2 {

    fun part1(inputs : List<String>) : Long {
        return inputs.stream()
            .map { createPolicy(it) }
            .filter{ passwordPolicy ->
                val occurrences = countOccurrences(passwordPolicy)
                passwordPolicy.maxOccurrence >= occurrences &&
                        passwordPolicy.minOccurrence <= occurrences
            }.count()
    }

    fun part2(inputs : List<String>) : Long {
        return inputs.stream()
            .map { createPolicy(it) }
            .filter{singleOccurrence(it)}
            .count()
    }

    private fun countOccurrences(passwordPolicy: PasswordPolicy) : Int {
        var occurences = 0
        passwordPolicy.password.forEach { char ->
            if (char == passwordPolicy.letter) {
                occurences++
            }
        }
        return occurences
    }

    private fun singleOccurrence(passwordPolicy: PasswordPolicy) : Boolean {
        val first = passwordPolicy.password[passwordPolicy.minOccurrence - 1]
        val second = passwordPolicy.password[passwordPolicy.maxOccurrence - 1]
        return (first == passwordPolicy.letter) xor (second == passwordPolicy.letter)
    }

    private fun createPolicy(row : String) : PasswordPolicy {
        val parts = row.split(" ")
        val minAndMax = parts[0].split("-")
        return PasswordPolicy(
            parts[2],
            parts[1][0],
            Integer.parseInt(minAndMax[0]),
            Integer.parseInt(minAndMax[1])
        )
    }

    private data class PasswordPolicy(
        val password: String,
        val letter: Char,
        val minOccurrence: Int,
        val maxOccurrence: Int
    )


}