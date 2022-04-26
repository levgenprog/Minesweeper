public class Field {
    private final String[][] matrix;

    Field(String c) {
        matrix = new String[Game.size.x][Game.size.y];
        for (Coordinates coordinates : Game.getAllCoordinates()) {
            matrix[coordinates.x][coordinates.y] = c;
        }
    }

    String getMatrixChar(Coordinates coordinates) {
        if (Coordinates.areCoordinatesInside(coordinates)) {
            return matrix[coordinates.x][coordinates.y];
        } else return null;
    }

    public void setMatrix(Coordinates coordinates, String c) {
        if (Coordinates.areCoordinatesInside(coordinates)) {
            matrix[coordinates.x][coordinates.y] = c;
        }
    }

}
