package city.server;

import city.client.Client;
import city.commands.*;
import city.manager.*;

public class ServerMain {
    public static void main(String[] args) {
        InputManager inputManager = new InputManager();
        CommandManager commandManager = new CommandManager(inputManager);
        CollectionManager collectionManager = new CollectionManager();
        String fileName = System.getenv("CITY_FILE");
        FileManager fileManager = new FileManager(fileName);

        commandManager.register(new HelpCommand(commandManager));
        commandManager.register(new ShowCommand(collectionManager));
        commandManager.register(new AddCommand(collectionManager));
        commandManager.register(new AverageOfAgglomerationCommand(collectionManager));
        commandManager.register(new ClearCommand(collectionManager));
        commandManager.register(new ExecuteScriptCommand());
        commandManager.register(new ExitCommand(commandManager));
        commandManager.register(new FilterByGovernment(collectionManager));
        commandManager.register(new InfoCommand(collectionManager));
        commandManager.register(new PrintDescendingCommand(collectionManager));
        commandManager.register(new RemoveByIdCommand(collectionManager));
        commandManager.register(new RemoveFirstCommand(collectionManager));
        commandManager.register(new ReorderCommand(collectionManager));
        commandManager.register(new SaveCommand(collectionManager, fileManager));
        commandManager.register(new ShowCommand(collectionManager));
        commandManager.register(new ShuffleCommand(collectionManager));
        commandManager.register(new UpdateCommand(collectionManager));

        new Server(12345, commandManager).start();
    }
}
