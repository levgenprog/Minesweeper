import java.util.Objects;
import java.util.Random;

public class Coordinates {
    public int x;
    public int y;
    private final static Random rnd = new Random();


    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public static Coordinates getRandomCoordinates() {
        return new Coordinates(rnd.nextInt(Game.size.x), rnd.nextInt(Game.size.y));
    }

    public static boolean areCoordinatesInside(Coordinates coordinates) {
        return coordinates.x >= 0 && coordinates.x < Game.size.x &&
                coordinates.y >= 0 && coordinates.y < Game.size.y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return x == that.x &&
                y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}