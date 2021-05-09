package seamcarving

import java.awt.Color
import java.io.File
import javax.imageio.ImageIO

class NegativePicture(args: Array<String>) : ImageModifier(args) {
    override fun draw() {
        ImageIO.write(negativePicture(args[1]), "png", File(args[3]))
    }

    private fun Color.negativeRGB() = Color(255 - red, 255 - green, 255 - blue).rgb

    private val negativePicture = { fileName: String ->
        ImageIO.read(File(fileName)).apply {
            (0 until width).forEach { x ->
                (0 until height).forEach { y ->
                    setRGB(x, y, Color(getRGB(x, y)).negativeRGB())
                }
            }
        }
    }
}