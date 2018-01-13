import java.awt.geom.AffineTransform

class Person : Ragdoll() {
    init {
        val torsoW = 60
        val torsoH = 130
        val torso = SpriteRectangle(torsoW, torsoH, null)
        torso.transform(AffineTransform.getTranslateInstance(370.0, 200.0))
        torso.setCanTranslate(true)

        root = torso

        /* HEAD */
        val headW = 40
        val headH = 60
        val head = SpriteEllipse(headW, headH, torso)
        head.transform(AffineTransform.getTranslateInstance(((torsoW - headW) / 2).toDouble(), 0.0))
        head.setPivotAround(headW / 2, 0, 50.0, 0.0, 180.0)

        /* UPPER LEFT LEG */
        val ulLegW = 20
        val ulLegH = 90
        val ulLeg = SpriteEllipse(ulLegW, ulLegH, torso)
        ulLeg.transform(AffineTransform.getTranslateInstance(0.0, torsoH.toDouble()))
        ulLeg.setPivotAround(ulLegW / 2, 0, 90.0)
        ulLeg.setCanScale(true)

        /* LOWER LEFT LEG */
        val llLegW = 20
        val llLegH = 60
        val llLeg = SpriteEllipse(llLegW, llLegH, ulLeg)
        llLeg.transform(AffineTransform.getTranslateInstance(0.0, ulLegH.toDouble()))
        llLeg.setPivotAround(llLegW / 2, 0, 90.0)
        llLeg.setCanScale(true)
        ulLeg.setSlave(llLeg)

        /* LEFT FOOT */
        val lFootW = 18
        val lFootH = 40
        val lFoot = SpriteEllipse(lFootW, lFootH, llLeg)
        lFoot.transform(AffineTransform.getTranslateInstance(0.0, llLegH.toDouble()))
        lFoot.setPivotAround(lFootW / 2, 0, 90.0, 0.0, 90.0)

        /* UPPER RIGHT LEG */
        val urLeg = SpriteEllipse(ulLegW, ulLegH, torso)
        urLeg.transform(AffineTransform.getTranslateInstance((torsoW - ulLegW).toDouble(), torsoH.toDouble()))
        urLeg.setPivotAround(ulLegW / 2, 0, 90.0)
        urLeg.setCanScale(true)

        /* LOWER RIGHT LEG */
        val lrLeg = SpriteEllipse(llLegW, llLegH, urLeg)
        lrLeg.transform(AffineTransform.getTranslateInstance(0.0, ulLegH.toDouble()))
        lrLeg.setPivotAround(llLegW / 2, 0, 90.0)
        lrLeg.setCanScale(true)
        urLeg.setSlave(lrLeg)

        /* RIGHT FOOT */
        val rFoot = SpriteEllipse(lFootW, lFootH, lrLeg)
        rFoot.transform(AffineTransform.getTranslateInstance(0.0, llLegH.toDouble()))
        rFoot.setPivotAround(lFootW / 2, 0, 90.0, 0.0, -90.0)

        /* UPPER LEFT ARM */
        val ulArmW = 20
        val ulArmH = 70
        val ulArm = SpriteEllipse(ulArmW, ulArmH, torso)
        ulArm.transform(AffineTransform.getTranslateInstance((-ulArmW / 2).toDouble(), 0.0))
        ulArm.setPivotAround(ulArmW / 2, 0, 360.0, 10.0)

        /* LOWER LEFT ARM */
        val llArmW = 20
        val llArmH = 70
        val llArm = SpriteEllipse(llArmW, llArmH, ulArm)
        llArm.transform(AffineTransform.getTranslateInstance(0.0, ulArmH.toDouble()))
        llArm.setPivotAround(llArmW / 2, 0, 135.0, -5.0)

        /* LEFT HAND */
        val lHandW = 20
        val lHandH = 30
        val lHand = SpriteEllipse(lHandW, lHandH, llArm)
        lHand.transform(AffineTransform.getTranslateInstance(0.0, llArmH.toDouble()))
        lHand.setPivotAround(lHandW / 2, 0, 35.0)

        /* UPPER RIGHT ARM */
        val urArm = SpriteEllipse(ulArmW, ulArmH, torso)
        urArm.transform(AffineTransform.getTranslateInstance((torsoW - ulArmW / 2).toDouble(), 0.0))
        urArm.setPivotAround(ulArmW / 2, 0, 360.0, -10.0)

        /* LOWER RIGHT ARM */
        val lrArm = SpriteEllipse(llArmW, llArmH, urArm)
        lrArm.transform(AffineTransform.getTranslateInstance(0.0, ulArmH.toDouble()))
        lrArm.setPivotAround(llArmW / 2, 0, 135.0, 5.0)

        /* RIGHT HAND */
        val rHand = SpriteEllipse(lHandW, lHandH, lrArm)
        rHand.transform(AffineTransform.getTranslateInstance(0.0, llArmH.toDouble()))
        rHand.setPivotAround(lHandW / 2, 0, 35.0)
    }
}
