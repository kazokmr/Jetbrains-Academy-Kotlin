package seamcarving

import java.awt.Color.RED
import java.awt.image.BufferedImage
import java.awt.image.BufferedImage.TYPE_INT_RGB
import java.io.File
import javax.imageio.ImageIO

class LineCrossImage(args: Array<String>) : ImageModifier(args) {
    override fun draw() {
        println("Enter rectangle width:")
        val width = readLine()!!.toInt()
        println("Enter rectangle height:")
        val height = readLine()!!.toInt()
        println("Enter output image name:")
        val imageName = readLine()!!
        ImageIO.write(crossingLines(width, height), "png", File(imageName))
    }

    private val crossingLines = { width: Int, height: Int ->
        BufferedImage(width, height, TYPE_INT_RGB).apply {
            createGraphics().apply {
                color = RED
                drawLine(0, 0, width - 1, height - 1)
                drawLine(0, height - 1, width - 1, 0)
            }
        }
    }
}