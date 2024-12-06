fun main() {

    fun parseInput(input: List<String>): Pair<List<Pair<Int, Int>>, List<List<Int>>> {

        val separatorIndex = input.indexOf("")

        val rules = (0 until separatorIndex)
            .map { input[it] }
            .map { it.split('|').map(String::toInt) }
            .map { it[0] to it[1] }

        val data = (separatorIndex + 1 until input.size)
            .map { input[it] }
            .map { it.split(',').map(String::toInt) }

        return rules to data

    }

    /**
     * Returns the rules that are concerned by the given update
     */
    fun concernedRules(update: List<Int>, rules: List<Pair<Int, Int>>): List<Pair<Int, Int>> = rules
        .filter { update.contains(it.first) && update.contains(it.second) }

    /**
     * Returns true if the given update respects the rules
     */
    fun respectsRules(update: List<Int>, rules: List<Pair<Int, Int>>): Boolean {
        // probably not the most efficient way to iterate over the rules
        return concernedRules(update, rules)
            .all { update.indexOf(it.first) < update.indexOf(it.second) }
    }

    /**
     * Solves part1 of the problem
     */
    fun part1(input: List<String>): Int {
        val data = parseInput(input)


        return data.second
            .filter { update -> respectsRules(update, data.first) }
            .sumOf { it[it.size / 2] } // middle element
    }

    /**
     * Solves part2 of the problem
     */
    fun part2(input: List<String>): Int {
        val data = parseInput(input)

        return data.second
            .filter { update -> !respectsRules(update, data.first) }
            // sort according to the rules
            .map {
                val pagesToSort = it.toMutableList()

                val sortedPages = mutableListOf<Int>()

                var concernedRules = concernedRules(it, data.first)

                do {
                    // finding the next page to add
                    val pageToAdd = pagesToSort.first { page ->
                        !concernedRules.map { it.second }.contains(page)
                    }

                    sortedPages.add(pageToAdd)
                    pagesToSort.remove(pageToAdd)

                    concernedRules = concernedRules(pagesToSort, concernedRules)
                } while (concernedRules.size != 1)

                // only one rule left, adding the last 2 pages
                sortedPages.add(concernedRules[0].first)
                sortedPages.add(concernedRules[0].second)

                return@map sortedPages
            }
            .sumOf { it[it.size / 2] }

    }

    // Test if implementation meets criteria from the description
    // part1
    check(part1(listOf("47|53", "", "1")) == 1)
    check(part1(listOf("1|2", "2|3", "", "1,2,3")) == 2)
    check(part1(listOf("1|2", "2|3", "", "3,2,1")) == 0)
    // part2
    check(part2(listOf("1|2", "2|3", "", "1,2,3")) == 0)
    check(part2(listOf("1|2", "2|3", "", "3,1,2")) == 2)


    // Test input from the description
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 143)
    check(part2(testInput) == 123)

    // Read the input from file.
    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
