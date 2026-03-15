package city.commands;

import city.manager.CollectionManager;
import city.model.City;

/**
 * Команда average_of_agglomeration для вычисления среднего значения
 * поля agglomeration всех городов в коллекции.
 */
public class AverageOfAgglomerationCommand implements Command{
    private CollectionManager cm;

    public AverageOfAgglomerationCommand(CollectionManager cm) {
        this.cm = cm;
    }

    @Override
    public String getName() {
        return "average_of_agglomeration";
    }

    @Override
    public String getDescription() {
        return "Вывести среднее значение поля agglomeration для всех элементов коллекции";
    }

    @Override
    public void execute(String[] args) {
        if (args.length > 0) {
            System.out.println("Команда не принимает аргументы");
            return;
        }

        if (cm.getAll().isEmpty()) {
            System.out.println("Коллекция пуста");
            return;
        }

        double sum = 0;
        for (City city : cm.getAll()) {
            sum += city.getAgglomeration();
        }

        System.out.println("Среднее значение agglomeration: " + sum / cm.getAll().size());
    }
}
