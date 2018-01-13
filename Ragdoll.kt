import java.awt.Graphics2D
import java.awt.Point

abstract class Ragdoll {
    abstract protected val root: Sprite

    enum class Type {
        PERSON, TREE, DOG
    }

    fun getSelectedSprite(p: Point): Sprite? {
        return root.getSelectedSprite(p)
    }

    fun draw(g: Graphics2D) {
        this.root.draw(g)
    }
}
