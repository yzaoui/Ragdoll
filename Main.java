import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class Main {
    public static void main(String[] args) {
        /* Set up frame */
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Paper Doll");
        frame.setSize(800, 600);
        frame.setVisible(true);
        frame.setResizable(false);

        /* Set up menu bar */
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        /* Set up file menu */
        JMenu file = new JMenu("File");
        menuBar.add(file);

        JMenuItem reset = new JMenuItem("Reset");
        reset.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_DOWN_MASK));
        file.add(reset);

        file.add(new JSeparator());

        JMenuItem quit = new JMenuItem("Quit");
        quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK));
        quit.addActionListener((ActionEvent e) -> System.exit(0));
        file.add(quit);

        /* Set up ragdoll menu */
        JMenu ragdoll = new JMenu("Ragdoll");
        menuBar.add(ragdoll);

        //Person
        JRadioButtonMenuItem person = new JRadioButtonMenuItem("Person", true);
        ragdoll.add(person);

        //Tree
        JRadioButtonMenuItem tree = new JRadioButtonMenuItem("Tree");
        ragdoll.add(tree);

        //Dog
        JRadioButtonMenuItem dog = new JRadioButtonMenuItem("Dog");
        ragdoll.add(dog);

        ButtonGroup group = new ButtonGroup();
        group.add(person);
        group.add(tree);
        group.add(dog);

        /* Set up canvas */
        Canvas canvas = new Canvas();
        frame.add(canvas);

        reset.addActionListener((ActionEvent e) -> canvas.reset());
        person.addActionListener((ActionEvent e) -> canvas.setRagdoll(Ragdoll.Type.PERSON));
        tree.addActionListener((ActionEvent e) -> canvas.setRagdoll(Ragdoll.Type.TREE));
        dog.addActionListener((ActionEvent e) -> canvas.setRagdoll(Ragdoll.Type.DOG));

        frame.repaint();
        frame.revalidate();
    }
}
