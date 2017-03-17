import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class Canvas extends JPanel {
    private Ragdoll root;
    private Ragdoll.Type currentType;
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
        this.currentType = Ragdoll.Type.PERSON;

        this.reset();
        this.repaint();
        this.revalidate();
    }

    public void reset() {
        switch (currentType) {
            case PERSON:
                root = new Person();
                break;
            case TREE:
                root = new Tree();
                break;
            case DOG:
                root = new Dog();
                break;
        }

        Timer timer = new Timer(20, (ActionEvent e) -> Canvas.this.repaint());
        timer.setRepeats(false);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(Color.black);

        root.draw((Graphics2D)g);
    }

    public void setRagdoll(Ragdoll.Type type) {
        if (this.currentType != type) {
            this.currentType = type;
            this.reset();
        }
    }
}
