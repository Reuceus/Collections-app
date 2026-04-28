package city.commands;

import city.manager.CollectionManager;
import city.model.City;

public class UpdateCommand implements Command {

    private final CollectionManager manager;

    public UpdateCommand(CollectionManager manager) {
        this.manager = manager;
    }

    @Override
    public String getName() {
        return "update";
    }

    @Override
    public String getDescription() {
        return "Обновить значение элемента коллекции по id";
    }

    @Override
    public String execute(String[] args, Object obj) {

        if (args == null || args.length == 0) {
            return "Ошибка: не указан id";
        }

        if (obj == null) {
            return "Ошибка: объект не передан";
        }

        try {
            int id = Integer.parseInt(args[0]);

            if (!(obj instanceof City)) {
                return "Ошибка: неверный тип объекта";
            }

            City city = (City) obj;

            return manager.update(id, city);

        } catch (NumberFormatException e) {
            return "Ошибка: id должен быть числом";
        }
    }
}