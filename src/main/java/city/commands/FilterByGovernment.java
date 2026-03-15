package city.commands;

import city.manager.CollectionManager;
import city.manager.FileManager;
import city.model.City;
import city.model.Government;

/**
 * Команда filter_by_government для вывода городов, у которых поле government
 * совпадает с указанным значением.
 */
public class FilterByGovernment implements Command{
    private CollectionManager cm;

    public FilterByGovernment(CollectionManager cm) {
        this.cm = cm;
    }

    @Override
    public String getName() {
        return "filter_by_government";
    }

    @Override
    public String getDescription() {
        return "Вывести элементы, значение поля government которых равно заданному";
    }

    @Override
    public void execute(String[] args) {
        if (args.length != 1) {
            System.out.println("Использование: filter_by_government government");
            return;
        }

        if (cm.getAll().isEmpty()) {
            System.out.println("Коллекция пуста");
        }

        try {
            Government gov = Government.valueOf(args[0].toUpperCase());
            cm.filterByGovernment(gov);
        } catch (IllegalArgumentException e) {
            System.out.println("Неверное значение government");
        }
    }
}
