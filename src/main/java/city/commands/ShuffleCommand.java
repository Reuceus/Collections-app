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
    public void execute(String[] args) {
        if (args.length > 0) {
            System.out.println("Команда не принимает аргументов");
        }

        if (cm.getAll().isEmpty()) {
            System.out.println("Коллекция пуста");
        return;
        }

        cm.shuffle();
        System.out.println("Коллекция перемешана");
    }
}
