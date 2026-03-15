package city.commands;

/**
 * Интерфейс команды, которую можно выполнять через CommandManager.
 * Каждая команда должна иметь имя, описание и реализацию метода execute.
 */
public interface Command {
    String getName();
    String getDescription();
    void execute(String[] args);
}
