import java.util.Random;

public class Board {
    private final int size;
    private final boolean[][] mines;
    private final int[][] numbers;

    public Board(int size, int numMines) {
        this.size = size;
        this.mines = new boolean[size][size];
        this.numbers = new int[size][size];
        placeMines(numMines);
        calculateNumbers();
    }

    private void placeMines(int numMines) {
        Random rand = new Random();
        int placed = 0;
        while (placed < numMines) {
            int x = rand.nextInt(size);
            int y = rand.nextInt(size);
            if (!mines[x][y]) {
                mines[x][y] = true;
                placed++;
            }
        }
    }

    private void calculateNumbers() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (!mines[i][j]) {
                    numbers[i][j] = countAdjacentMines(i, j);
                }
            }
        }
    }

    private int countAdjacentMines(int x, int y) {
        int count = 0;
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                int nx = x + dx, ny = y + dy;
                if (nx >= 0 && ny >= 0 && nx < size && ny < size && mines[nx][ny]) {
                    count++;
                }
            }
        }
        return count;
    }

    public boolean isMine(int x, int y) {
        return mines[x][y];
    }

    public int getNumber(int x, int y) {
        return numbers[x][y];
    }

    public int getSize() {
        return size;
    }
}