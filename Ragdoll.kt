import java.awt.*

abstract class Ragdoll {
    protected var root: Sprite? = null

    enum class Type {
        PERSON, TREE, DOG
    }

    fun getSelectedSprite(p: Point): Sprite? {
        return this.root!!.getSelectedSprite(p)
    }

    fun draw(g: Graphics2D) {
        this.root!!.draw(g)
    }
}
