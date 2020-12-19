
fun main() {
    val inputs = InputUtils.getLines("day08.txt")
    val day8 = Day8()
    println(day8.run(inputs))
    println(day8.runUntilSuccess(inputs))
}

class Day8 {

    private var pc = 0
    private var acc = 0
    private var executed = mutableSetOf<Int>()
    private val noop = Unit

    fun run(instructions: List<String>) : String {
        executed = mutableSetOf()
        while (pc < instructions.size) {
            if (executed.contains(pc)) {
                return "Loop detected: acc = $acc"
            }
            execute(parse(instructions[pc]))
        }
        return if (pc == instructions.size) {
            "Success: acc = $acc"
        } else {
            "Corrupt run"
        }

    }

    fun runUntilSuccess(instructions: List<String>): String {
        for (i in instructions.indices) {
            reset()
            val copy = instructions.toMutableList()
            val instruction = parse(instructions[i])
            when (instruction.type) {
                InstructionType.JMP -> copy[i] = Instruction(InstructionType.NOP, instruction.arg).toString()
                InstructionType.NOP -> copy[i] = Instruction(InstructionType.JMP, instruction.arg).toString()
                else -> continue
            }
            val result = run(copy)
            if (result.contains("Success")) {
                return result
            }
        }
        return "No valid run was found"
    }

    private fun execute(instruction: Instruction) {
        executed.add(pc)
        when (instruction.type) {
            InstructionType.JMP -> pc += instruction.arg
            InstructionType.ACC -> acc += instruction.arg
            InstructionType.NOP -> { noop }
        }
        if (InstructionType.JMP != instruction.type) {
            pc++
        }
    }

    private fun parse(instruction: String): Instruction {
        val opAndArg = instruction.split(" ")
        val positive = opAndArg[1][0] == '+'
        val value = Integer.parseInt(opAndArg[1].substring(1))
        return Instruction(
            InstructionType.valueOf(opAndArg[0].toUpperCase()),
            if (positive) value else -value
        )
    }

    private data class Instruction(
        val type: InstructionType,
        val arg: Int
    ) {
        override fun toString() : String {
            val sign = if (arg < 0) "" else "+"
            return "${type.toString().toLowerCase()} $sign$arg"
        }
    }

    private enum class InstructionType {
        ACC, JMP, NOP
    }

    private fun reset() {
        pc = 0
        acc = 0
    }

}