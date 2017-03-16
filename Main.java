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

        /* Set up canvas */
        Canvas canvas = new Canvas();
        frame.add(canvas);

        reset.addActionListener((ActionEvent e) -> canvas.reset());

        frame.repaint();
        frame.revalidate();
    }
}
