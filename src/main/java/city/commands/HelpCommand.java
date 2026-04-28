package city.commands;

import city.manager.CollectionManager;
import city.manager.CommandManager;

/**
 * Команда help для отображения списка всех доступных команд.
 */
public class HelpCommand implements Command{
    private CommandManager manager;

    public HelpCommand(CommandManager manager) {
        this.manager = manager;
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "Показать список команд";
    }

    @Override
    public String execute(String[] args, Object obj) {
        return manager.showHelp();
    }
}
