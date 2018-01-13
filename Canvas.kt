import javax.swing.*
import java.awt.*
import java.awt.event.ActionEvent
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.event.MouseMotionAdapter

class Canvas : JPanel() {
    private var root: Ragdoll? = null
    private var currentType: Ragdoll.Type? = null
    private var selectedSprite: Sprite? = null

    init {
        this.addMouseListener(object : MouseAdapter() {
            override fun mousePressed(e: MouseEvent?) {
                selectedSprite = root!!.getSelectedSprite(e!!.point)
            }

            override fun mouseReleased(e: MouseEvent?) {
                if (selectedSprite != null) {
                    selectedSprite = null
                    repaint()
                }
            }
        })
        this.addMouseMotionListener(object : MouseMotionAdapter() {
            override fun mouseDragged(e: MouseEvent?) {
                if (selectedSprite != null) {
                    selectedSprite!!.drag(e!!.point)
                    repaint()
                }
            }
        })
        this.currentType = Ragdoll.Type.PERSON

        this.reset()
        this.repaint()
        this.revalidate()
    }

    fun reset() {
        when (currentType) {
            Ragdoll.Type.PERSON -> root = Person()
            Ragdoll.Type.TREE -> root = Tree()
            Ragdoll.Type.DOG -> root = Dog()
        }

        val timer = Timer(20) { e: ActionEvent -> this@Canvas.repaint() }
        timer.isRepeats = false
        timer.start()
    }

    override fun paintComponent(g: Graphics) {
        g.color = Color.white
        g.fillRect(0, 0, this.width, this.height)
        g.color = Color.black

        root!!.draw(g as Graphics2D)
    }

    fun setRagdoll(type: Ragdoll.Type) {
        if (this.currentType != type) {
            this.currentType = type
            this.reset()
        }
    }
}
