package search

import java.io.File

val people = PeopleSearchEngine()

fun main(args: Array<String>) {
    File(args[1]).forEachLine { people.addPerson(it) }
    showMenu()
}

fun showMenu() {
    println(
        """
            
            === Menu ===
            1. Find a person
            2. Print all people
            0. Exit
        """.trimIndent()
    )
    val select = readLine()
    println()
    when (select) {
        "1" -> findPeople()
        "2" -> people.printAllPeople()
        "0" -> {
            println("Bye!")
            return
        }
        else -> println("Incorrect option! Try again.")
    }
    showMenu()
}

fun findPeople() {
    println("Select a matching strategy: ALL, ANY, NONE")
    val strategy = readLine() ?: ""

    println("\nEnter a name or email to search all suitable people.")
    val query = readLine() ?: " "

    when (strategy) {
        "ALL" -> people.allPeopleBy(query)
        "ANY" -> people.anyPeopleBy(query)
        "NONE" -> people.noneOfPeopleBy(query)
        else -> people.peopleBy(query)
    }.let {
        if (it.isEmpty()) println("\nNo matching people found.")
        else {
            println("\n${it.size} persons found:")
            it.forEach { person -> println(person) }
        }
    }
}

fun initByCommand(): List<String> {
    println("Enter the number of people:")
    val numOfPeople = readLine()?.toInt() ?: 0
    println("Enter all people:")
    return List(numOfPeople) { readLine() ?: "" }
}
