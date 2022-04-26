import processing.core.PApplet;

import java.util.ArrayList;

public class Game extends PApplet {
    protected static Bombs bomb;
    protected final Flag flag;
    private static ArrayList<Coordinates> allCoordinates;
    protected static Coordinates size;
    public static StopWatch timer;

    public static final String BOMB = "bomb.png";
    public static final String EMPTY_CELL = "opened.png";
    public static final String CLOSED_CELL = "closed.png";
    public static final String FLAG = "flag.png";
    public static final int IMAGE_SIZE = 40;

    private boolean isFirstClick;

    enum GameStates {BEGINNING, IN_PROCESS, LOST, WON}

    protected static GameStates status;

    public Game(int cols, int rows, int bombs) {
        size = new Coordinates(cols, rows);
        allCoordinates = new ArrayList<>();

        for (int y = 0; y < size.y; y++) {
            for (int x = 0; x < size.x; x++) {
                allCoordinates.add(new Coordinates(x, y));
            }
        }
        bomb = new Bombs(bombs);
        flag = new Flag();
        isFirstClick = true;
        status = GameStates.BEGINNING;
        timer = new StopWatch();
    }


    public static ArrayList<Coordinates> getAllCoordinates() {
        return allCoordinates;
    }


    void leftClick(Coordinates coordinates) {
        if (isFirstClick) {
            isFirstClick = false;
            Bombs.placeBombs(coordinates);
            status = GameStates.IN_PROCESS;
            timer.start();
        }
        openCell(coordinates);
        isVictory();
        Main.isGameOver();
    }

    public void rightClick(Coordinates coordinates) {
        flag.setFlag(coordinates);
        isVictory();
        Main.isGameOver();
    }


    private void isVictory() {
        if (status == GameStates.IN_PROCESS && flag.uncoveredFields - bomb.bombsLeft == flag.uncoveredFields) {
            status = GameStates.WON;
            openRemainingClosedCells();
        }
    }

    private void openRemainingClosedCells() {
        for (Coordinates all : getAllCoordinates()) {
            if (flag.getFlagChar(all).equals(CLOSED_CELL))
                flag.setOpened(all);
            if (flag.getFlagChar(all).equals(BOMB)) {
                flag.setBombedExcept(all);
                status = GameStates.LOST;
            }
        }
    }

    void openCell(Coordinates coordinates) {
        switch (flag.getFlagChar(coordinates)) {
            case FLAG:
            case EMPTY_CELL:
                return;
            case "1.png":
            case "2.png":
            case "3.png":
            case "4.png":
            case "5.png":
            case "6.png":
            case "7.png":
            case "8.png":
                openNextFields(coordinates);
                return;
            case CLOSED_CELL:
                switch (Bombs.getBomb(coordinates)) {
                    case BOMB:
                        openBombsAndLose(coordinates);
                        return;
                    case Bombs.ZERO:
                        openAround(coordinates);
                        return;
                    default:
                        flag.setOpened(coordinates);
                }

        }
    }

    void openBombsAndLose(Coordinates coordinates) {
        flag.setBombedExcept(coordinates);
        showBombs(coordinates);
        status = GameStates.LOST;
    }

    void showBombs(Coordinates except) {
        for (Coordinates all : allCoordinates) {
            if (Bombs.getBomb(all).equals(BOMB) && all != except) {
                flag.setBombed(all);
            } else if (flag.getFlagChar(all).equals(FLAG) && !Bombs.getBomb(all).equals(BOMB)) {
                flag.setWrongFlag(all);
            }
        }
    }

    void openAround(Coordinates coordinates) {
        flag.setOpened(coordinates);
        for (Coordinates around : getCoordinatesAround(coordinates)) {
            openCell(around);
        }
    }


    void openNextFields(Coordinates coordinates) {
        if (!Bombs.getBomb(coordinates).equals(BOMB) &&
                flag.getFlagsAround(coordinates) == Integer.parseInt(String.valueOf((Bombs.getBomb(coordinates)).charAt(0)))) {
            for (Coordinates around : getCoordinatesAround(coordinates)) {
                if (flag.getFlagChar(around).equals(CLOSED_CELL)) {
                    openCell(around);
                }
            }
        }
    }

    public static ArrayList<Coordinates> getCoordinatesAround(Coordinates coordinates) {
        Coordinates around;
        ArrayList<Coordinates> list = new ArrayList<>();
        for (int i = coordinates.x - 1; i <= coordinates.x + 1; i++) {
            for (int j = coordinates.y - 1; j <= coordinates.y + 1; j++) {
                around = new Coordinates(i, j);
                if (Coordinates.areCoordinatesInside(around) && !around.equals(coordinates)) {
                    list.add(around);
                }
            }
        }
        return list;
    }

    public static void setBombsLeft(int n) {
        bomb.bombsLeft += n;
    }

    //toString overriding
    /*
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("Game(").append(level);
        res.append(", width = ").append(cols);
        res.append(", height = ").append(rows);
        res.append(", mines = ").append(bombs).append(")\n");
        res.append("Cells to uncover: ").append(flag.uncoveredFields).append("\n");
        res.append("Bombs left: ").append(bomb.bombsLeft).append("\n");

        for (int r = 0; r < size.y; r++){
            for (int c = 0; c < size.x; c++){
                res.append(flag.getFlagChar(new Coordinates(c, r)));
            }
            res.append("\n");
        }
        return res.toString();
    }*/
}
