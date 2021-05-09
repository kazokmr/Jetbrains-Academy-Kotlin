package parking

data class Car(val regNumber: String, val color: String)

class ParkingLot {
    private var spots = Array<Car?>(0) { null }

    fun park(car: Car) {
        if (spots.none { it == null }) {
            println("Sorry, the parking lot is full.")
        } else {
            val index = spots.withIndex().firstOrNull { it.value == null }?.index ?: -1
            spots[index] = car
            println("${car.color} car parked in spot ${index + 1}.")
        }
    }

    fun leave(number: Int) {
        if (spots[number - 1] == null) {
            println("There is no car in spot $number.")
        } else {
            spots[number - 1] = null
            println("Spot $number is free.")
        }
    }

    fun status() {
        if (spots.all { it == null }) {
            println("Parking lot is empty.")
        } else {
            spots
                .withIndex()
                .filter { it.value != null }
                .forEach { (index, spot) -> println("${index + 1} ${spot?.regNumber} ${spot?.color}") }
        }
    }

    fun create(number: Int) {
        spots = Array(number) { null }
        println("Created a parking lot with $number spots.")
    }

    fun regByColor(color: String) {
        val cars = spots.filter { it?.color.equals(color, ignoreCase = true) }.map { it?.regNumber }
        println(if (cars.isEmpty()) "No cars with color $color were found." else cars.joinToString())
    }

    fun spotByColor(color: String) {
        val nums = spots.withIndex().filter { it.value?.color.equals(color, ignoreCase = true) }.map { it.index + 1 }
        println(if (nums.isEmpty()) "No cars with color $color were found." else nums.joinToString())
    }

    fun spotByReg(regNum: String) {
        val nums =
            spots.withIndex().filter { it.value?.regNumber.equals(regNum, ignoreCase = true) }.map { it.index + 1 }
        println(if (nums.isEmpty()) "No cars with registration number $regNum were found." else nums.joinToString())
    }

    val isOperate = {
        val isSpotEmpty = spots.isEmpty()
        if (isSpotEmpty) println("Sorry, a parking lot has not been created.")
        !isSpotEmpty
    }
}
