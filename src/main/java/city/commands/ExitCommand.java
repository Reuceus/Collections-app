package city.commands;

import city.manager.CommandManager;

/**
 * Команда exit для завершения работы программы через CommandManager.
 */
public class ExitCommand implements Command{
    private CommandManager commandManager;

    public ExitCommand(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    @Override
    public String getName() {
        return "exit";
    }

    @Override
    public String getDescription() {
        return "Завершить программы";
    }

    @Override
    public void execute(String[] args) {
        System.out.println("Завершение программы");
        commandManager.stop();
    }
}
