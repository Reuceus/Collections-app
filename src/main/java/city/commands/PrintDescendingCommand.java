package city.commands;

import city.manager.CollectionManager;

/**
 * Команда print_descending для вывода всех элементов коллекции
 * в порядке убывания.
 */
public class PrintDescendingCommand implements Command{
    private final CollectionManager cm;

    public PrintDescendingCommand(CollectionManager cm) {
        this.cm = cm;
    }

    @Override
    public String getName() {
        return "print_descending";
    }

    @Override
    public String getDescription() {
        return "Вывести элементы коллекции в порядке убывания";
    }

    @Override
    public String execute(String[] args, Object obj) {
        return cm.printDescending();
    }
}
