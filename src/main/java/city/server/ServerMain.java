package city.server;

import city.commands.*;
import city.manager.*;

import java.util.logging.Logger;

public class ServerMain {
    private static final Logger logger = Logger.getLogger(ServerMain.class.getName());

    public static void main(String[] args) {
        InputManager inputManager = new InputManager();
        CommandManager commandManager = new CommandManager(inputManager);
        String fileName = System.getenv("CITY_FILE");

        if (fileName == null || fileName.isBlank()) {
            fileName = "cities.json";
            System.out.println("CITY_FILE не найден, используется файл " + fileName);
        }
        FileManager fileManager = new FileManager(fileName);
        CollectionManager collectionManager = new CollectionManager(fileManager);

        commandManager.register(new HelpCommand(commandManager));
        commandManager.register(new AddCommand(collectionManager));
        commandManager.register(new AverageOfAgglomerationCommand(collectionManager));
        commandManager.register(new ClearCommand(collectionManager));
        commandManager.register(new ExecuteScriptCommand());
        commandManager.register(new FilterByGovernment(collectionManager));
        commandManager.register(new InfoCommand(collectionManager));
        commandManager.register(new PrintDescendingCommand(collectionManager));
        commandManager.register(new RemoveByIdCommand(collectionManager));
        commandManager.register(new RemoveFirstCommand(collectionManager));
        commandManager.register(new ReorderCommand(collectionManager));
        commandManager.register(new SaveCommand(collectionManager));
        commandManager.register(new ShowCommand(collectionManager));
        commandManager.register(new ShuffleCommand(collectionManager));
        commandManager.register(new UpdateCommand(collectionManager));
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Сохранение коллекции перед завершением");
            logger.info(collectionManager.save());
        }));

        new Server(12345, commandManager).start();
    }
}
