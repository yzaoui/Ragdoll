import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.util.Vector;

public abstract class Sprite {
    private Sprite parent;
    private Sprite slave = null;
    private Vector<Sprite> children = new Vector<>();
    private AffineTransform transform = new AffineTransform();
    private Point2D lastPoint = null;
    protected Color fillColor = Color.white;

    private double initialAbsoluteAngle = 0;

    private boolean canPivot = false;
        private int pivotX, pivotY;
        private double pivotAngle;
        private double pivotMaxAngle;
    private boolean canScale = false;
        private int lastScalePointY;
    private boolean canTranslate = false;

    private AffineTransform selectedInverseTransform = null;

    public Sprite(Sprite parent) {
        if (parent != null) {
            this.parent = parent;
            parent.addChild(this);
        }
    }

    public void addChild(Sprite child) {
        this.children.add(child);
        child.setParent(this);
    }

    public void setParent(Sprite parent) {
        this.parent = parent;
    }

    public Sprite getParent() {
        return this.parent;
    }

    private boolean hasSlave() {
        return (this.slave != null);
    }

    public void setSlave(Sprite slave) {
        this.slave = slave;
    }

    public void setPivotAround(int x, int y, double maxAngle) {
        this.canPivot = true;
        this.pivotX = x;
        this.pivotY = y;
        this.pivotMaxAngle = Math.toRadians(maxAngle);
        this.pivotAngle = 0;
    }

    public void setPivotAround(int x, int y, double maxAngle, double initialAngleWithinRange) {
        setPivotAround(x, y, maxAngle);
        this.pivot(Math.toRadians(initialAngleWithinRange));
    }

    public void setPivotAround(int x, int y, double maxAngle, double initialAngleWithinRange, double initialAbsoluteAngle) {
        setPivotAround(x, y, maxAngle, initialAngleWithinRange);
        this.initialAbsoluteAngle = Math.toRadians(initialAbsoluteAngle);
        this.transform.rotate(this.initialAbsoluteAngle, this.pivotX, this.pivotY);
    }

    public void setCanScale(boolean b) {
        this.canScale = b;
    }

    public void setCanTranslate(boolean b) {
        this.canTranslate = b;
    }

    public abstract boolean containsPoint(Point2D p);

    public void pivot(double newAngle) {
        if (Math.abs(newAngle + pivotAngle) <= this.pivotMaxAngle) {
            if (selectedInverseTransform != null) {
                selectedInverseTransform.rotate(newAngle, pivotX, pivotY);
            }

            this.transform.rotate(newAngle, pivotX, pivotY);

            if (this.pivotAngle + newAngle >= Math.PI || this.pivotAngle + newAngle <= -Math.PI) {
                this.pivotAngle = 0;
            }

            this.pivotAngle += newAngle;
        }
    }

    protected abstract void scaleShapeY(double scale);

    protected abstract void startScale();

    protected void translateChildren(int dy) {
        for (Sprite child : children) {
            child.transform.rotate(-child.pivotAngle - child.initialAbsoluteAngle);
            child.transform.translate(0, dy);
            child.transform.rotate(child.pivotAngle + child.initialAbsoluteAngle);
        }
    }

    protected void scaleSlave(double scale) {
        if (this.hasSlave()) {
            this.slave.scaleShapeY(scale);
        }
    }

    protected void drag(Point2D p) {
        try {
            Point2D localPNonRotated = this.selectedInverseTransform.inverseTransform(p, null);

            if (this.canPivot) {
                int transformedX = pivotX - (int)localPNonRotated.getX();
                int transformedY = pivotY - (int)localPNonRotated.getY();

                double newAngle = -Math.atan2(transformedX, transformedY);
                this.pivot(newAngle);
                if (canScale) {
                    double scale = (double)transformedY / lastScalePointY;
                    this.scaleShapeY(scale);
                }
            } else if (this.canTranslate) {
                double translateX = localPNonRotated.getX() - lastPoint.getX();
                double translateY = localPNonRotated.getY() - lastPoint.getY();
                this.transform.translate(translateX, translateY);
                lastPoint.setLocation(localPNonRotated);
            }
        } catch (NoninvertibleTransformException e) {
            e.printStackTrace();
        }
    }

    public Sprite getSelectedSprite(Point p) {
        //Check children first
        for (Sprite child : children) {
            Sprite selected = child.getSelectedSprite(p);
            if (selected != null) {
                return selected;
            }
        }

        //Check self
        if (this.containsPoint(p)) {
            selectedInverseTransform = new AffineTransform(this.getFullTransform());

            try {
                Point2D localP = selectedInverseTransform.inverseTransform(p, null);
                if (this.canPivot) {
                    selectedInverseTransform.rotate(-(Math.atan2(pivotX - localP.getX(), pivotY - localP.getY())), pivotX, pivotY);

                    if (this.canScale) {
                        lastScalePointY = (int)localP.getY() - pivotY;
                        this.startScale();
                        if (this.hasSlave()) {
                            this.slave.startScale();
                        }
                    }
                } else if (this.canTranslate) {
                    lastPoint = localP;
                }
            } catch (NoninvertibleTransformException e) {
                e.printStackTrace();
            }

            return this;
        }

        //No hits
        return null;
    }

    public AffineTransform getFullTransform() {
        AffineTransform returnTransform = new AffineTransform();
        Sprite curSprite = this;

        while (curSprite != null) {
            returnTransform.preConcatenate(curSprite.getLocalTransform());
            curSprite = curSprite.getParent();
        }

        return returnTransform;
    }

    public AffineTransform getLocalTransform() {
        return new AffineTransform(this.transform);
    }

    public void transform(AffineTransform t) {
        this.transform.concatenate(t);
    }

    public void draw(Graphics2D g) {
        AffineTransform oldTransform = g.getTransform();

        g.setTransform(this.getFullTransform());

        this.drawSprite(g);

        g.setTransform(oldTransform);

        for (Sprite child : children) {
            child.draw(g);
        }
    }

    public void setColor(Color color) {
        this.fillColor = color;
    }

    protected abstract void drawSprite(Graphics2D g);
}
