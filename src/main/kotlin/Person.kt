import java.awt.geom.AffineTransform

class Person : Ragdoll() {
    override val root: Sprite

    init {
        val torsoW = 60
        val torsoH = 130
        val torso = SpriteRectangle(torsoW, torsoH, null).apply {
            transform(AffineTransform.getTranslateInstance(370.0, 200.0))
            setCanTranslate(true)
        }

        root = torso

        /* HEAD */
        val headW = 40
        val headH = 60
        val head = SpriteEllipse(headW, headH, torso).apply {
            transform(AffineTransform.getTranslateInstance(((torsoW - headW) / 2).toDouble(), 0.0))
            setPivotAround(headW / 2, 0, 50.0, 0.0, 180.0)
        }

        /* UPPER LEFT LEG */
        val ulLegW = 20
        val ulLegH = 90
        val ulLeg = SpriteEllipse(ulLegW, ulLegH, torso).apply {
            transform(AffineTransform.getTranslateInstance(0.0, torsoH.toDouble()))
            setPivotAround(ulLegW / 2, 0, 90.0)
            setCanScale(true)
        }

        /* LOWER LEFT LEG */
        val llLegW = 20
        val llLegH = 60
        val llLeg = SpriteEllipse(llLegW, llLegH, ulLeg).apply {
            transform(AffineTransform.getTranslateInstance(0.0, ulLegH.toDouble()))
            setPivotAround(llLegW / 2, 0, 90.0)
            setCanScale(true)
        }.also {
            ulLeg.setSlave(it)
        }

        /* LEFT FOOT */
        val lFootW = 18
        val lFootH = 40
        val lFoot = SpriteEllipse(lFootW, lFootH, llLeg).apply {
            transform(AffineTransform.getTranslateInstance(0.0, llLegH.toDouble()))
            setPivotAround(lFootW / 2, 0, 90.0, 0.0, 90.0)
        }

        /* UPPER RIGHT LEG */
        val urLeg = SpriteEllipse(ulLegW, ulLegH, torso).apply {
            transform(AffineTransform.getTranslateInstance((torsoW - ulLegW).toDouble(), torsoH.toDouble()))
            setPivotAround(ulLegW / 2, 0, 90.0)
            setCanScale(true)
        }

        /* LOWER RIGHT LEG */
        val lrLeg = SpriteEllipse(llLegW, llLegH, urLeg).apply {
            transform(AffineTransform.getTranslateInstance(0.0, ulLegH.toDouble()))
            setPivotAround(llLegW / 2, 0, 90.0)
            setCanScale(true)
        }.also {
            urLeg.setSlave(it)
        }

        /* RIGHT FOOT */
        val rFoot = SpriteEllipse(lFootW, lFootH, lrLeg).apply {
            transform(AffineTransform.getTranslateInstance(0.0, llLegH.toDouble()))
            setPivotAround(lFootW / 2, 0, 90.0, 0.0, -90.0)
        }

        /* UPPER LEFT ARM */
        val ulArmW = 20
        val ulArmH = 70
        val ulArm = SpriteEllipse(ulArmW, ulArmH, torso).apply {
            transform(AffineTransform.getTranslateInstance((-ulArmW / 2).toDouble(), 0.0))
            setPivotAround(ulArmW / 2, 0, 360.0, 10.0)
        }

        /* LOWER LEFT ARM */
        val llArmW = 20
        val llArmH = 70
        val llArm = SpriteEllipse(llArmW, llArmH, ulArm).apply {
            transform(AffineTransform.getTranslateInstance(0.0, ulArmH.toDouble()))
            setPivotAround(llArmW / 2, 0, 135.0, -5.0)
        }

        /* LEFT HAND */
        val lHandW = 20
        val lHandH = 30
        val lHand = SpriteEllipse(lHandW, lHandH, llArm).apply {
            transform(AffineTransform.getTranslateInstance(0.0, llArmH.toDouble()))
            setPivotAround(lHandW / 2, 0, 35.0)
        }

        /* UPPER RIGHT ARM */
        val urArm = SpriteEllipse(ulArmW, ulArmH, torso).apply {
            transform(AffineTransform.getTranslateInstance((torsoW - ulArmW / 2).toDouble(), 0.0))
            setPivotAround(ulArmW / 2, 0, 360.0, -10.0)
        }

        /* LOWER RIGHT ARM */
        val lrArm = SpriteEllipse(llArmW, llArmH, urArm).apply {
            transform(AffineTransform.getTranslateInstance(0.0, ulArmH.toDouble()))
            setPivotAround(llArmW / 2, 0, 135.0, 5.0)
        }

        /* RIGHT HAND */
        val rHand = SpriteEllipse(lHandW, lHandH, lrArm).apply {
            transform(AffineTransform.getTranslateInstance(0.0, llArmH.toDouble()))
            setPivotAround(lHandW / 2, 0, 35.0)
        }
    }
}
