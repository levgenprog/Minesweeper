import processing.core.PApplet;

public class Flag extends PApplet {
    private Field flagMap;
    int uncoveredFields = Game.size.x * Game.size.y;

    public Flag() {
        flagMap = fillField();
    }

    Field fillField() {
        flagMap = new Field(Game.CLOSED_CELL);
        return flagMap;
    }

    public String getFlagChar(Coordinates coordinates) {
        return flagMap.getMatrixChar(coordinates);
    }

    void setOpened(Coordinates coordinates) {
        flagMap.setMatrix(coordinates, Bombs.getBomb(coordinates));
        uncoveredFields--;
    }

    void setBombed(Coordinates coordinates) {
        if (!flagMap.getMatrixChar(coordinates).equals(Game.FLAG))
            flagMap.setMatrix(coordinates, Bombs.getBomb(coordinates));
    }

    void setBombedExcept(Coordinates except) {
        String bombed = "bombed.png";
        if (!flagMap.getMatrixChar(except).equals(Game.FLAG))
            flagMap.setMatrix(except, bombed);
    }

    void setFlag(Coordinates coordinates) {
        if (flagMap.getMatrixChar(coordinates).equals(Game.CLOSED_CELL)) {
            flagMap.setMatrix(coordinates, Game.FLAG);
            Game.setBombsLeft(-1);
        } else if (flagMap.getMatrixChar(coordinates).equals(Game.FLAG)) {
            flagMap.setMatrix(coordinates, Game.CLOSED_CELL);
            Game.setBombsLeft(1);
        }
    }

    int getFlagsAround(Coordinates coordinates) {
        int flags = 0;
        for (Coordinates around : Game.getCoordinatesAround(coordinates)) {
            if (flagMap.getMatrixChar(around).equals(Game.FLAG)) {
                flags++;
            }
        }
        return flags;
    }

    void setWrongFlag(Coordinates coordinates) {
        String noBomb = "no_bomb.png";
        if (flagMap.getMatrixChar(coordinates).equals(Game.FLAG)) {
            flagMap.setMatrix(coordinates, noBomb);
        }
    }
}
