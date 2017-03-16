import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;

public class Canvas extends JPanel {
    private Sprite root;
    private Sprite selectedSprite = null;

    public Canvas() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                selectedSprite = root.getSelectedSprite(e.getPoint());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (selectedSprite != null) {
                    selectedSprite = null;
                    repaint();
                }
            }
        });
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (selectedSprite != null) {
                    selectedSprite.drag(e.getPoint());
                    repaint();
                }
            }
        });

        this.initCanvas();
        this.repaint();
        this.revalidate();
    }

    private void initCanvas() {
        int torsoW = 60, torsoH = 130;
        Sprite torso = new SpriteRectangle(torsoW, torsoH, null);
        torso.transform(AffineTransform.getTranslateInstance(370, 200));
        torso.setCanTranslate(true);

        root = torso;

        /* HEAD */
        int headW = 40, headH = 60;
        Sprite head = new SpriteEllipse(headW, headH, torso);
        head.transform(AffineTransform.getTranslateInstance((torsoW - headW) / 2, 0));
        head.setPivotAround(headW / 2, 0, 50, 0, 180);

        /* LEFT SIDE OF THE BODY*/

        /* UPPER LEFT ARM */
        int ulArmW = 20, ulArmH = 70;
        Sprite ulArm = new SpriteEllipse(ulArmW, ulArmH, torso);
        ulArm.transform(AffineTransform.getTranslateInstance(-ulArmW/2, 0));
        ulArm.setPivotAround(ulArmW / 2, 0, 360, 10);

        /* LOWER LEFT ARM */
        int llArmW = 20, llArmH = 70;
        Sprite llArm = new SpriteEllipse(llArmW, llArmH, ulArm);
        llArm.transform(AffineTransform.getTranslateInstance(0, ulArmH));
        llArm.setPivotAround(llArmW / 2, 0, 135, -5);

        /* LEFT HAND */
        int lHandW = 20, lHandH = 30;
        Sprite lHand = new SpriteEllipse(lHandW, lHandH, llArm);
        lHand.transform(AffineTransform.getTranslateInstance(0, llArmH));
        lHand.setPivotAround(lHandW / 2, 0, 35);

        /* UPPER LEFT LEG */
        int ulLegW = 20, ulLegH = 90;
        Sprite ulLeg = new SpriteEllipse(ulLegW, ulLegH, torso);
        ulLeg.transform(AffineTransform.getTranslateInstance(0, torsoH));
        ulLeg.setPivotAround(ulLegW / 2, 0, 90);
        ulLeg.setCanScale(true);

        /* LOWER LEFT LEG */
        int llLegW = 20, llLegH = 60;
        Sprite llLeg = new SpriteEllipse(llLegW, llLegH, ulLeg);
        llLeg.transform(AffineTransform.getTranslateInstance(0, ulLegH));
        llLeg.setPivotAround(llLegW / 2, 0, 90);
        llLeg.setCanScale(true);
        ulLeg.setSlave(llLeg);

        /* LEFT FOOT */
        int lFootW = 18, lFootH = 40;
        Sprite lFoot = new SpriteEllipse(lFootW, lFootH, llLeg);
        lFoot.transform(AffineTransform.getTranslateInstance(0, llLegH));
        lFoot.setPivotAround(lFootW / 2, 0, 90, 0, 90);

        /* RIGHT SIDE OF THE BODY*/

        /* UPPER RIGHT ARM */
        Sprite urArm = new SpriteEllipse(ulArmW, ulArmH, torso);
        urArm.transform(AffineTransform.getTranslateInstance(torsoW - ulArmW/2, 0));
        urArm.setPivotAround(ulArmW / 2, 0, 360, -10);

        /* LOWER RIGHT ARM */
        Sprite lrArm = new SpriteEllipse(llArmW, llArmH, urArm);
        lrArm.transform(AffineTransform.getTranslateInstance(0, ulArmH));
        lrArm.setPivotAround(llArmW / 2, 0, 135, 5);

        /* RIGHT HAND */
        Sprite rHand = new SpriteEllipse(lHandW, lHandH, lrArm);
        rHand.transform(AffineTransform.getTranslateInstance(0, llArmH));
        rHand.setPivotAround(lHandW / 2, 0, 35);

        /* UPPER RIGHT LEG */
        Sprite urLeg = new SpriteEllipse(ulLegW, ulLegH, torso);
        urLeg.transform(AffineTransform.getTranslateInstance(torsoW - ulLegW, torsoH));
        urLeg.setPivotAround(ulLegW / 2, 0, 90);
        urLeg.setCanScale(true);

        /* LOWER RIGHT LEG */
        Sprite lrLeg = new SpriteEllipse(llLegW, llLegH, urLeg);
        lrLeg.transform(AffineTransform.getTranslateInstance(0, ulLegH));
        lrLeg.setPivotAround(llLegW / 2, 0, 90);
        lrLeg.setCanScale(true);
        urLeg.setSlave(lrLeg);

        /* RIGHT FOOT */
        Sprite rFoot = new SpriteEllipse(lFootW, lFootH, lrLeg);
        rFoot.transform(AffineTransform.getTranslateInstance(0, llLegH));
        rFoot.setPivotAround(lFootW / 2, 0, 90, 0, -90);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(Color.black);

        root.draw((Graphics2D)g);
    }
}
