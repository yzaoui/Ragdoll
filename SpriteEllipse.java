import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;

public class SpriteEllipse extends Sprite {
    private Ellipse2D shape = null;
    private int width, height;
    private int heightSinceClick;

    public SpriteEllipse(int width, int height, Sprite parent) {
        super(parent);
        this.shape = new Ellipse2D.Double(0, 0, width, height);
        this.width = width;
        this.height = height;
    }
    @Override
    public boolean containsPoint(Point2D p) {
        AffineTransform fullTransform = this.getFullTransform();
        AffineTransform inverseTransform = null;

        try {
            inverseTransform = fullTransform.createInverse();
        } catch (NoninvertibleTransformException e) {
            e.printStackTrace();
        }

        Point2D newPoint = inverseTransform.transform(p, null);
        return shape.contains(newPoint);
    }

    @Override
    protected void startScale() {
        this.heightSinceClick = height;
    }

    @Override
    protected void scaleShapeY(double scale) {
        this.shape = new Ellipse2D.Double(0, 0, width, (int)(heightSinceClick * scale));
        this.scaleChildren((int)(heightSinceClick * scale) - height);
        height = (int)(heightSinceClick * scale);
    }

    @Override
    protected void drawSprite(Graphics2D g) {
        g.setColor(Color.black);
        g.draw(shape);
    }
}