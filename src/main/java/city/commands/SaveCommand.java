package city.commands;

import city.manager.CollectionManager;
import city.manager.FileManager;

/**
 * Команда save для сохранения текущей коллекции городов в файл.
 */
public class SaveCommand implements Command{
    private CollectionManager collectionManager;
    private FileManager fileManager;

    public SaveCommand(CollectionManager cm, FileManager fm) {
        this.collectionManager = cm;
        this.fileManager = fm;
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
    public void execute(String[] args) {
        fileManager.save(collectionManager.getAll());
    }
}
