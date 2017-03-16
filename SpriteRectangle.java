import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;

public class SpriteRectangle extends Sprite {
    private RoundRectangle2D shape = null;

    public SpriteRectangle(int width, int height, Sprite parent) {
        super(parent);
        this.shape = new RoundRectangle2D.Double(0, 0, width, height, 15, 15);
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
    protected void scaleShapeY(double scale) {
        //Should never reach here
    }

    @Override
    protected void startScale() {
        //Should never reach here
    }

    @Override
    protected void drawSprite(Graphics2D g) {
        g.setColor(this.fillColor);
        g.fill(shape);
        g.setColor(Color.black);
        g.draw(shape);
    }
}
