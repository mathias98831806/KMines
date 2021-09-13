import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class KMiner {

    private HashMap<Integer, Integer> levelMine;
    private Integer[][] minerGrid;
    public int rowCount;
    public int columnCount;
    private int freedPlaces;
    private int currentLevel;


    public int getCurrentLevel() {
        return currentLevel;
    }


    public KMiner() {
        System.out.println("Creating the hashMap that will contain the levels and mines count");
        levelMine = new HashMap<Integer, Integer>();
        System.out.println("Initialization of row count");
        rowCount = 10;

        freedPlaces = 0;
        currentLevel = 1;

        System.out.println("Initialization of column count");
        columnCount = 10;
        System.out.println("Initialization of the levelMine object");
        initLevelMine();
        System.out.println("Initialization of the default grid");
        initMinerGrid();
    }

    // Check if the player click on a mine or not
    public boolean isMine(int x, int y) {
        return minerGrid[x][y] == 1;
    }

    // Return minesFound by the player
    public void mineFound() {
        freedPlaces++;
    }

    public int minesCount() {
        return levelMine.get(currentLevel);
    }

    // Check if the player win or not
    public boolean playerWined() {
        return freedPlaces == rowCount*columnCount - levelMine.get(currentLevel);
    }

    // Generate nextLevel and return it
    public void nextLevel() {
        System.out.println("###########################");
        System.out.println(currentLevel);
        System.out.println(levelMine.get(currentLevel));
        if (currentLevel < 4) {
            currentLevel++;
            generateLevel(currentLevel);
        }
    }

    // Generate a grid according to the level
    private void generateLevel(int level) {
        minerGrid = generate(rowCount, columnCount, levelMine.get(level));
    }

    // Return the generated grid
    public Integer[][] getGrid() {
        return minerGrid;
    }

    // Init the object levelMine
    private void initLevelMine() {

        levelMine.put(1, 30);
        levelMine.put(2, 50);
        levelMine.put(3, 40);
        levelMine.put(4, 80);
    }

    // Init the grid of mines
    private void initMinerGrid() {
        minerGrid = new Integer[rowCount][columnCount];
        generateLevelOne();
    }

    // Generate levelOne
    public void generateLevelOne() {
        System.out.println("Generating level one miner grid");
        minerGrid = generate(rowCount, columnCount, levelMine.get(1));
    }

    // Generate level two
    public void generateLevelTwo() {
        System.out.println("Generating level two miner grid");
        minerGrid = generate(rowCount, columnCount, levelMine.get(2));
    }

    // Generate level three
    public void generateLevelThree() {
        System.out.println("Generating level three miner grid");
        minerGrid = generate(rowCount, columnCount, levelMine.get(3));
    }

    // Generate level four
    public void generateLevelFour() {
        System.out.println("Generating level four miner grid");
        minerGrid = generate(rowCount, columnCount, levelMine.get(4));
    }

    //
    public int neighborMinesCountAt(int x, int y) {
        System.out.println("Computing neighbor mines count");
        int minesCount = 0;

        System.out.println("Building neighbors");
        List<Integer> neighbors = buildNeighbor(x, y);

        System.out.println("Counting the mines");
        for (int value : neighbors) {
            if (value == 1) minesCount++;
        }

        return minesCount;
    }

    private List<Integer> buildNeighbor(int x, int y) {
        List<Integer> neighbors = new ArrayList<>();

        // First line
        if (y == 0) {
            neighbors.add(minerGrid[x][y + 1]);
            if (x + 1 < rowCount) {
                neighbors.add(minerGrid[x + 1][y]);
                neighbors.add(minerGrid[x + 1][y + 1]);
            }

            if (x > 0) {
                neighbors.add(minerGrid[x - 1][y]);
                neighbors.add(minerGrid[x - 1][y + 1]);
            }
        }

        // Last line
        if (y == columnCount - 1) {
            neighbors.add(minerGrid[x][y - 1]);
            if (x + 1 < rowCount) {
                neighbors.add(minerGrid[x + 1][y]);
                neighbors.add(minerGrid[x + 1][y - 1]);
            }

            if (x > 0) {
                neighbors.add(minerGrid[x - 1][y]);
                neighbors.add(minerGrid[x - 1][y - 1]);
            }
        }

        // First column
        if (x == 0 && y > 0 && y < columnCount - 1) {
            neighbors.add(minerGrid[x + 1][y]);

            neighbors.add(minerGrid[x + 1][y - 1]);
            neighbors.add(minerGrid[x][y - 1]);


            neighbors.add(minerGrid[x][y + 1]);
            neighbors.add(minerGrid[x + 1][y + 1]);


        }

        // Last Column
        if (x + 1 == rowCount && y > 0 && y + 1 < columnCount) {
            neighbors.add(minerGrid[x - 1][y]);

            neighbors.add(minerGrid[x - 1][y - 1]);
            neighbors.add(minerGrid[x][y - 1]);


            neighbors.add(minerGrid[x][y + 1]);
            neighbors.add(minerGrid[x - 1][y + 1]);

        }

        if (x > 0 && x + 1 < rowCount && y > 0 && y + 1 < columnCount) {
            neighbors.add(minerGrid[x + 1][y + 1]);
            neighbors.add(minerGrid[x + 1][y]);
            neighbors.add(minerGrid[x][y - 1]);
            neighbors.add(minerGrid[x][y + 1]);
            neighbors.add(minerGrid[x - 1][y]);
            neighbors.add(minerGrid[x - 1][y - 1]);
            neighbors.add(minerGrid[x + 1][y - 1]);
            neighbors.add(minerGrid[x - 1][y + 1]);
        }

        return neighbors;
    }


    private static Integer[][] generate(int rowCount, int colCount, int minesCount) {

        Integer[][] kminerGrid = new Integer[rowCount][colCount];

        // Fill with 1
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                kminerGrid[i][j] = 1;
            }
        }

        // Place mines mines
        int freedPlaces = 0;
        int freedPlacesCount = rowCount * colCount - minesCount;
        while (freedPlaces < freedPlacesCount) {
            int randx = new Random().nextInt(rowCount);
            int randy = new Random().nextInt(colCount);


            if (kminerGrid[randx][randy] == 1) {
                kminerGrid[randx][randy] = 0;
                freedPlaces++;
            }
        }


        return kminerGrid;
    }

    private static void fillMinerGrid(int[][] minerGrid) {

    }
}
