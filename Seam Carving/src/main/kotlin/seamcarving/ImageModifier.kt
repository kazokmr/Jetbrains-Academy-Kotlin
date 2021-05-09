package seamcarving

import java.awt.Color
import java.awt.image.BufferedImage
import kotlin.math.pow
import kotlin.math.sqrt

open class ImageModifier(val args: Array<String>) {
    open fun draw() = println()

    private val gradientByColor = { a: Int, b: Int -> (a.toDouble() - b.toDouble()).pow(2.0) }
    private val prev = { x: Int, max: Int -> if (x == 0) 0 else if (x == max - 1) max - 3 else x - 1 }
    private val next = { x: Int, max: Int -> if (x == 0) 2 else if (x == max - 1) max - 1 else x + 1 }

    open fun BufferedImage.energies(width: Int, height: Int) =
        MutableList(height) { y -> MutableList(width) { x -> energyOfPixelAt(x, y) } }

    fun BufferedImage.energyOfPixelAt(x: Int, y: Int): Double =
        sqrt(delta(prev(x, width), next(x, width), y, y) + delta(x, x, prev(y, height), next(y, height)))

    private fun BufferedImage.delta(x1: Int, x2: Int, y1: Int, y2: Int) =
        gradientByColor(Color(getRGB(x1, y1)).red, Color(getRGB(x2, y2)).red) +
                gradientByColor(Color(getRGB(x1, y1)).green, Color(getRGB(x2, y2)).green) +
                gradientByColor(Color(getRGB(x1, y1)).blue, Color(getRGB(x2, y2)).blue)
}