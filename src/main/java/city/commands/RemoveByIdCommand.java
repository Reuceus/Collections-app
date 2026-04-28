package city.commands;

import city.manager.CollectionManager;

/**
 * Команда remove_by_id для удаления элемента коллекции по его уникальному идентификатору (id).
 */
public class RemoveByIdCommand implements Command{
    private CollectionManager manager;

    public RemoveByIdCommand(CollectionManager manager) {
        this.manager = manager;
    }

    @Override
    public String getName() {
        return "remove_by_id";
    }

    @Override
    public String getDescription() {
        return "Удалить элемент по id";
    }

    @Override
    public String execute(String[] args, Object obj) {
        if (args.length == 0) {
            return "Ошибка: не указан id";
        }

        try {
            int id = Integer.parseInt(args[0]);
            manager.removeById(id);
            return "Элемент удалён";
        } catch (NumberFormatException e) {
            return "Ошибка: id должен быть числом";
        }
    }
}
