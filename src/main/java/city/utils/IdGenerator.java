package city.utils;

/**
 * Утилитный класс IdGenerator предназначен для генерации уникальных идентификаторов.
 * Класс нельзя инстанцировать.
 */
public final class IdGenerator {
    private static int currentId = 1;
    private IdGenerator() {}

    public static synchronized int generateId() {
        return currentId++;
    }

    public static synchronized void setNextId(int nextId) {
        if (nextId <= 0) {
            throw new IllegalArgumentException("ID должен быть больше 0");
        }
        currentId = nextId;
    }
}