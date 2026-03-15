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
    public void execute(String[] args) {
        if(args.length > 0) {
            System.out.println("Команда не принимает аргументы");
            return;
        }

        cm.clear();
        System.out.println("Коллекция очищена");
    }
}
