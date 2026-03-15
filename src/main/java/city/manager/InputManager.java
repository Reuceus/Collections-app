package city.manager;

import java.io.*;
import java.util.Stack;
import java.util.function.Predicate;

/**
 * Класс InputManager управляет вводом данных из консоли и выполнения скриптов.
 * Позволяет читать строки с консоли или из файлов скриптов в стеке.
 */
public class InputManager {
    private final BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
    private final Stack<BufferedReader> scriptStack = new Stack<>();

    public String readLine() throws IOException {
        if (!scriptStack.isEmpty()) {
            String line = scriptStack.peek().readLine();
            if (line == null) {
                scriptStack.pop().close();
                return readLine();
            }

            System.out.println("> " + line);
            return line;
        }

        return consoleReader.readLine();
    }

    public void pushScript(String fileName) throws FileNotFoundException {
        scriptStack.push(new BufferedReader(new FileReader(fileName)));
    }

    public boolean isScriptRunning(String fileName) {
        return scriptStack.stream().anyMatch(r -> r.toString().contains(fileName));
    }

    public boolean isReadingFromScript() {
        return !scriptStack.isEmpty();
    }

    public String readString(String message, Predicate<String> validator, String errorMessage) throws IOException {
        while (true) {
            System.out.println(message);
            String input = readLine();

            if (input == null) throw new IOException("Ввод прерван");

            if (validator.test(input)) {
                return input;
            }

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

                if (validator.test(value)) {
                    return value;
                }
            } catch (NumberFormatException e) {}
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

                if (validator.test(value)) {
                    return value;
                }
            } catch (NumberFormatException e) {}
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

                if (validator.test(value)) {
                    return value;
                }
            } catch (NumberFormatException e) {}
            System.out.println(errorMessage);


            if (isReadingFromScript()) {
                throw new IllegalArgumentException("Ошибка в скрипте: " + errorMessage);
            }
        }
    }
}
