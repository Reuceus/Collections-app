package city.commands;

import city.manager.CollectionManager;

/**
 * Команда reorder для изменения порядка элементов коллекции на обратный.
 */
public class ReorderCommand implements Command{
    private CollectionManager cm;

    public ReorderCommand(CollectionManager cm) {
        this.cm = cm;
    }

    @Override
    public String getName() {
        return "reorder";
    }

    @Override
    public String getDescription() {
        return "Отсортировать коллекцию в порядке, обратном нынешнему";
    }

    @Override
    public void execute(String[] args) {
        if (args.length > 0) {
            System.out.println("Команда не принимает аргументы");
            return;
        }

        cm.reorder();
        System.out.println("Порядок изменен");
    }
}
