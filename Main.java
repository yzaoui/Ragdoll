import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Canvas canvas = new Canvas();

        JFrame frame = new JFrame();
        frame.add(canvas);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Paper Doll");
        frame.setSize(800, 600);
        frame.setVisible(true);
    }
}
