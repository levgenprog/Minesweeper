public class Bombs {
    private static Field bombMap;
    private static int finalBombs;
    private static final String[] numbers = {"1.png", "2.png", "3.png", "4.png", "5.png", "6.png", "7.png", "8.png"};
    public static final String ZERO = "zero.png";
    public int bombsLeft;


    private static final int[] DR = {-1, -1, 0, 1, 1, 1, 0, -1};
    private static final int[] DC = {0, 1, 1, 1, 0, -1, -1, -1};

    public Bombs(int bombsTotal) {
        finalBombs = bombsTotal;
        bombMap = new Field(Game.EMPTY_CELL);
        bombsLeft = finalBombs;
    }

    public static void placeBombs(Coordinates except) {
        int bombsSet = 0;
        while (bombsSet < finalBombs) {
            Coordinates coordinates = Coordinates.getRandomCoordinates();
            if (!bombMap.getMatrixChar(coordinates).equals(Game.BOMB) && !coordinates.equals(except)) {
                bombMap.setMatrix(coordinates, Game.BOMB);
                setNumbersAroundBombs();
                bombsSet++;
            }
        }
    }

    static String getBomb(Coordinates coordinates) {
        return bombMap.getMatrixChar(coordinates);
    }

    static void setNumbersAroundBombs() {
        for (Coordinates around : Game.getAllCoordinates()) {
            if (!bombMap.getMatrixChar(around).equals(Game.BOMB)) {
                int nMines = 0;
                for (int i = 0; i < DR.length; i++) {
                    int tr = around.y + DR[i];
                    int tc = around.x + DC[i];
                    if (Coordinates.areCoordinatesInside(new Coordinates(tc, tr)) &&
                            bombMap.getMatrixChar(new Coordinates(tc, tr)).equals(Game.BOMB)) {
                        nMines++;
                    }
                }
                String nBombs = " ";
                switch (nMines) {
                    case 0:
                        nBombs = ZERO;
                        break;
                    case 1:
                        nBombs = numbers[0];
                        break;
                    case 2:
                        nBombs = numbers[1];
                        break;
                    case 3:
                        nBombs = numbers[2];
                        break;
                    case 4:
                        nBombs = numbers[3];
                        break;
                    case 5:
                        nBombs = numbers[4];
                        break;
                    case 6:
                        nBombs = numbers[5];
                        break;
                    case 7:
                        nBombs = numbers[6];
                        break;
                    case 8:
                        nBombs = numbers[7];
                        break;
                }
                bombMap.setMatrix(around, nBombs);
            }
        }

        //of meth to set numbers
        /*for (Coordinates around : Coordinates.getCoordinatesAround(coordinates)){
            int nBombs = 1;
            /*for (Coordinates round2 : Coordinates.getCoordinatesAround(around)){

            }


            if (bombMap.getMatrixChar(around) != BOMB) {
                nBombs++;
            }

            char charNBombs = ' ';
            switch (nBombs){
                case 1: charNBombs = '1';
                break;
                case 2: charNBombs = '2';
                break;
                case 8: charNBombs = '8';
                break;
                default: charNBombs = '-';
            }
            bombMap.setMatrix(around, charNBombs);
        }*/
    }
}
