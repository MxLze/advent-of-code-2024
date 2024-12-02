import kotlin.math.abs

fun main() {
    fun parseInput(input: List<String>) = input
        .map { it.split("   ") }
        .map { it.first().toInt() to it.last().toInt() }

    fun part1(input: List<String>): Int {
        val data = parseInput(input)

        val l1 = data.map { it.first }.sorted()
        val l2 = data.map { it.second }.sorted()

        return l1.zip(l2).sumOf { abs(it.first - it.second) }
    }

    fun part2(input: List<String>): Int {
        val data = parseInput(input)

        val l1 = data.map { it.first }
        val l2 = data.map { it.second }

        return l1.sumOf { value -> value * l2.count { it == value } }
    }

    // Test if implementation meets criteria from the description
    // part1
    check(part1(listOf("1   0")) == 1)
    check(part1(listOf("0   1")) == 1)
    check(part1(listOf("1   1")) == 0)
    check(part1(listOf("-1   1")) == 2)
    // part2
    check(part2(listOf("1   1")) == 1)
    check(part2(listOf("2   2")) == 2)
    check(part2(listOf("2   2", "0   2")) == 4)

    // Test input from the description
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 11)
    check(part2(testInput) == 31)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
