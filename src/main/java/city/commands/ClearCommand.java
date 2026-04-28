package city.commands;

import city.manager.CollectionManager;

/**
 * Команда clear для очистки всех элементов коллекции.
 */
public class ClearCommand implements Command{
    private CollectionManager cm;

    public ClearCommand(CollectionManager cm) {
        this.cm = cm;
    }

    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public String getDescription() {
        return "Очистить коллекцию";
    }

    @Override
    public String execute(String[] args, Object obj) {
        cm.clear();
        return "Коллекция очищена";
    }
}
