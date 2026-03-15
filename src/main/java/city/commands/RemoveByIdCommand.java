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
    public void execute(String[] args) {
        if (args.length == 0) {
            System.out.println("Укажите id");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("ID должен быть числом");
            return;
        }

        if (manager.removeById(id)) {
            System.out.println("Удалено");
        } else {
            System.out.println("Элемент не найден");
        }
    }
}
