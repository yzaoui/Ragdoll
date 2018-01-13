import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.event.MouseMotionAdapter
import javax.swing.JPanel
import javax.swing.Timer

class Canvas : JPanel() {
    private var root: Ragdoll = Person()
    private var currentType: Ragdoll.Type
    private var selectedSprite: Sprite? = null

    init {
        this.addMouseListener(object : MouseAdapter() {
            override fun mousePressed(e: MouseEvent) {
                selectedSprite = root.getSelectedSprite(e.point)
            }

            override fun mouseReleased(e: MouseEvent) {
                selectedSprite?.let { repaint() }
                selectedSprite = null
            }
        })
        this.addMouseMotionListener(object : MouseMotionAdapter() {
            override fun mouseDragged(e: MouseEvent) {
                selectedSprite?.drag(e.point).also {
                    repaint()
                }
            }
        })
        currentType = Ragdoll.Type.PERSON

        reset()
        repaint()
        revalidate()
    }

    fun reset() {
        root = when (currentType) {
            Ragdoll.Type.PERSON -> Person()
            Ragdoll.Type.TREE -> Tree()
            Ragdoll.Type.DOG -> Dog()
        }

        Timer(20) { repaint() }.apply {
            isRepeats = false
            start()
        }
    }

    override fun paintComponent(g: Graphics) {
        g.apply {
            color = Color.white
            fillRect(0, 0, width, height)
            color = Color.black
        }

        root.draw(g as Graphics2D)
    }

    fun setRagdoll(type: Ragdoll.Type) {
        if (currentType != type) {
            currentType = type
            reset()
        }
    }
}
