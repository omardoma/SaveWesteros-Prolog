import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Grid {
    private int m;
    private int n;
    private Cell[][] cells;
    private Cell dragonStone;
    private Cell agentCell;
    private List<Cell> obstacles;
    private List<Cell> whiteWalkers;
    private int agentCapacity;
    public static final int MAX_DIMENSION = 10;
    public static final int MIN_DIMENSION = 3;
    public static final int MIN_WHITE_WALKERS = 2;
    public static final int MIN_OBSTACLES = 2;
    public static final int MIN_AGENT_CAPACITY = 1;

    public Grid() {
        Random rand = new Random();
        m = rand.nextInt((MAX_DIMENSION - MIN_DIMENSION) + 1) + MIN_DIMENSION;
        n = rand.nextInt((MAX_DIMENSION - MIN_DIMENSION) + 1) + MIN_DIMENSION;
        obstacles = new ArrayList<>();
        whiteWalkers = new ArrayList<>();
        cells = new Cell[m][n];
        initCells();
        generateMap();
    }

    public Grid(int n) throws Exception {
        this(n, n);
    }

    public Grid(int m, int n) throws Exception {
        if (!(m >= MIN_DIMENSION && m <= MAX_DIMENSION && n >= MIN_DIMENSION && n <= MAX_DIMENSION)) {
            throw new Exception("Map dimensions minimum is " + MIN_DIMENSION + " and maximum " + MAX_DIMENSION);
        }
        this.m = m;
        this.n = n;
        obstacles = new ArrayList<>();
        whiteWalkers = new ArrayList<>();
        cells = new Cell[m][n];
        initCells();
        generateMap();
    }

    public int getM() {
        return m;
    }

    public int getN() {
        return n;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public Cell getDragonStone() {
        return dragonStone;
    }

    public Cell getAgentCell() {
        return agentCell;
    }

    public List<Cell> getObstacles() {
        return obstacles;
    }

    public List<Cell> getWhiteWalkers() {
        return whiteWalkers;
    }

    public int getAgentCapacity() {
        return agentCapacity;
    }

    private void initCells() {
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                cells[i][j] = new Cell(i, j);
            }
        }
    }

    private void generateMap() {
        Random rand = new Random();
        int whiteWalkersNumber = rand.nextInt((n - MIN_WHITE_WALKERS) + 1) + MIN_WHITE_WALKERS;
        int obstaclesNumber = rand.nextInt((n - MIN_OBSTACLES) + 1) + MIN_OBSTACLES;
        List<String> existingCoordinates = new ArrayList<>();
        int tmpRow = m - 1;
        int tmpCol = n - 1;

        // Place agent
        agentCell = cells[tmpRow][tmpCol];
        existingCoordinates.add(tmpRow + " " + tmpCol);

        // Place obstacles
        for (int i = 0; i < obstaclesNumber; i++) {
            do {
                tmpRow = rand.nextInt(m);
                tmpCol = rand.nextInt(n);
            } while (existingCoordinates.contains(tmpRow + " " + tmpCol));
            existingCoordinates.add(tmpRow + " " + tmpCol);
            obstacles.add(cells[tmpRow][tmpCol]);
        }

        // Place White Walkers
        for (int i = 0; i < whiteWalkersNumber; i++) {
            do {
                tmpRow = rand.nextInt(m);
                tmpCol = rand.nextInt(n);
            }
            while (existingCoordinates.contains(tmpRow + " " + tmpCol));
            existingCoordinates.add(tmpRow + " " + tmpCol);
            whiteWalkers.add(cells[tmpRow][tmpCol]);
        }

        // Place the dragonstone
        do {
            tmpRow = rand.nextInt(m);
            tmpCol = rand.nextInt(n);
        } while (existingCoordinates.contains(tmpRow + " " + tmpCol));
        dragonStone = cells[tmpRow][tmpCol];

        agentCapacity = rand.nextInt((whiteWalkersNumber - MIN_AGENT_CAPACITY) + 1) + MIN_AGENT_CAPACITY;
    }

    public void printGridInfo() {
        System.out.println("Generated Grid Size: " + m + " x " + n);
        System.out.println("No. of White Walkers: " + whiteWalkers.size());
        System.out.println("No. of Obstacles: " + obstacles.size());
        System.out.println("Agent Capacity: " + agentCapacity);
        System.out.println();

        Cell current;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                current = cells[i][j];
                System.out.print("[" + (obstacles.contains(current) ? "O" : whiteWalkers.contains(current) ? "W" : current == dragonStone ? "D" : current == agentCell ? "A" : "E") + "]");
            }
            System.out.println();
        }

        System.out.println();
    }

    public void writeKB(String path) throws IOException {
        BufferedWriter br = new BufferedWriter(new FileWriter(path));
        StringBuilder sb = new StringBuilder();

        Cell current;
        for (int i = 0; i < m; i++) {
            br.write("%% ");
            for (int j = 0; j < n; j++) {
                current = cells[i][j];
                br.write("[" + (obstacles.contains(current) ? "O" : whiteWalkers.contains(current) ? "W" : current == dragonStone ? "D" : current == agentCell ? "A" : "E") + "]");
            }
            br.newLine();
        }

        br.newLine();

        // Print Grid Boundaries
        br.write(sb.append("grid(").append(m).append(",").append(n).append(").").toString());
        br.newLine();
        sb.setLength(0);

        // Print DragonStone Capacity
        br.write(sb.append("agent_capacity(").append(agentCapacity).append(").").toString());
        br.newLine();
        sb.setLength(0);

        // Place DragonStone
        br.write(sb.append("dragon_stone(").append(dragonStone.getRow()).append(",").append(dragonStone.getCol()).append(").").toString());
        br.newLine();
        sb.setLength(0);

        // Place Obstacles
        for (Cell obstacle : obstacles) {
            br.write(sb.append("obstacle(").append(obstacle.getRow()).append(",").append(obstacle.getCol()).append(").").toString());
            br.newLine();
            sb.setLength(0);
        }

        // Place White Walkers
        for (Cell whiteWalker : whiteWalkers) {
            br.write(sb.append("white_walker(").append(whiteWalker.getRow()).append(",").append(whiteWalker.getCol()).append(",").append("s0").append(").").toString());
            br.newLine();
            sb.setLength(0);
        }

        // Place Agent
        br.write(sb.append("agent(").append(agentCell.getRow()).append(",").append(agentCell.getCol()).append(",").append("0").append(",").append("s0").append(").").toString());
        br.newLine();
        sb.setLength(0);

        br.newLine();

        // Write the goal predicate
        br.write(sb.append("%% Checks if all white walkers in the grid have been killed, which is my goal state").append("\n").append("is_goal(S):-").toString());
        br.newLine();
        sb.setLength(0);

        int size = whiteWalkers.size();
        for (int i = 0; i < size; i++) {
            current = whiteWalkers.get(i);
            br.write(sb.append("\tkilled_white_walker(").append(current.getRow()).append(",").append(current.getCol()).append(",").append("S").append(")").append(i != size - 1 ? "," : ".").toString());
            br.newLine();
            sb.setLength(0);
        }

        br.newLine();

        br.close();
    }
}
