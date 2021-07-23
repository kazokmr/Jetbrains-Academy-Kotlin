package seamcarving

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

class Resizing(args: Array<String>) : VerticalSeam(args) {
    override fun draw() {
        ImageIO.write(resizedImage(args[1], args[5].toInt(), args[7].toInt()), "png", File(args[3]))
    }

    val resizedImage = { fileName: String, resizeW: Int, resizeH: Int ->
        ImageIO.read(File(fileName)).removeSeam(resizeW, resizeH)
    }

    private fun BufferedImage.removeSeam(resizeW: Int, resizeH: Int): BufferedImage {
        var resizedImage = BufferedImage(width, height, type)
        (0 until width).forEach { x ->
            (0 until height).forEach { y ->
                resizedImage.setRGB(x, y, this.getRGB(x, y))
            }
        }
        resizedImage = resizedImage.removeVertical(resizeW)
        resizedImage = resizedImage.removeHorizontal(resizeH)
        return resizedImage
    }

    private fun BufferedImage.removeVertical(resizeW: Int): BufferedImage {
        val pixels = MutableList(height) { y -> MutableList(width) { x -> getRGB(x, y) } }
        val minEnergies = MutableList(height) { y -> MutableList(width) { x -> energyOfPixelAt(x, y) } }
        calcMinEnergyAt(minEnergies)
        repeat(resizeW) {
            val minIndex = minEnergies.last().withIndex().minByOrNull { it.value }!!.index
            removeVSeam(minIndex, minEnergies.lastIndex, minEnergies, pixels)
        }
        val resizedImage = BufferedImage(width - resizeW, height, type)
        pixels.forEachIndexed { y, row -> row.forEachIndexed { x, rgb -> resizedImage.setRGB(x, y, rgb) } }
        return resizedImage
    }

    private fun BufferedImage.removeHorizontal(resizeH: Int): BufferedImage {
        val pixels = MutableList(width) { x -> MutableList(height) { y -> getRGB(x, y) } }
        val minEnergies = MutableList(width) { x -> MutableList(height) { y -> energyOfPixelAt(x, y) } }
        calcMinEnergyAt(minEnergies)
        repeat(resizeH) {
            val minIndex = minEnergies.last().withIndex().minByOrNull { it.value }!!.index
            removeHSeam(minEnergies.lastIndex, minIndex, minEnergies, pixels)
        }
        val resizedImage = BufferedImage(width, height - resizeH, type)
        pixels.forEachIndexed { x, col -> col.forEachIndexed { y, rgb -> resizedImage.setRGB(x, y, rgb) } }
        return resizedImage
    }

    private fun removeVSeam(
        x: Int,
        y: Int,
        minEnergies: MutableList<MutableList<Double>>,
        pixels: MutableList<MutableList<Int>>
    ) {
        pixels[y].removeAt(x)
        minEnergies[y].removeAt(x)
        if (y - 1 < 0) return
        removeVSeam(minEnergiesByRow(minEnergies[y - 1], x).index, y - 1, minEnergies, pixels)
    }

    private fun removeHSeam(
        x: Int,
        y: Int,
        minEnergies: MutableList<MutableList<Double>>,
        pixels: MutableList<MutableList<Int>>
    ) {
        pixels[x].removeAt(y)
        minEnergies[x].removeAt(y)
        if (x - 1 < 0) return
        removeHSeam(x - 1, minEnergiesByRow(minEnergies[x - 1], y).index, minEnergies, pixels)
    }
}