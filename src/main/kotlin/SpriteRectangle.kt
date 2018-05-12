import java.awt.*
import java.awt.geom.AffineTransform
import java.awt.geom.NoninvertibleTransformException
import java.awt.geom.Point2D
import java.awt.geom.RoundRectangle2D

class SpriteRectangle(width: Int, height: Int, parent: Sprite?) : Sprite(parent) {
    private val shape: RoundRectangle2D = RoundRectangle2D.Double(0.0, 0.0, width.toDouble(), height.toDouble(), 15.0, 15.0)

    override fun containsPoint(p: Point2D): Boolean {
        val newPoint = fullTransform.createInverse().transform(p, null)

        return shape.contains(newPoint)
    }

    override fun scaleShapeY(scale: Double) {
        //Should never reach here
    }

    override fun startScale() {
        //Should never reach here
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
