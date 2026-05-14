package city.commands;

import city.manager.CollectionManager;
import city.manager.FileManager;

/**
 * Команда save для сохранения текущей коллекции городов в файл.
 */
public class SaveCommand implements Command{
    private CollectionManager collectionManager;

    public SaveCommand(CollectionManager cm) {
        this.collectionManager = cm;
    }

    @Override
    public String getName() {
        return "save";
    }

    @Override
    public String getDescription() {
        return "Сохранить коллекцию в файл";
    }

    @Override
    public String execute(String[] args, Object obj) {
        this.collectionManager.save();
        return "Сохранено";
    }
}
