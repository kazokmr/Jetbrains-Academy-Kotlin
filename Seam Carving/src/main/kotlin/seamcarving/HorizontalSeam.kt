package seamcarving

import java.awt.Color
import java.awt.image.BufferedImage

class HorizontalSeam(args: Array<String>) : VerticalSeam(args) {

    override fun BufferedImage.seam() {
        val minEnergies = energies(width, height)
        calcMinEnergyAt(minEnergies)
        minEnergies.last()
            .withIndex()
            .filter { it.value == minEnergies.last().minOrNull() }
            .forEach { setSeam(minEnergies.lastIndex, it.index, minEnergies) }
    }

    override fun BufferedImage.setSeam(x: Int, y: Int, minEnergies: List<List<Double>>) {
        setRGB(x, y, Color.red.rgb)
        if (x - 1 < 0) return
        setSeam(x - 1, minEnergiesByRow(minEnergies[x - 1], y).index, minEnergies)
    }

    override fun BufferedImage.energies(width: Int, height: Int): MutableList<MutableList<Double>> =
        MutableList(width) { x -> MutableList(height) { y -> energyOfPixelAt(x, y) } }
}