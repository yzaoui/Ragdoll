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

    val localTransform: AffineTransform
        get() = AffineTransform(this.transform)

    init {
        if (parent != null) {
            this.parent = parent
            parent.addChild(this)
        }
    }

    fun addChild(child: Sprite) {
        this.children.add(child)
        child.parent = this
    }

    private fun hasSlave(): Boolean {
        return this.slave != null
    }

    fun setSlave(slave: Sprite) {
        this.slave = slave
    }

    fun setPivotAround(x: Int, y: Int, maxAngle: Double) {
        this.canPivot = true
        this.pivotX = x
        this.pivotY = y
        this.pivotMaxAngle = Math.toRadians(maxAngle)
        this.pivotAngle = 0.0
    }

    fun setPivotAround(x: Int, y: Int, maxAngle: Double, initialAngleWithinRange: Double) {
        setPivotAround(x, y, maxAngle)
        this.pivot(Math.toRadians(initialAngleWithinRange))
    }

    fun setPivotAround(x: Int, y: Int, maxAngle: Double, initialAngleWithinRange: Double, initialAbsoluteAngle: Double) {
        setPivotAround(x, y, maxAngle, initialAngleWithinRange)
        this.initialAbsoluteAngle = Math.toRadians(initialAbsoluteAngle)
        this.transform.rotate(this.initialAbsoluteAngle, this.pivotX.toDouble(), this.pivotY.toDouble())
    }

    fun setCanScale(b: Boolean) {
        this.canScale = b
    }

    fun setCanTranslate(b: Boolean) {
        this.canTranslate = b
    }

    abstract fun containsPoint(p: Point2D): Boolean

    fun pivot(newAngle: Double) {
        if (Math.abs(newAngle + pivotAngle) <= this.pivotMaxAngle) {
            if (selectedInverseTransform != null) {
                selectedInverseTransform!!.rotate(newAngle, pivotX.toDouble(), pivotY.toDouble())
            }

            this.transform.rotate(newAngle, pivotX.toDouble(), pivotY.toDouble())

            if (this.pivotAngle + newAngle >= Math.PI || this.pivotAngle + newAngle <= -Math.PI) {
                this.pivotAngle = 0.0
            }

            this.pivotAngle += newAngle
        }
    }

    protected abstract fun scaleShapeY(scale: Double)

    protected abstract fun startScale()

    protected fun translateChildren(dy: Int) {
        for (child in children) {
            child.transform.rotate(-child.pivotAngle - child.initialAbsoluteAngle)
            child.transform.translate(0.0, dy.toDouble())
            child.transform.rotate(child.pivotAngle + child.initialAbsoluteAngle)
        }
    }

    protected fun scaleSlave(scale: Double) {
        if (this.hasSlave()) {
            this.slave!!.scaleShapeY(scale)
        }
    }

    fun drag(p: Point2D) {
        try {
            val localPNonRotated = this.selectedInverseTransform!!.inverseTransform(p, null)

            if (this.canPivot) {
                val transformedX = pivotX - localPNonRotated.x.toInt()
                val transformedY = pivotY - localPNonRotated.y.toInt()

                val newAngle = -Math.atan2(transformedX.toDouble(), transformedY.toDouble())
                this.pivot(newAngle)
                if (canScale) {
                    val scale = transformedY.toDouble() / lastScalePointY
                    this.scaleShapeY(scale)
                }
            } else if (this.canTranslate) {
                val translateX = localPNonRotated.x - lastPoint!!.x
                val translateY = localPNonRotated.y - lastPoint!!.y
                this.transform.translate(translateX, translateY)
                lastPoint!!.setLocation(localPNonRotated)
            }
        } catch (e: NoninvertibleTransformException) {
            e.printStackTrace()
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
        if (this.containsPoint(p) && this.canSelect) {
            selectedInverseTransform = AffineTransform(this.fullTransform)

            try {
                val localP = selectedInverseTransform!!.inverseTransform(p, null)
                if (this.canPivot) {
                    selectedInverseTransform!!.rotate(-Math.atan2(pivotX - localP.x, pivotY - localP.y), pivotX.toDouble(), pivotY.toDouble())

                    if (this.canScale) {
                        lastScalePointY = localP.y.toInt() - pivotY
                        this.startScale()
                        if (this.hasSlave()) {
                            this.slave!!.startScale()
                        }
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
        this.transform.concatenate(t)
    }

    fun draw(g: Graphics2D) {
        g.setRenderingHints(RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON))

        val oldTransform = g.transform

        g.transform = this.fullTransform

        this.drawSprite(g)

        g.transform = oldTransform

        for (child in children) {
            child.draw(g)
        }
    }

    fun setColor(color: Color) {
        this.fillColor = color
    }

    fun setSelectable(bool: Boolean) {
        this.canSelect = bool
    }

    protected abstract fun drawSprite(g: Graphics2D)
}
