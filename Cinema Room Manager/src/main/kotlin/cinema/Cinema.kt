package cinema

fun main() {
    val cinema = init()
    showMenu(cinema)
}

fun init(): Array<CharArray> {
    println("Enter the number of rows:")
    val row = readLine()!!.toInt()
    println("Enter the number of seats in each row:")
    val seatsInRow = readLine()!!.toInt()
    return Array(row) { CharArray(seatsInRow) { 'S' } }
}

fun showMenu(cinema: Array<CharArray>) {
    println()
    println("1. Show the seats")
    println("2. Buy a ticket")
    println("3. Statistics")
    println("0. Exit")
    val command = try {
        readLine()!!.toInt()
    } catch (e: Exception) {
        9
    }
    println()
    when (command) {
        1 -> showCinema(cinema)
        2 -> buySeat(cinema)
        3 -> showStatics(cinema)
        0 -> return
    }
    showMenu(cinema)
}

fun buySeat(cinema: Array<CharArray>) {
    println("Enter a row number:")
    val row = readLine()!!.toInt()
    println("Enter a seat number in that row:")
    val seatInRow = readLine()!!.toInt()
    if (isSheetEmpty(cinema, row, seatInRow)) {
        println("\nTicket price: \$${getPrice(cinema, row)}")
        cinema[row - 1][seatInRow - 1] = 'B'
        return
    }
    println()
    buySeat(cinema)
}

fun isSheetEmpty(cinema: Array<CharArray>, row: Int, seatInRow: Int): Boolean {
    return try {
        when {
            cinema[row - 1][seatInRow - 1] == 'B' -> {
                println("That ticket has already been purchased!")
                false
            }
            else -> true
        }
    } catch (e: Exception) {
        println("Wrong input!")
        false
    }
}

fun getPrice(cinema: Array<CharArray>, curRow: Int): Int {
    val row = cinema.size
    val seatsInRow = cinema[0].size
    return when {
        row * seatsInRow <= 60 -> 10
        curRow <= row / 2 -> 10
        else -> 8
    }
}

fun showCinema(cinema: Array<CharArray>) {
    println("Cinema:")
    print(" ")
    for (i in 1..cinema[0].size) print(" $i")
    println()
    for (row in cinema.indices) println("${row + 1} ${cinema[row].joinToString(separator = " ")}")
}

fun showStatics(cinema: Array<CharArray>) {
    val numOfSeats = cinema.size * cinema[0].size
    var numOfPurchasedTickets = 0
    var currentIncome = 0
    var totalIncome = 0

    for (row in cinema.indices) {
        val price = getPrice(cinema, row + 1)
        totalIncome += price * cinema[row].size
        for (seat in cinema[row]) {
            if (seat == 'B') {
                numOfPurchasedTickets++
                currentIncome += price
            }
        }
    }
    println("Number of purchased tickets: $numOfPurchasedTickets")
    println("Percentage: ${"%.2f".format(numOfPurchasedTickets.toDouble() / numOfSeats.toDouble() * 100)}%")
    println("Current income: \$$currentIncome")
    println("Total income: \$$totalIncome")
}
