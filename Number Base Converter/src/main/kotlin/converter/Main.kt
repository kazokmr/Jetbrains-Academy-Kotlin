package converter

import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode

fun main() {
    menu()
}

fun menu() {
    println("Enter two numbers in format: {source base} {target base} (To quit type /exit)")
    when (val input = readLine()!!) {
        "/exit" -> return
        else -> inputNumber(input)
    }
    println()
    menu()
}

fun inputNumber(bases: String) {
    val (source, target) = bases.split(" ").map { it.toInt() }
    println("Enter number in base $source to convert to base $target (To go back type /back)")
    when (val input = readLine()!!) {
        "/back" -> return
        else -> println("Conversion result: ${convert(input, source, target)}")
    }
    println()
    inputNumber(bases)
}

fun convert(input: String, fromBase: Int, toBase: Int): String {
    if (input.contains(".").not()) {
        return BigInteger(input, fromBase).toString(toBase)
    }
    val (integer, fraction) = input.split(".")
    return "${BigInteger(integer, fromBase).toString(toBase)}.${convertFractionalPart(fraction, fromBase, toBase)}"
}

fun convertFractionalPart(fraction: String, fromBase: Int, toBase: Int): String {
    var decimal: BigDecimal = BigDecimal.ZERO
    for (index in fraction.indices) {
        val integer = fraction[index].toString().toBigInteger(fromBase)
        decimal += integer.toBigDecimal().divide(fromBase.toBigDecimal().pow(index + 1), 10, RoundingMode.DOWN)
    }
    var result = ""
    repeat(5) {
        decimal = decimal.multiply(toBase.toBigDecimal())
        val integer = decimal.toBigInteger()
        result += integer.toString(toBase)
        decimal -= integer.toBigDecimal()
    }
    return result
}
