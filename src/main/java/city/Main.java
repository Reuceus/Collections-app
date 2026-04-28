/*
package city;

import city.manager.*;
import city.commands.*;
import city.model.City;
import city.utils.IdGenerator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Главный класс приложения для работы с коллекцией городов.
 * Отвечает за инициализацию коллекции, загрузку данных из файла,
 * регистрацию команд и обработку ввода пользователя.
 */
/*
public class Main {
    public static void main(String[] args) {
        String fileName = System.getenv("CITY_FILE");

        if (fileName == null) {
            System.out.println("Переменная окружения CITY_FILE не установлена");
            System.exit(1);
        }

        FileManager fileManager = new FileManager(fileName);
        ArrayList<City> loadedCities = fileManager.load();
        if (loadedCities == null) {
            loadedCities = new ArrayList<>();
        }
        CollectionManager collectionManager = new CollectionManager(loadedCities);
        int maxId = loadedCities.stream()
                .filter(Objects::nonNull)
                .map(City::getId)
                .filter(Objects::nonNull) // отбрасываем null id
                .mapToInt(Integer::intValue)
                .max()
                .orElse(0);
        IdGenerator.setNextId(maxId + 1);
        CommandManager commandManager = new CommandManager();
        InputManager inputManager = new InputManager();

        commandManager.register(new AddCommand(collectionManager));
        commandManager.register(new InfoCommand(collectionManager));
        commandManager.register(new ShowCommand(collectionManager));
        commandManager.register(new RemoveByIdCommand(collectionManager));
        commandManager.register(new ExitCommand(commandManager));
        commandManager.register(new HelpCommand(commandManager));
        commandManager.register(new SaveCommand(collectionManager, fileManager));
        commandManager.register(new ClearCommand(collectionManager));
        commandManager.register(new ExecuteScriptCommand(inputManager));
        commandManager.register(new UpdateCommand(collectionManager, inputManager));
        commandManager.register(new RemoveFirstCommand(collectionManager));
        commandManager.register(new ShuffleCommand(collectionManager));
        commandManager.register(new ReorderCommand(collectionManager));
        commandManager.register(new AverageOfAgglomerationCommand(collectionManager));
        commandManager.register(new FilterByGovernment(collectionManager));
        commandManager.register(new PrintDescendingCommand(collectionManager));

        while (commandManager.isRunning()) {
            try {
                System.out.print("> ");
                String input = inputManager.readLine();
                commandManager.execute(input, );
            } catch (IOException e) {
                System.out.println("Ошибка ввода");
            }
        }
    }
}
 */