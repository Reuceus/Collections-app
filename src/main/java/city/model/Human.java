package city.model;

/**
 * Класс Human представляет губернатора города с указанием его возраста и роста.
 * Поле age должно быть больше 0.
 * Поле height должно быть больше 0.
 */
public class Human {
    private Integer age; //Значение поля должно быть больше 0
    private int height; //Значение поля должно быть больше 0

    public Human(Integer age, int height) {
        if (age <= 0) {
            throw new IllegalArgumentException("age must be > 0");
        }
        if (height <= 0) {
            throw new IllegalArgumentException("height must be >0");
        }

        this.age = age;
        this.height = height;
    }

    public Integer getAge() {
        return age;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return "Human{age=" + age + ", height=" + height + '}';
    }
}
