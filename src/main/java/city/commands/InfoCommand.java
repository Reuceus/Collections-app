package city.commands;

import city.manager.CollectionManager;

/**
 * Команда info для вывода информации о коллекции городов.
 */
public class InfoCommand implements Command {
    private CollectionManager manager;

    public InfoCommand(CollectionManager manager) {
        this.manager = manager;
    }

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getDescription() {
        return "Вывести информацию о коллекции";
    }

    @Override
    public void execute(String[] args) {
        System.out.println(manager.getInfo());
    }
}
