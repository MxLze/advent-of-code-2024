fun main() {

    val xmasRegex = Regex("XMAS")
    val samxRegex = Regex("SAMX")

    fun parseInput(input: List<String>): List<List<String>> {

        // iterate vertically
        val verticalLines = input.indices.map { y -> input.mapNotNull { it.getOrNull(y) }.joinToString("") }

        val width = input.first().length
        val height = input.size

        // iterate diagonally
        val diagonalLines1 = (0..(width + height - 2)).map { iteration ->
            (0..iteration).mapNotNull { x -> input.getOrNull(x)?.getOrNull(iteration - x) }.joinToString("")
        }

        val diagonalLines2 = (0..(width + height - 2)).map { iteration ->
            (0..iteration).mapNotNull { x -> input.getOrNull(x)?.getOrNull(width - 1 - (iteration - x)) }.joinToString("")
        }

        return listOf(input, verticalLines, diagonalLines1, diagonalLines2)
    }

    fun part1(input: List<String>): Int {
        val data = parseInput(input)

        return data.sumOf { projection ->
            projection.sumOf { line ->
                xmasRegex.findAll(line).count() + samxRegex.findAll(line).count()
            }
        }
    }

    fun part2(input: List<String>): Int {

        return (1 until input.size - 1).sumOf { y ->
            (1 until input.first().length - 1).count { x ->
                val c = input[y][x]

                val upLeft = input[y - 1][x - 1]
                val upRight = input[y - 1][x + 1]
                val downLeft = input[y + 1][x - 1]
                val downRight = input[y + 1][x + 1]

                return@count (c == 'A')
                        && ((upLeft == 'M' && downRight == 'S') || (upLeft == 'S' && downRight == 'M'))
                        && ((upRight == 'M' && downLeft == 'S') || (upRight == 'S' && downLeft == 'M'))

            }
        }
    }

    // Test if implementation meets criteria from the description
    // part1
    check(part1(listOf("XMAS", "OOOO", "OOOO", "OOOO")) == 1)
    check(part1(listOf("XXXX")) == 0)
    check(part1(listOf("..X...", ".SAMX.", ".A..A.", "XMAS.S", ".X....")) == 4)
    // part2
    check(part2(listOf("M.S", ".A.", "M.S")) == 1)


    // Test input from the description
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 18)
    check(part2(testInput) == 9)

    // Read the input from file.
    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
