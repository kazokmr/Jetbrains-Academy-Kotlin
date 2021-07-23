package signature

import kotlin.math.abs
import kotlin.math.max

var leftSpaceOfName = 2
var rightSpaceOfName = 2
var leftSpaceOfStatus = 2
var rightSpaceOfStatus = 2

val frame = { length: Int -> "8".repeat(length) }
val outputLine =
    { line: String, leftSpace: Int, rightSpace: Int -> "88${" ".repeat(leftSpace)}$line${" ".repeat(rightSpace)}88" }

fun main() {
    println("Enter name and surname:")
    val name = Signature(readLine()!!, "$DIR_PATH/roman.txt", 10)
    println("Enter person's status:")
    val status = Signature(readLine()!!, "$DIR_PATH/medium.txt", 5)
    printSignature(name, status)
}

fun printSignature(name: Signature, status: Signature) {
    adjustSpace(name, status)
    val lengthOfName = 2 + leftSpaceOfName + name.length() + rightSpaceOfName + 2
    val lengthOfStatus = 2 + leftSpaceOfStatus + status.length() + rightSpaceOfStatus + 2
    val length = max(lengthOfName, lengthOfStatus)

    println(frame(length))
    (0 until name.fontSize).forEach { println(outputLine(name.line(it), leftSpaceOfName, rightSpaceOfName)) }
    (0 until status.fontSize).forEach { println(outputLine(status.line(it), leftSpaceOfStatus, rightSpaceOfStatus)) }
    println(frame(length))
}

fun adjustSpace(name: Signature, status: Signature) {
    val diff = (status.length() - name.length()) / 2
    if (diff > 0) {
        leftSpaceOfName += diff
        rightSpaceOfName += status.length() - name.length() - diff
    } else {
        leftSpaceOfStatus += abs(diff)
        rightSpaceOfStatus += name.length() - status.length() - abs(diff)
    }
}

private const val DIR_PATH = "/Users/kazokmr/Downloads"
