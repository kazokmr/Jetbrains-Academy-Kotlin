package seamcarving

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

class EnergyPicture(args: Array<String>) : ImageModifier(args) {
    override fun draw() {
        ImageIO.write(energyPicture(args[1]), "png", File(args[3]))
    }

    private val maxEnergy =
        { energies: MutableList<MutableList<Double>> -> energies.flatMap { it.asIterable() }.maxOf { it } }

    private val energyPicture = { fileName: String ->
        ImageIO.read(File(fileName)).apply {
            val energies = energies(width, height)
            val maxEnergy = maxEnergy(energies)
            energies.forEachIndexed { y, row ->
                row.forEachIndexed { x, energy ->
                    setIntensity(x, y, energy, maxEnergy)
                }
            }
        }
    }

    private fun BufferedImage.setIntensity(x: Int, y: Int, energy: Double, maxEnergy: Double) =
        let { (255.0 * energy / maxEnergy).toInt() }.let { setRGB(x, y, Color(it, it, it).rgb) }
}