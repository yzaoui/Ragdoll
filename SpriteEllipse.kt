import java.awt.*
import java.awt.geom.AffineTransform
import java.awt.geom.Ellipse2D
import java.awt.geom.NoninvertibleTransformException
import java.awt.geom.Point2D

class SpriteEllipse(private val width: Int, private var height: Int, parent: Sprite) : Sprite(parent) {
    private var shape: Ellipse2D? = null
    private var heightSinceClick: Int = 0

    init {
        this.shape = Ellipse2D.Double(0.0, 0.0, width.toDouble(), height.toDouble())
    }

    override fun containsPoint(p: Point2D): Boolean {
        val fullTransform = this.fullTransform
        var inverseTransform: AffineTransform? = null

        try {
            inverseTransform = fullTransform.createInverse()
        } catch (e: NoninvertibleTransformException) {
            e.printStackTrace()
        }

        val newPoint = inverseTransform!!.transform(p, null)
        return shape!!.contains(newPoint)
    }

    override fun startScale() {
        this.heightSinceClick = height
    }

    override fun scaleShapeY(scale: Double) {
        val newHeight = (heightSinceClick * scale).toInt()
        if (newHeight >= minHeight && newHeight <= maxHeight) {
            this.shape = Ellipse2D.Double(0.0, 0.0, width.toDouble(), newHeight.toDouble())
            this.translateChildren(newHeight - height)
            height = (heightSinceClick * scale).toInt()
            this.scaleSlave(scale)
        }
    }

    override fun drawSprite(g: Graphics2D) {
        g.color = this.fillColor
        g.fill(shape)
        g.color = Color.black
        g.draw(shape)
    }

    companion object {
        private val minHeight = 30
        private val maxHeight = 200
    }
}
