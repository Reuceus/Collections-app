package city.commands;

import city.manager.CollectionManager;
import city.manager.InputManager;
import city.model.*;

/**
 * Команда add для добавления нового города в коллекцию.
 * При выполнении команды пользователь вводит данные для нового города,
 * включая название, координаты, площадь, население, климат, форму правления
 * и параметры губернатора.
 */
public class AddCommand implements Command{
    private CollectionManager manager;
    private InputManager inputManager;

    public AddCommand(CollectionManager manager, InputManager inputManager) {
        this.manager = manager;
        this.inputManager = inputManager;
    }

    @Override
    public String getName() {
        return "add";
    }

    @Override
    public String getDescription(){
        return "Добавить элемент в коллекцию";
    }

    @Override
    public void execute(String[] args) {
        try {
            String name = inputManager.readString("Название: ", s -> !s.trim().isEmpty(), "Название не должно быть пустым");
            float x = inputManager.readFloat("Координата Х (int): ", p -> p < 960, "Координата должна быть меньше 960");
            int y = inputManager.readInt("Координата Y (int): ", p -> p < 333, "Координата должна быть меньше 333");
            Coordinates c = new Coordinates(x, y);
            float area = inputManager.readFloat("Площадь (float): ", p -> p > 0, "Значение поля должно быть больше 0");
            int population = inputManager.readInt("Население (int): ", p -> p > 0 && p != null, "Значение поля должно быть больше 0");
            double metersAboveSeaLevel = inputManager.readDouble("Высота над уровнем моря (double): ", p -> p != null, "Значение поля не может быть равно null");
            double agglomeration = inputManager.readDouble("Агломерация (double): ", p -> p != null, "Значение поля не может быть равно null");
            Climate climate = Climate.valueOf(inputManager.readString("Климат (MONSOON, OCEANIC, SUBARCTIC, DESERT",
                            s -> s.equals("MONSOON") || s.equals("OCEANIC") ||s.equals("SUBARCTIC") ||s.equals("DESERT"), "Введите один из данных вариантов"));
            Government government = Government.valueOf(inputManager.readString("Форма правления (IDEOCRACY, MERITOCRACY, ...): ",
                    s -> s.equals("IDEOCRACY") || s.equals("CORPORATOCRACY") || s.equals("NOOCRACY") || s.equals("PATRIARCHY") || s.equals("TECHNOCRACY"), "Введите один из вариантов"));
            int age = inputManager.readInt("Возраст губернатора (int): ", p -> p > 0, "Значение поля должно быть больше 0");
            int height = inputManager.readInt("Рост губернатора (int): ", p -> p > 0, "Значение поля должно быть больше 0");

            Human governor = new Human(age, height);

            City city = new City(name, c, area, population, metersAboveSeaLevel, agglomeration, climate, government, governor);

            manager.add(city);

            System.out.println("Город добавлен!");
        } catch (Exception e) {
            System.out.println("Ошибка ввода данных!");
        }

    }
}
