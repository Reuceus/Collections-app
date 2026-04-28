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

    public AddCommand(CollectionManager manager) {
        this.manager = manager;
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
    public String execute(String[] args, Object obj) {
        if (obj == null) return "Ошибка: объект не передан";
        try {
            City city = (City) obj;
            manager.add(city);
            return "Город добавлен!";
        } catch(ClassCastException e) {
            return "Ошибка: неверный тип объекта";
        }
    }
}
