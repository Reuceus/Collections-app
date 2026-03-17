package city.manager;

import java.io.*;
import java.util.*;
import java.util.function.Predicate;

/**
 * Класс InputManager управляет вводом данных из консоли и выполнения скриптов.
 * Позволяет читать строки с консоли или из файлов скриптов в стеке.
 * Поддерживает проверку рекурсии скриптов и вложенные вызовы.
 */
public class InputManager {
    private final BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
    private final Stack<BufferedReader> scriptStack = new Stack<>();
    private Deque<String> scriptNames = new ArrayDeque<>();
    private static final int MAX_SCRIPT_DEPTH = 10;

    public String readLine() throws IOException {
        while (true) {
            if (!scriptStack.isEmpty()) {
                BufferedReader current = scriptStack.peek();
                String line = current.readLine();

                if (line == null) {
                    current.close();
                    scriptStack.pop();
                    scriptNames.pop();
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
}