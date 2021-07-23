package search

class PeopleSearchEngine {

    val allPeopleBy = { query: String ->
        peopleMap.keys.filter { index: Int -> indicesBy(query).all { it.contains(index) } }.map { peopleMap[it]!! }
    }

    val anyPeopleBy = { query: String ->
        peopleMap.keys.filter { index: Int -> indicesBy(query).any { it.contains(index) } }.map { peopleMap[it]!! }
    }

    val noneOfPeopleBy = { query: String ->
        peopleMap.keys.filterNot { index: Int -> indicesBy(query).any { it.contains(index) } }.map { peopleMap[it]!! }
    }

    val peopleBy = { query: String -> indexMap[query]?.map { peopleMap[it] } ?: emptyList() }

    fun printAllPeople() {
        println("=== List of people ===")
        peopleMap.values.forEach { person -> println(person) }
    }

    fun addPerson(person: String) = add(person)

    companion object {
        private val peopleMap = mutableMapOf<Int, String>()
        private val indexMap = mutableMapOf<String, MutableList<Int>>()
        private val indicesBy = { query: String -> query.split(" ").map { indexMap[it.toLowerCase()] ?: emptyList() } }
        private fun add(person: String) {
            val index = peopleMap.size
            peopleMap[index] = person
            person.split(" ")
                .map { it.toLowerCase() }
                .forEach { indexMap.computeIfAbsent(it) { mutableListOf() }.apply { add(index) } }
        }
    }
}