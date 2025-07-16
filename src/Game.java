public class Game {
    private final Board board;
    private final boolean[][] revealed;

    public Game(Board board) {
        this.board = board;
        int size = board.getSize();
        this.revealed = new boolean[size][size];
    }

    public void reveal(int x, int y) {
        if (!isValid(x, y) || revealed[x][y]) return;
        revealed[x][y] = true;

        if (board.getNumber(x, y) == 0 && !board.isMine(x, y)) {
            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    if (dx != 0 || dy != 0) {
                        reveal(x + dx, y + dy);
                    }
                }
            }
        }
    }

    public boolean isRevealed(int x, int y) {
        return revealed[x][y];
    }

    public boolean isWin() {
        int size = board.getSize();
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                if (!board.isMine(i, j) && !revealed[i][j])
                    return false;
        return true;
    }

    private boolean isValid(int x, int y) {
        int size = board.getSize();
        return x >= 0 && y >= 0 && x < size && y < size;
    }

    public Board getBoard() {
        return board;
    }
}