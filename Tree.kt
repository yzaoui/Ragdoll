import java.awt.*
import java.awt.geom.AffineTransform
import java.util.Random

class Tree : Ragdoll() {
    init {
        val brown = Color(140, 70, 20)
        val forestGreen = Color(34, 139, 34)

        val trunkW = 60
        val trunkH = 40
        val trunk = SpriteRectangle(trunkW, trunkH, null!!)
        trunk.transform(AffineTransform.getTranslateInstance(370.0, 480.0))
        trunk.setCanTranslate(true)
        trunk.setColor(brown)

        var branchW = 500
        val branchH = 20
        var parentBranch: Sprite = SpriteRectangle(branchW, branchH, trunk)
        parentBranch.transform(AffineTransform.getTranslateInstance(((trunkW - branchW) / 2).toDouble(), (-branchH).toDouble()))
        val rand = Random()
        parentBranch.setPivotAround(branchW / 2, branchH / 2, 15.0, rand.nextDouble() * 20 - 10)
        parentBranch.setColor(forestGreen)

        val shrinkFactor = 22
        for (i in 0..20) {
            branchW -= shrinkFactor
            val branch = SpriteRectangle(branchW, branchH, parentBranch)
            parentBranch = branch
            branch.transform(AffineTransform.getTranslateInstance((shrinkFactor / 2).toDouble(), (-branchH).toDouble()))
            branch.setPivotAround(branchW / 2, branchH / 2, 15.0, rand.nextDouble() * 20 - 10)
            branch.setColor(forestGreen)
        }

        root = trunk
    }
}
