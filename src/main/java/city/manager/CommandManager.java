package city.manager;

import city.commands.Command;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {

    private final Map<String, Command> commands = new HashMap<>();
    private final InputManager inputManager;
    private boolean running = true;

    public CommandManager(InputManager inputManager) {
        this.inputManager = inputManager;
    }

    public void register(Command command) {
        commands.put(command.getName(), command);
    }

    public String execute(String commandName, Object obj, String[] args) {

        if (commandName == null || commandName.trim().isEmpty()) {
            return "Пустая команда";
        }

        Command command = commands.get(commandName);

        if (command == null) {
            return "Неизвестная команда: " + commandName;
        }

        try {
            String result = command.execute(args, obj);

            if (result != null && result.startsWith("EXEC_SCRIPT:")) {
                String fileName = result.substring("EXEC_SCRIPT:".length()).trim();
                return executeScript(fileName);
            }

            return result;

        } catch (Exception e) {
            return "Ошибка выполнения команды: " + e.getMessage();
        }
    }

    private String executeScript(String fileName) {

        try {
            inputManager.pushScript(fileName);

            StringBuilder sb = new StringBuilder();
            sb.append("Скрипт ").append(fileName).append(" выполняется\n");

            while (inputManager.hasNext()) {

                String line = inputManager.readLine();

                if (line == null || line.trim().isEmpty()) continue;

                String[] parts = line.trim().split("\\s+");
                String cmdName = parts[0];

                String[] args = new String[Math.max(0, parts.length - 1)];
                System.arraycopy(parts, 1, args, 0, args.length);

                String result = execute(cmdName, null, args);

                sb.append(result).append("\n");
            }

            inputManager.popScript();

            sb.append("Скрипт ").append(fileName).append(" завершён");

            return sb.toString();

        } catch (Exception e) {
            return "Ошибка выполнения скрипта: " + e.getMessage();
        }
    }

    public boolean isRunning() {
        return running;
    }

    public void stop() {
        running = false;
    }

    public String showHelp() {

        StringBuilder sb = new StringBuilder();

        for (Command cmd : commands.values()) {
            sb.append(cmd.getName())
                    .append(" - ")
                    .append(cmd.getDescription())
                    .append("\n");
        }

        return sb.toString();
    }
}