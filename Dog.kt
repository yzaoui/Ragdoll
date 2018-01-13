import java.awt.*
import java.awt.geom.AffineTransform

class Dog : Ragdoll() {
    private val brown = Color(140, 70, 20)

    init {
        val torsoW = 240
        val torsoH = 80
        val torso = SpriteRectangle(torsoW, torsoH, null)
        torso.transform(AffineTransform.getTranslateInstance(300.0, 240.0))
        torso.setCanTranslate(true)

        root = torso

        val neckW = 128
        val neckH = 32
        val neck = SpriteEllipse(neckW, neckH, torso)
        neck.transform(AffineTransform.getTranslateInstance(-30.0, (-neckH).toDouble()))
        neck.setPivotAround(neckW / 2, neckH, 20.0)

        val headW = 190
        val headH = 84
        val head = SpriteEllipse(headW, headH, neck)
        head.transform(AffineTransform.getTranslateInstance((-headW / 2).toDouble(), (-headH + 10).toDouble()))
        head.setPivotAround(headW / 2 + 5, headH - 5, 20.0)

        val noseW = 36
        val noseH = 20
        val nose = SpriteEllipse(noseW, noseH, head)
        nose.transform(AffineTransform.getTranslateInstance(5.0, ((headH - noseH) / 2).toDouble()))
        nose.setColor(Color.black)
        nose.setSelectable(false)

        val eyeR = 16
        val leftEye = SpriteEllipse(eyeR, eyeR, head)
        leftEye.transform(AffineTransform.getTranslateInstance(140.0, (headH / 2 - 10).toDouble()))
        leftEye.setColor(brown)
        leftEye.setSelectable(false)

        val eyepatchW = 82
        val eyepatchH = 38
        val eyepatch = SpriteEllipse(eyepatchW, eyepatchH, head)
        eyepatch.transform(AffineTransform.getTranslateInstance(50.0, 0.0))
        eyepatch.setColor(Color.black)
        eyepatch.setSelectable(false)

        val rightEye = SpriteEllipse(eyeR, eyeR, eyepatch)
        rightEye.transform(AffineTransform.getTranslateInstance((eyepatchW / 2 - eyeR + 6).toDouble(), 14.0))
        rightEye.setColor(brown)
        rightEye.setSelectable(false)

        val earW = 20
        val earH = 64
        val leftEar = SpriteEllipse(earW, earH, head)
        leftEar.transform(AffineTransform.getTranslateInstance((headW / 2 + 10).toDouble(), 0.0))
        leftEar.setColor(Color.black)
        leftEar.setPivotAround(earW / 2, 0, 360.0, 0.0, 180.0)
        leftEar.setCanScale(true)

        val rightEar = SpriteEllipse(earW, earH, head)
        rightEar.transform(AffineTransform.getTranslateInstance((headW - earW - 4).toDouble(), 20.0))
        rightEar.setColor(Color.black)
        rightEar.setPivotAround(earW / 2, 0, 360.0, 0.0, 180.0)
        rightEar.setCanScale(true)

        leftEar.setSlave(rightEar)

        val tailW = 18
        val tailH = 54
        val tail = SpriteEllipse(tailW, tailH, torso)
        tail.transform(AffineTransform.getTranslateInstance((torsoW - tailW).toDouble(), 0.0))
        tail.setPivotAround(tailW / 2, 0, 75.0, -60.0, -120.0)
        tail.setCanScale(true)

        val ulegW = 34
        val ulegH = 128
        val llegW = 24
        val llegH = 110
        val footW = 24
        val footH = 50

        val uleg1 = SpriteEllipse(ulegW, ulegH, torso)
        uleg1.transform(AffineTransform.getTranslateInstance(-5.0, 0.0))
        uleg1.setPivotAround(ulegW / 2, 0, 45.0)

        val lleg1 = SpriteRectangle(llegW, llegH, uleg1)
        lleg1.transform(AffineTransform.getTranslateInstance(((ulegW - llegW) / 2).toDouble(), (ulegH - 16).toDouble()))
        lleg1.setPivotAround(llegW / 2, 0, 45.0)

        val foot1 = SpriteEllipse(footW, footH, lleg1)
        foot1.transform(AffineTransform.getTranslateInstance((llegW - footW / 2).toDouble(), llegH.toDouble()))
        foot1.setPivotAround(llegW / 2, 0, 45.0, 0.0, 90.0)

        val uleg2 = SpriteEllipse(ulegW, ulegH, torso)
        uleg2.transform(AffineTransform.getTranslateInstance(50.0, 0.0))
        uleg2.setPivotAround(ulegW / 2, 0, 45.0)

        val lleg2 = SpriteRectangle(llegW, llegH, uleg2)
        lleg2.transform(AffineTransform.getTranslateInstance(((ulegW - llegW) / 2).toDouble(), (ulegH - 16).toDouble()))
        lleg2.setPivotAround(llegW / 2, 0, 45.0)

        val foot2 = SpriteEllipse(footW, footH, lleg2)
        foot2.transform(AffineTransform.getTranslateInstance((llegW - footW / 2).toDouble(), llegH.toDouble()))
        foot2.setPivotAround(llegW / 2, 0, 45.0, 0.0, 90.0)

        val backR = 110
        val backULeg = SpriteEllipse(backR, backR, torso)
        backULeg.transform(AffineTransform.getTranslateInstance((torsoW - backR + 20).toDouble(), 20.0))
        backULeg.setPivotAround(backR / 2, backR / 2, 360.0)

        val leg3 = SpriteRectangle(llegW, llegH, backULeg)
        leg3.transform(AffineTransform.getTranslateInstance((backR / 2 - llegW - 14).toDouble(), (backR / 2 + 14).toDouble()))
        leg3.setPivotAround(llegW / 2, 0, 45.0)

        val foot3 = SpriteEllipse(footW, footH, leg3)
        foot3.transform(AffineTransform.getTranslateInstance(((llegW - footW) / 2).toDouble(), (llegH - 10).toDouble()))
        foot3.setPivotAround(llegW / 2, 0, 45.0)

        val leg4 = SpriteRectangle(llegW, llegH, backULeg)
        leg4.transform(AffineTransform.getTranslateInstance((backR / 2 + llegW - 14).toDouble(), (backR / 2 + 14).toDouble()))
        leg4.setPivotAround(llegW / 2, 0, 45.0)

        val foot4 = SpriteEllipse(footW, footH, leg4)
        foot4.transform(AffineTransform.getTranslateInstance(((llegW - footW) / 2).toDouble(), (llegH - 10).toDouble()))
        foot4.setPivotAround(llegW / 2, 0, 45.0)
    }
}
