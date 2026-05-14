package city.manager;

import city.model.*;

import java.io.*;
import java.util.*;
import java.util.function.Predicate;

public class InputManager {
    private final BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
    private final Stack<BufferedReader> scriptStack = new Stack<>();
    private final Deque<String> scriptNames = new ArrayDeque<>();
    private static final int MAX_SCRIPT_DEPTH = 10;

    public String readLine() throws IOException {
        while (true) {
            if (!scriptStack.isEmpty()) {
                BufferedReader current = scriptStack.peek();
                String line = current.readLine();

                if (line == null) {
                    popScript();
                    continue;
                }

                System.out.println("> " + line);
                return line;
            }
            return consoleReader.readLine();
        }
    }

    public void pushScript(String fileName) throws IOException {
        if (scriptStack.size() >= MAX_SCRIPT_DEPTH) {
            throw new IllegalStateException(
                    "Слишком глубокая вложенность скриптов! Максимум: " + MAX_SCRIPT_DEPTH
            );
        }

        if (scriptNames.contains(fileName)) {
            throw new IllegalArgumentException("Обнаружена рекурсия скриптов!");
        }

        scriptStack.push(new BufferedReader(new FileReader(fileName)));
        scriptNames.push(fileName);
    }

    public void popScript() throws IOException {
        if (!scriptStack.isEmpty()) {
            BufferedReader reader = scriptStack.pop();
            reader.close();
            scriptNames.pop();
        }
    }

    public boolean hasNext() {
        try {
            if (!scriptStack.isEmpty()) {
                BufferedReader current = scriptStack.peek();
                current.mark(1);
                int c = current.read();
                current.reset();
                return c != -1;
            }
            return consoleReader.ready();
        } catch (IOException e) {
            return false;
        }
    }

    public boolean isReadingFromScript() {
        return !scriptStack.isEmpty();
    }

    public String readString(String message, Predicate<String> validator, String errorMessage) throws IOException {
        while (true) {
            System.out.println(message);
            String input = readLine();

            if (input == null) throw new IOException("Ввод прерван");

            if (validator.test(input)) return input;

            System.out.println(errorMessage);

            if (isReadingFromScript()) {
                throw new IllegalArgumentException("Ошибка в скрипте: " + errorMessage);
            }
        }
    }

    public int readInt(String message, Predicate<Integer> validator, String errorMessage) throws IOException {
        while (true) {
            System.out.println(message);
            String input = readLine();

            try {
                int value = Integer.parseInt(input);
                if (validator.test(value)) return value;
            } catch (NumberFormatException ignored) {}

            System.out.println(errorMessage);

            if (isReadingFromScript()) {
                throw new IllegalArgumentException("Ошибка в скрипте: " + errorMessage);
            }
        }
    }

    public double readDouble(String message, Predicate<Double> validator, String errorMessage) throws IOException {
        while (true) {
            System.out.println(message);
            String input = readLine();

            try {
                double value = Double.parseDouble(input);
                if (validator.test(value)) return value;
            } catch (NumberFormatException ignored) {}

            System.out.println(errorMessage);

            if (isReadingFromScript()) {
                throw new IllegalArgumentException("Ошибка в скрипте: " + errorMessage);
            }
        }
    }

    public float readFloat(String message, Predicate<Float> validator, String errorMessage) throws IOException {
        while (true) {
            System.out.println(message);
            String input = readLine();

            try {
                float value = Float.parseFloat(input);
                if (validator.test(value)) return value;
            } catch (NumberFormatException ignored) {}

            System.out.println(errorMessage);

            if (isReadingFromScript()) {
                throw new IllegalArgumentException("Ошибка в скрипте: " + errorMessage);
            }
        }
    }

    public City readCity() throws IOException {
        String name = readString(
                "Введите название города:",
                value -> value != null && !value.trim().isEmpty(),
                "Название города не может быть пустым"
        );

        float x = readFloat(
                "Введите координату x (максимум 959):",
                value -> value <= 959,
                "x должен быть <= 959"
        );

        int y = readInt(
                "Введите координату y (максимум 332):",
                value -> value <= 332,
                "y должен быть <= 332"
        );

        Coordinates coordinates = new Coordinates(x, y);

        float area = readFloat(
                "Введите площадь города (> 0):",
                value -> value > 0,
                "Площадь должна быть больше 0"
        );

        int population = readInt(
                "Введите население города (> 0):",
                value -> value > 0,
                "Население должно быть больше 0"
        );

        double metersAboveSeaLevel = readDouble(
                "Введите высоту над уровнем моря:",
                value -> true,
                "Введите корректное число"
        );

        double agglomeration = readDouble(
                "Введите агломерацию:",
                value -> true,
                "Введите корректное число"
        );

        Climate climate = readClimate();

        Government government = readGovernment();

        int governorAge = readInt(
                "Введите возраст губернатора (> 0):",
                value -> value > 0,
                "Возраст должен быть больше 0"
        );

        int governorHeight = readInt(
                "Введите рост губернатора (> 0):",
                value -> value > 0,
                "Рост должен быть больше 0"
        );

        Human governor = new Human(governorAge, governorHeight);

        return new City(
                name,
                coordinates,
                area,
                population,
                metersAboveSeaLevel,
                agglomeration,
                climate,
                government,
                governor
        );
    }

    private Climate readClimate() throws IOException {
        while (true) {
            System.out.println("Введите климат: " + Arrays.toString(Climate.values()));
            String input = readLine();

            try {
                return Climate.valueOf(input.trim().toUpperCase());
            } catch (Exception e) {
                System.out.println("Некорректный климат");

                if (isReadingFromScript()) {
                    throw new IllegalArgumentException("Ошибка в скрипте: некорректный климат");
                }
            }
        }
    }

    private Government readGovernment() throws IOException {
        while (true) {
            System.out.println("Введите форму правления или пустую строку для null: " + Arrays.toString(Government.values()));
            String input = readLine();

            if (input == null || input.trim().isEmpty()) {
                return null;
            }

            try {
                return Government.valueOf(input.trim().toUpperCase());
            } catch (Exception e) {
                System.out.println("Некорректная форма правления");

                if (isReadingFromScript()) {
                    throw new IllegalArgumentException("Ошибка в скрипте: некорректная форма правления");
                }
            }
        }
    }
}