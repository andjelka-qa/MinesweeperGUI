import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

public class MinesweeperGUI extends JFrame {
    private final int size;
    private final int numMines;
    private final JButton[][] buttons;
    private final Game game;
    private final Board board;
    private final GameTimer gameTimer;
    private final JLabel timerLabel;
    private final JButton restartButton = new JButton("Restart");

    private final ImageIcon bombRed;
    private final ImageIcon bombGray;

    public MinesweeperGUI(int size, int numMines) {
        this.size = size;
        this.numMines = numMines;
        this.board = new Board(size, numMines);
        this.game = new Game(board);
        this.buttons = new JButton[size][size];
        this.timerLabel = new JLabel("   Time: 0");
        this.gameTimer = new GameTimer(timerLabel);

        bombRed = loadIcon("/bomb_red.png", "red");
        bombGray = loadIcon("/bomb_gray.png", "gray");

        setTitle("Minesweeper GUI");
        setIconImage(new ImageIcon(getClass().getResource("/icon.png")).getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel gridPanel = new JPanel(new GridLayout(size, size));
        initializeButtons(gridPanel);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(timerLabel, BorderLayout.CENTER);
        topPanel.add(restartButton, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);
        add(gridPanel, BorderLayout.CENTER);

        setSize(size * 50, size * 50 + 40);
        setVisible(true);
        restartButton.addActionListener(e -> restartGame());
    }

    private ImageIcon loadIcon(String path, String label) {
        URL imageUrl = getClass().getResource(path);
        if (imageUrl == null) {
            System.out.println("Could not load bomb image (" + label + "): " + path);
            return null;
        }
        Image img = new ImageIcon(imageUrl).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        /*System.out.println("Loaded bomb image (" + label + ")");*/
        return new ImageIcon(img);
    }

    private void initializeButtons(JPanel panel) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                JButton button = new JButton("?");
                button.setBackground(new Color(179, 255, 102));
                int finalI = i;
                int finalJ = j;

                button.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (SwingUtilities.isRightMouseButton(e)) {
                            toggleFlag(finalI, finalJ);
                        } else if (SwingUtilities.isLeftMouseButton(e)) {
                            gameTimer.start();
                            handleClick(finalI, finalJ);
                        }
                    }
                });

                buttons[i][j] = button;
                panel.add(button);
            }
        }
    }

    private void handleClick(int x, int y) {
        if (game.isRevealed(x, y)) return;

        if (board.isMine(x, y)) {
            buttons[x][y].setIcon(bombRed);
            buttons[x][y].setText("");
            buttons[x][y].setBackground(new Color(112, 0, 0));
            revealAllMines(x, y);

            timerLabel.setText("   PUKLA BOMBA!");
            timerLabel.setForeground(Color.RED);
            gameTimer.stop();
        } else {
            game.reveal(x, y);
            updateBoardVisuals();

            if (game.isWin()) {
                timerLabel.setText("   POBEDA!");
                timerLabel.setForeground(new Color(0, 128, 0));
                gameTimer.stop();
            }

            updateBoardVisuals();
        }
    }

    private void updateBoardVisuals() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (game.isRevealed(i, j)) {
                    int val = board.getNumber(i, j);
                    if (val == 0) {
                        buttons[i][j].setText("");
                        buttons[i][j].setBackground(Color.LIGHT_GRAY);
                    } else {
                        buttons[i][j].setText(String.valueOf(val));
                        buttons[i][j].setForeground(getColor(val));
                        buttons[i][j].setBackground(new Color(230, 230, 230));
                    }
                    for (MouseListener ml : buttons[i][j].getMouseListeners()) {
                        buttons[i][j].removeMouseListener(ml);
                    }
                }
            }
        }
    }

    private void revealAllMines(int clickedX, int clickedY) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board.isMine(i, j)) {
                    buttons[i][j].setIcon(i == clickedX && j == clickedY ? bombRed : bombGray);
                    buttons[i][j].setText("");
                    buttons[i][j].setBackground(new Color(230, 230, 230));
                }

                for (MouseListener ml : buttons[i][j].getMouseListeners()) {
                    buttons[i][j].removeMouseListener(ml);
                }
            }
        }
    }

    private void toggleFlag(int x, int y) {
        String text = buttons[x][y].getText();
        if (text.equals("?")) {
            buttons[x][y].setText("🚩");
        } else if (text.equals("🚩")) {
            buttons[x][y].setText("?");
        }
    }

    private Color getColor(int val) {
        return switch (val) {
            case 1 -> Color.BLUE;
            case 2 -> Color.ORANGE;
            case 3 -> Color.RED;
            case 4 -> Color.MAGENTA;
            default -> Color.BLACK;
        };
    }

    private void restartGame() {
        this.dispose();
        new MinesweeperGUI(size, numMines);
    }
}