package city.commands;

import city.manager.CollectionManager;

/**
 * Команда shuffle для перемешивания элементов коллекции в случайном порядке.
 */
public class ShuffleCommand implements Command{
    private CollectionManager cm;

    public ShuffleCommand(CollectionManager cm) {
        this.cm = cm;
    }

    @Override
    public String getName() {
        return "shuffle";
    }

    @Override
    public String getDescription() {
        return "Перемешать элементы коллекции в случайном порядке";
    }

    @Override
    public String execute(String[] args, Object obj) {
        return cm.shuffle();
    }
}
