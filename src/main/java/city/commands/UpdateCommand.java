package city.commands;

import city.manager.CollectionManager;
import city.manager.InputManager;
import city.model.*;

/**
 * Команда update для обновления значения элемента коллекции по заданному id.
 */
public class UpdateCommand implements Command{
    private CollectionManager manager;
    private InputManager inputManager;

    public UpdateCommand(CollectionManager manager, InputManager inputManager) {
        this.manager = manager;
        this.inputManager = inputManager;
    }

    @Override
    public String getName() {
        return "update";
    }

    @Override
    public String getDescription() {
        return "Обновить значение элемента коллекции, id которого равен заданному";
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

        City oldCity = manager.getById(id);

        if (oldCity == null) {
            System.out.println("Город с таким id не найден");
            return;
        }

        try {
            System.out.print("Название: ");
            String name = inputManager.readLine();

            System.out.print("Координата Х (int): ");
            int x = Integer.parseInt(inputManager.readLine());

            System.out.print("Координата Y (int): ");
            int y = Integer.parseInt(inputManager.readLine());

            Coordinates c = new Coordinates(x, y);

            System.out.print("Площадь (float): ");
            float area = Float.parseFloat(inputManager.readLine());

            System.out.print("Население (int): ");
            int population = Integer.parseInt(inputManager.readLine());

            System.out.print("Метры над уровнем моря (double): ");
            double metersAboveSeaLevel = Double.parseDouble(inputManager.readLine());

            System.out.println("Агломерация (double): ");
            double agglomeration = Double.parseDouble(inputManager.readLine());

            System.out.print("Климат (DESERT, MONSOON, STEPPE): ");
            Climate climate = Climate.valueOf(inputManager.readLine().toUpperCase());

            System.out.print("Форма правления (IDEOCRACY, MERITOCRACY, ...): ");
            Government government = Government.valueOf(inputManager.readLine().toUpperCase());

            System.out.print("Возраст губернатора (int): ");
            int age = Integer.parseInt(inputManager.readLine());

            System.out.print("Рост губернатора (double): ");
            int height = Integer.parseInt(inputManager.readLine());

            Human governor = new Human(age, height);

            City newCity = new City(name, c, area, population, metersAboveSeaLevel, agglomeration, climate, government, governor);
            newCity.setId(id);
            manager.update(id, newCity);
            System.out.println("Город обновлён!");

        } catch (Exception e) {
            System.out.println("Ошибка ввода данных!");
        }
    }
}
