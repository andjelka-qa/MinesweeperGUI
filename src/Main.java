import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            int size = Integer.parseInt(JOptionPane.showInputDialog("Enter board size (example: 8):"));
            int bombs = Integer.parseInt(JOptionPane.showInputDialog("Enter number of bombs (example: 10):"));

            SwingUtilities.invokeLater(() -> new MinesweeperGUI(size, bombs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid input. Exiting.");
        }
    }
}

