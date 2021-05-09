package signature

import java.io.File

class Signature(private val text: String, filePath: String, private val sizeOfSpace: Int) {
    private val lines = File(filePath).readLines()
    val fontSize = lines[0].split(" ")[0].toInt()
    val length = { text.toCharArray().sumOf { lengthOfChar(it) } }
    val line = { numOfLine: Int -> text.toCharArray().joinToString("") { charByLine(it, numOfLine) } }

    val index = { char: Char ->
        val startLower = 1
        val startUpper = startLower + lines[0].split(" ")[1].toInt() / 2 * (fontSize + 1)
        if (char.isLowerCase()) startLower + (char - 'a') * (fontSize + 1) else startUpper + (char - 'A') * (fontSize + 1)
    }

    private fun lengthOfChar(char: Char) =
        if (char.isWhitespace()) sizeOfSpace else lines[index(char)].split(" ")[1].toInt()

    private fun charByLine(char: Char, numOfLine: Int) =
        if (char.isWhitespace()) " ".repeat(sizeOfSpace) else lines[index(char) + 1 + numOfLine]
}