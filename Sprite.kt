import java.awt.*
import java.awt.geom.AffineTransform
import java.awt.geom.NoninvertibleTransformException
import java.awt.geom.Point2D
import java.util.Vector

abstract class Sprite(parent: Sprite?) {
    var parent: Sprite? = null
    private var slave: Sprite? = null
    private val children = Vector<Sprite>()
    private val transform = AffineTransform()
    private var lastPoint: Point2D? = null
    protected var fillColor = Color.white

    private var initialAbsoluteAngle = 0.0

    private var canPivot = false
    private var pivotX: Int = 0
    private var pivotY: Int = 0
    private var pivotAngle: Double = 0.toDouble()
    private var pivotMaxAngle: Double = 0.toDouble()
    private var canScale = false
    private var lastScalePointY: Int = 0
    private var canTranslate = false
    private var canSelect = true

    private var selectedInverseTransform: AffineTransform? = null

    val fullTransform: AffineTransform
        get() {
            val returnTransform = AffineTransform()
            var curSprite: Sprite? = this

            while (curSprite != null) {
                returnTransform.preConcatenate(curSprite.localTransform)
                curSprite = curSprite.parent
            }

            return returnTransform
        }

    private val localTransform: AffineTransform
        get() = AffineTransform(this.transform)

    init {
        if (parent != null) {
            this.parent = parent
            parent.addChild(this)
        }
    }

    private fun addChild(child: Sprite) {
        this.children.add(child)
        child.parent = this
    }

    fun setSlave(slave: Sprite) {
        this.slave = slave
    }

    fun setPivotAround(x: Int, y: Int, maxAngle: Double) {
        canPivot = true
        pivotX = x
        pivotY = y
        pivotMaxAngle = Math.toRadians(maxAngle)
        pivotAngle = 0.0
    }

    fun setPivotAround(x: Int, y: Int, maxAngle: Double, initialAngleWithinRange: Double) {
        setPivotAround(x, y, maxAngle)
        pivot(Math.toRadians(initialAngleWithinRange))
    }

    fun setPivotAround(x: Int, y: Int, maxAngle: Double, initialAngleWithinRange: Double, initialAbsoluteAngle: Double) {
        setPivotAround(x, y, maxAngle, initialAngleWithinRange)
        this.initialAbsoluteAngle = Math.toRadians(initialAbsoluteAngle)
        this.transform.rotate(this.initialAbsoluteAngle, this.pivotX.toDouble(), this.pivotY.toDouble())
    }

    fun setCanScale(b: Boolean) {
        canScale = b
    }

    fun setCanTranslate(b: Boolean) {
        canTranslate = b
    }

    abstract fun containsPoint(p: Point2D): Boolean

    private fun pivot(newAngle: Double) {
        if (Math.abs(newAngle + pivotAngle) <= pivotMaxAngle) {
            selectedInverseTransform?.rotate(newAngle, pivotX.toDouble(), pivotY.toDouble())

            transform.rotate(newAngle, pivotX.toDouble(), pivotY.toDouble())

            if (pivotAngle + newAngle >= Math.PI || pivotAngle + newAngle <= -Math.PI) {
                pivotAngle = 0.0
            }

            pivotAngle += newAngle
        }
    }

    protected abstract fun scaleShapeY(scale: Double)

    protected abstract fun startScale()

    protected fun translateChildren(dy: Int) {
        for (child in children) {
            child.apply {
                transform.rotate(-pivotAngle - initialAbsoluteAngle)
                transform.translate(0.0, dy.toDouble())
                transform.rotate(pivotAngle + initialAbsoluteAngle)
            }
        }
    }

    protected fun scaleSlave(scale: Double) {
        slave?.scaleShapeY(scale)
    }

    fun drag(p: Point2D) {
        val localPNonRotated = selectedInverseTransform!!.inverseTransform(p, null)

        if (canPivot) {
            val transformedX = pivotX - localPNonRotated.x.toInt()
            val transformedY = pivotY - localPNonRotated.y.toInt()

            val newAngle = -Math.atan2(transformedX.toDouble(), transformedY.toDouble())
            this.pivot(newAngle)
            if (canScale) {
                val scale = transformedY.toDouble() / lastScalePointY
                this.scaleShapeY(scale)
            }
        } else if (canTranslate) {
            val lastPoint = lastPoint!!
            val translateX = localPNonRotated.x - lastPoint.x
            val translateY = localPNonRotated.y - lastPoint.y
            this.transform.translate(translateX, translateY)
            lastPoint.setLocation(localPNonRotated)
        }
    }

    fun getSelectedSprite(p: Point): Sprite? {
        //Check children first
        for (i in children.indices.reversed()) {
            val child = children[i]

            val selected = child.getSelectedSprite(p)
            if (selected != null) {
                return selected
            }
        }

        //Check self
        if (containsPoint(p) && canSelect) {
            selectedInverseTransform = AffineTransform(fullTransform)

            try {
                val localP = selectedInverseTransform!!.inverseTransform(p, null)
                if (this.canPivot) {
                    selectedInverseTransform!!.rotate(-Math.atan2(pivotX - localP.x, pivotY - localP.y), pivotX.toDouble(), pivotY.toDouble())

                    if (this.canScale) {
                        lastScalePointY = localP.y.toInt() - pivotY
                        this.startScale()
                        slave?.startScale()
                    }
                } else if (this.canTranslate) {
                    lastPoint = localP
                }
            } catch (e: NoninvertibleTransformException) {
                e.printStackTrace()
            }

            return this
        }

        //No hits
        return null
    }

    fun transform(t: AffineTransform) {
        transform.concatenate(t)
    }

    fun draw(g: Graphics2D) {
        g.setRenderingHints(RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON))

        val oldTransform = g.transform

        g.transform = fullTransform

        this.drawSprite(g)

        g.transform = oldTransform

        for (child in children) {
            child.draw(g)
        }
    }

    fun setColor(color: Color) {
        fillColor = color
    }

    fun setSelectable(bool: Boolean) {
        canSelect = bool
    }

    protected abstract fun drawSprite(g: Graphics2D)
}
