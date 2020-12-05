package day04

fun main() {
    val input = InputUtils.getStrings("day04.txt", "\r\n\r")
    val day4 = Day4()
    println(day4.part1(input))
    println(day4.part2(input))

}

class Day4 {

    fun part1(passPorts: List<String>) : Int {
        return passPorts.count { passPort -> requiredFields.all { passPort.contains(it) }}
    }

    fun part2(passPorts: List<String>) : Int {
        return passPorts.count { passPort -> validFields.all { it.containsMatchIn(passPort) }}
    }

    companion object {

        val requiredFields = listOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")

        val validFields = listOf(
            """\bbyr:(19[2-9][0-9]|200[0-2])\b""",
            """\biyr:(201[0-9]|2020)\b""",
            """\beyr:(202[0-9]|2030)\b""",
            """\bhgt:(1([5-8][0-9]|9[0-3])cm)|((59|6[0-9]|7[0-6])in)\b""",
            """\bhcl:(#[a-f0-9]{6})\b""",
            """\becl:(amb|blu|brn|gry|grn|hzl|oth)\b""",
            """\bpid:[0-9]{9}\b""",
        ).map { it.toRegex() }

    }
}