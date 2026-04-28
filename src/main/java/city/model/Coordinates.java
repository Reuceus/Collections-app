package city.model;

import java.io.Serializable;

/**
 * Класс Coordinates хранит координаты города: x и y.
 * Поле x имеет максимум 959, поле y имеет максимум 332 и не может быть null.
 */
public class Coordinates implements Serializable {
    private float x; //Максимальное значение поля: 959
    private Integer y; //Максимальное значение поля: 332, Поле не может быть null

    public Coordinates(float x, Integer y) {
        if (x > 959) {
            throw new IllegalArgumentException("x must be <= 959");
        }
        if (y == null || y > 332) {
            throw new IllegalArgumentException("y must be <= 332 and not null");
        }
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Coordinates{x=" + x + ", y=" + y + '}';
    }
}
