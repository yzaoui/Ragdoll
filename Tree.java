import java.awt.geom.AffineTransform;

public class Tree extends Ragdoll {
    public Tree() {
        int trunkW = 60, trunkH = 40;
        Sprite trunk = new SpriteRectangle(trunkW, trunkH, null);
        trunk.transform(AffineTransform.getTranslateInstance(370, 440));
        trunk.setCanTranslate(true);

        root = trunk;
    }
}
