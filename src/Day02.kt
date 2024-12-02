fun main() {
    fun parseInput(input: List<String>) = input
        .map { line -> line.split(" ").map { it.toInt() } }

    fun isValidReport(it: List<Int>) = (it == it.sorted() || it == it.sortedDescending())
            && it.sorted().zipWithNext { a, b -> b - a in 1..3 }.all { it }

    fun part1(input: List<String>): Int {
        val data = parseInput(input)

        return data
            .filter { isValidReport(it) }
            .size
    }

    fun part2(input: List<String>): Int {
        val data = parseInput(input)

        return data
            .filter { report ->
                report.indices.any { n -> isValidReport(report.filterIndexed { index, _ -> index != n }) }
            }
            .size
    }

    // Test if implementation meets criteria from the description
    // part1
    check(part1(listOf("1 0")) == 1)
    check(part1(listOf("0 1 2")) == 1)
    check(part1(listOf("2 1 0")) == 1)
    check(part1(listOf("5 1 0")) == 0)
    // part2
    check(part2(listOf("5 1 0")) == 1)

    // Test input from the description
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    // Read the input from file.
    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
