import java.awt.Color
import java.awt.Graphics2D
import java.awt.geom.Ellipse2D
import java.awt.geom.Point2D

class SpriteEllipse(private val width: Int, private var height: Int, parent: Sprite) : Sprite(parent) {

    companion object {
        private val minHeight = 30
        private val maxHeight = 200
    }

    private var shape: Ellipse2D
    private var heightSinceClick: Int = 0

    init {
        shape = Ellipse2D.Double(0.0, 0.0, width.toDouble(), height.toDouble())
    }

    override fun containsPoint(p: Point2D): Boolean {
        val newPoint = fullTransform.createInverse().transform(p, null)
        return shape.contains(newPoint)
    }

    override fun startScale() {
        heightSinceClick = height
    }

    override fun scaleShapeY(scale: Double) {
        val newHeight = (heightSinceClick * scale).toInt()
        if (newHeight in minHeight..maxHeight) {
            shape = Ellipse2D.Double(0.0, 0.0, width.toDouble(), newHeight.toDouble())
            translateChildren(newHeight - height)
            height = (heightSinceClick * scale).toInt()
            scaleSlave(scale)
        }
    }

    override fun drawSprite(g: Graphics2D) {
        g.apply {
            color = fillColor
            fill(shape)
            color = Color.black
            draw(shape)
        }
    }
}
