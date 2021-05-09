package seamcarving

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

open class VerticalSeam(args: Array<String>) : ImageModifier(args) {

    override fun draw() {
        ImageIO.write(findSeam(args[1]), "png", File(args[3]))
    }

    val minEnergiesByRow = { energies: List<Double>, n: Int ->
        energies.withIndex().filter { it.index in n - 1..n + 1 }.minByOrNull { it.value }!!
    }

    open val findSeam = { fileName: String -> ImageIO.read(File(fileName)).apply { seam() } }

    open fun BufferedImage.seam() {
        val minEnergies = energies(width, height)
        calcMinEnergyAt(minEnergies)
        minEnergies.last()
            .withIndex()
            .filter { it.value == minEnergies.last().minOrNull() }
            .forEach { setSeam(it.index, minEnergies.lastIndex, minEnergies) }
    }

    fun calcMinEnergyAt(minEnergies: MutableList<MutableList<Double>>) {
        minEnergies[0][0]
        (1..minEnergies.lastIndex).forEach { a ->
            minEnergies[a].indices.forEach { b ->
                minEnergies[a][b] = minEnergiesByRow(minEnergies[a - 1], b).value
            }
        }
    }

    open fun BufferedImage.setSeam(x: Int, y: Int, minEnergies: List<List<Double>>) {
        setRGB(x, y, Color.red.rgb)
        if (y - 1 < 0) return
        setSeam(minEnergiesByRow(minEnergies[y - 1], x).index, y - 1, minEnergies)
    }
}