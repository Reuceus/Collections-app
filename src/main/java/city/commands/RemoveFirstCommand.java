package city.commands;

import city.manager.CollectionManager;

/**
 * Команда remove_first для удаления первого элемента из коллекции.
 */
public class RemoveFirstCommand implements Command{
    private CollectionManager cm;

    public RemoveFirstCommand(CollectionManager cm) {
        this.cm = cm;
    }

    @Override
    public String getName() {
        return  "remove_first";
    }

    @Override
    public String getDescription() {
        return "Удалить первый элемент из коллекции";
    }

    @Override
    public void execute(String[] args) {
        if (args.length > 0) {
            System.out.println("Команда не принимает аргументы");
            return;
        }

        cm.removeFirst();
    }
}
