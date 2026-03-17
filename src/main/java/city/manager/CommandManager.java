package city.manager;

import city.commands.Command;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Класс CommandManager управляет регистрацией и выполнением команд,
 * отслеживает состояние работы программы и выполняемые скрипты.
 */
public class CommandManager {
    private Map<String, Command> commands = new HashMap<>();
    private boolean running = true;

    public void register(Command command) {
        commands.put(command.getName(), command);
    }

    public void  execute(String input) {
        if (input == null || input.trim().isEmpty()) {
            return;
        }
        String[] parts = input.trim().split("\\s+");
        String commandName = parts[0];
        Command command = commands.get(commandName);

        if (command == null) {
            System.out.println("Неизвестная команда");
            return;
        }

        String[] args = new String[parts.length - 1];
        System.arraycopy(parts, 1, args, 0, args.length);
        command.execute(args);
    }

    public boolean isRunning() {
        return running;
    }

    public void stop() {
        running = false;
    }

    public void showHelp() {
        commands.values().forEach(cmd -> System.out.println(cmd.getName() + " - " + cmd.getDescription()));
    }

}
