import java.awt.*
import java.awt.geom.AffineTransform
import java.util.Random

class Tree : Ragdoll() {
    override val root: Sprite

    init {
        val brown = Color(140, 70, 20)
        val forestGreen = Color(34, 139, 34)

        val trunkW = 60
        val trunkH = 40
        val trunk = SpriteRectangle(trunkW, trunkH, null).apply {
            transform(AffineTransform.getTranslateInstance(370.0, 480.0))
            setCanTranslate(true)
            setColor(brown)
        }

        root = trunk

        var branchW = 500
        val branchH = 20
        val rand = Random()
        var parentBranch: Sprite = SpriteRectangle(branchW, branchH, trunk).apply {
            transform(AffineTransform.getTranslateInstance(((trunkW - branchW) / 2).toDouble(), (-branchH).toDouble()))
            setPivotAround(branchW / 2, branchH / 2, 15.0, rand.nextDouble() * 20 - 10)
            setColor(forestGreen)
        }

        val shrinkFactor = 22
        for (i in 0..20) {
            branchW -= shrinkFactor
            parentBranch = SpriteRectangle(branchW, branchH, parentBranch).apply {
                transform(AffineTransform.getTranslateInstance((shrinkFactor / 2).toDouble(), (-branchH).toDouble()))
                setPivotAround(branchW / 2, branchH / 2, 15.0, rand.nextDouble() * 20 - 10)
                setColor(forestGreen)
            }
        }
    }
}
