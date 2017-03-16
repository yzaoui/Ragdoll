import java.awt.*;

public abstract class Ragdoll {
    protected Sprite root;

    public Sprite getSelectedSprite(Point p) {
        return this.root.getSelectedSprite(p);
    }

    public void draw(Graphics2D g) {
        this.root.draw(g);
    }
}
