
fun main() {
    val inputs = InputUtils.getInts("day10.txt")
    val day10 = Day10(inputs)
    println(day10.part1())
    println(day10.part2())
}

class Day10(adapters : List<Int>) {

    private val adapterBag = adapters.toSet()
    private val device = 1

    fun part1() : Pair<Int, Int> {
        var up3 = 0
        var up1 = 0
        var current = 0
        val adapters = generateSequence(0) { if (it < (adapterBag.maxOrNull() ?: -1)) findAdapters(it).first() else null}
        adapters.forEach {
                when (it) {
                    current + 3 -> up3++
                    current + 1 -> up1++
                }
                current = it
            }
        return up1 to (up3 + device)
    }

    fun part2() : Long {
        val pathsForAdapter = mutableMapOf<Int, Long>(0 to 1)
        adapterBag.sorted().forEach { jolt ->
            pathsForAdapter[jolt] = (1..3).map {
                pathsForAdapter.getOrDefault(jolt - it, 0)
            }.sum()
        }
        return pathsForAdapter.getValue(adapterBag.maxOrNull() ?: throw IllegalArgumentException("No max value"))
    }

    private fun findAdapters(currentJolt: Int) : List<Int> {
        return (1..3)
            .map { currentJolt + it  }
            .filter { adapterBag.contains(it) }
            .toList()

    }

}