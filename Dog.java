import java.awt.*;
import java.awt.geom.AffineTransform;

public class Dog extends Ragdoll {
    private Color brown = new Color(140, 70, 20);

    public Dog() {
        int torsoW = 240, torsoH = 80;
        Sprite torso = new SpriteRectangle(torsoW, torsoH, null);
        torso.transform(AffineTransform.getTranslateInstance(300, 240));
        torso.setCanTranslate(true);

        root = torso;

        int neckW = 128, neckH = 32;
        Sprite neck = new SpriteEllipse(neckW, neckH, torso);
        neck.transform(AffineTransform.getTranslateInstance(-30, -neckH));
        neck.setPivotAround(neckW / 2, neckH, 20);

        int headW = 190, headH = 84;
        Sprite head = new SpriteEllipse(headW, headH, neck);
        head.transform(AffineTransform.getTranslateInstance(-headW / 2, -headH + 10));
        head.setPivotAround(headW / 2 + 5, headH - 5, 20);

        int noseW = 36, noseH = 20;
        Sprite nose = new SpriteEllipse(noseW, noseH, head);
        nose.transform(AffineTransform.getTranslateInstance(5, (headH - noseH) / 2));
        nose.setColor(Color.black);
        nose.setSelectable(false);

        int eyeR = 16;
        Sprite leftEye = new SpriteEllipse(eyeR, eyeR, head);
        leftEye.transform(AffineTransform.getTranslateInstance(140, headH / 2 - 10));
        leftEye.setColor(brown);
        leftEye.setSelectable(false);

        int eyepatchW = 82, eyepatchH = 38;
        Sprite eyepatch = new SpriteEllipse(eyepatchW, eyepatchH, head);
        eyepatch.transform(AffineTransform.getTranslateInstance(50, 0));
        eyepatch.setColor(Color.black);
        eyepatch.setSelectable(false);

        Sprite rightEye = new SpriteEllipse(eyeR, eyeR, eyepatch);
        rightEye.transform(AffineTransform.getTranslateInstance(eyepatchW / 2 - eyeR + 6, 14));
        rightEye.setColor(brown);
        rightEye.setSelectable(false);

        int earW = 20, earH = 64;
        Sprite leftEar = new SpriteEllipse(earW, earH, head);
        leftEar.transform(AffineTransform.getTranslateInstance(headW / 2 + 10, 0));
        leftEar.setColor(Color.black);
        leftEar.setPivotAround(earW / 2, 0, 360, 0, 180);
        leftEar.setCanScale(true);

        Sprite rightEar = new SpriteEllipse(earW, earH, head);
        rightEar.transform(AffineTransform.getTranslateInstance(headW - earW - 4, 20));
        rightEar.setColor(Color.black);
        rightEar.setPivotAround(earW / 2, 0, 360, 0, 180);
        rightEar.setCanScale(true);

        leftEar.setSlave(rightEar);

        int tailW = 18, tailH = 54;
        Sprite tail = new SpriteEllipse(tailW, tailH, torso);
        tail.transform(AffineTransform.getTranslateInstance(torsoW - tailW, 0));
        tail.setPivotAround(tailW / 2, 0, 75, -60, -120);
        tail.setCanScale(true);

        int ulegW = 34, ulegH = 128;
        int llegW = 24, llegH = 110;
        int footW = 24, footH = 50;

        Sprite uleg1 = new SpriteEllipse(ulegW, ulegH, torso);
        uleg1.transform(AffineTransform.getTranslateInstance(-5, 0));
        uleg1.setPivotAround(ulegW / 2, 0, 45);

        Sprite lleg1 = new SpriteRectangle(llegW, llegH, uleg1);
        lleg1.transform(AffineTransform.getTranslateInstance((ulegW - llegW) / 2, ulegH - 16));
        lleg1.setPivotAround(llegW / 2, 0, 45);

        Sprite foot1 = new SpriteEllipse(footW, footH, lleg1);
        foot1.transform(AffineTransform.getTranslateInstance(llegW - footW / 2, llegH));
        foot1.setPivotAround(llegW / 2, 0, 45, 0, 90);

        Sprite uleg2 = new SpriteEllipse(ulegW, ulegH, torso);
        uleg2.transform(AffineTransform.getTranslateInstance(50, 0));
        uleg2.setPivotAround(ulegW / 2, 0, 45);

        Sprite lleg2 = new SpriteRectangle(llegW, llegH, uleg2);
        lleg2.transform(AffineTransform.getTranslateInstance((ulegW - llegW) / 2, ulegH - 16));
        lleg2.setPivotAround(llegW / 2, 0, 45);

        Sprite foot2 = new SpriteEllipse(footW, footH, lleg2);
        foot2.transform(AffineTransform.getTranslateInstance(llegW - footW / 2, llegH));
        foot2.setPivotAround(llegW / 2, 0, 45, 0, 90);

        int backR = 110;
        Sprite backULeg = new SpriteEllipse(backR, backR, torso);
        backULeg.transform(AffineTransform.getTranslateInstance(torsoW - backR + 20, 20));
        backULeg.setPivotAround(backR / 2, backR / 2, 360);

        Sprite leg3 = new SpriteRectangle(llegW, llegH, backULeg);
        leg3.transform(AffineTransform.getTranslateInstance(backR / 2 - llegW - 14, backR / 2 + 14));
        leg3.setPivotAround(llegW / 2, 0, 45);

        Sprite foot3 = new SpriteEllipse(footW, footH, leg3);
        foot3.transform(AffineTransform.getTranslateInstance((llegW - footW) / 2, llegH - 10));
        foot3.setPivotAround(llegW / 2, 0, 45);

        Sprite leg4 = new SpriteRectangle(llegW, llegH, backULeg);
        leg4.transform(AffineTransform.getTranslateInstance(backR / 2 + llegW - 14, backR / 2 + 14));
        leg4.setPivotAround(llegW / 2, 0, 45);

        Sprite foot4 = new SpriteEllipse(footW, footH, leg4);
        foot4.transform(AffineTransform.getTranslateInstance((llegW - footW) / 2, llegH - 10));
        foot4.setPivotAround(llegW / 2, 0, 45);
    }
}
