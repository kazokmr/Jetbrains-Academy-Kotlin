package parking


fun main() {
    val parkingLot = ParkingLot()
    while (true) {
        val input = readLine()!!.split(" ")
        if (input[0] != "create" && input[0] != "exit" && !parkingLot.isOperate()) continue
        when (input[0]) {
            "park" -> parkingLot.park(Car(input[1], input[2]))
            "leave" -> parkingLot.leave(input[1].toInt())
            "create" -> parkingLot.create(input[1].toInt())
            "status" -> parkingLot.status()
            "reg_by_color" -> parkingLot.regByColor(input[1])
            "spot_by_color" -> parkingLot.spotByColor(input[1])
            "spot_by_reg" -> parkingLot.spotByReg(input[1])
            "exit" -> return
        }
    }
}
