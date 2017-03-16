import java.awt.geom.AffineTransform;
import java.util.Random;

public class Tree extends Ragdoll {
    public Tree() {
        int trunkW = 60, trunkH = 40;
        Sprite trunk = new SpriteRectangle(trunkW, trunkH, null);
        trunk.transform(AffineTransform.getTranslateInstance(370, 480));
        trunk.setCanTranslate(true);

        int branchW = 500, branchH = 20;
        Sprite parentBranch = new SpriteRectangle(branchW, branchH, trunk);
        parentBranch.transform(AffineTransform.getTranslateInstance((trunkW - branchW) / 2, -branchH));
        Random rand = new Random();
        parentBranch.setPivotAround(branchW / 2, branchH / 2, 45, rand.nextDouble() * 20 - 10);

        int shrinkFactor = 22;
        for (int i = 0; i < 21; i++) {
            branchW -= shrinkFactor;
            Sprite branch = new SpriteRectangle(branchW, branchH, parentBranch);
            parentBranch = branch;
            branch.transform(AffineTransform.getTranslateInstance(shrinkFactor / 2, -branchH));
            branch.setPivotAround(branchW / 2, branchH / 2, 45, rand.nextDouble() * 20 - 10);
        }

        root = trunk;
    }
}
