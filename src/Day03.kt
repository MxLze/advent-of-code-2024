fun main() {

    val rangeRegex = Regex("(?:^|do\\(\\))(.*?)(?:\$|don't\\(\\))")
    val multRegex = Regex("mul\\((\\d+),(\\d+)\\)")

    fun parseInput(input: List<String>) = input.joinToString("")

    fun part1(input: List<String>): Int {
        val data = parseInput(input)

        return multRegex.findAll(data)
            .map { it.groupValues }
            // regex groups are 1-indexed, 0 corresponds to the whole match
            .map { it[1].toInt() * it.last().toInt() }
            .sum()
    }

    fun part2(input: List<String>): Int {
        val data = parseInput(input)

        return rangeRegex.findAll(data)
            .map { it.value }
            .flatMap { multRegex.findAll(it) }
            .map { it.groupValues }
            .map { it[1].toInt() * it.last().toInt() }
            .sum()
    }

    // Test if implementation meets criteria from the description
    // part1
    check(part1(listOf("aaaa")) == 0)
    check(part1(listOf("mul(1,3)")) == 3)
    check(part1(listOf("mul(2,3)aaaamul(1,12)")) == 18)
    // part2


    // Test input from the description
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 161)
    check(part2(testInput) == 48)

    // Read the input from file.
    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
