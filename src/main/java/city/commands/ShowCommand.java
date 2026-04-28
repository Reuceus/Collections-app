package city.commands;

import city.manager.CollectionManager;

/**
 * Команда show для вывода всех элементов коллекции городов.
 */
public class ShowCommand implements Command{
    private CollectionManager manager;

    public ShowCommand(CollectionManager manager) {
        this.manager = manager;
    }

    @Override
    public String getName() {
        return "show";
    }

    @Override
    public String getDescription() {
        return "Вывести все элементы коллекции";
    }

    @Override
    public String execute(String[] args, Object obj) {
        return manager.show();
    }
}
