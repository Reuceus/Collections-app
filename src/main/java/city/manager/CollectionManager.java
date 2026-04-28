package city.manager;

import city.model.City;
import city.model.Government;

import java.util.*;

/**
 * Класс CollectionManager управляет коллекцией объектов City,
 * предоставляя методы для добавления, удаления, обновления,
 * фильтрации и сортировки элементов.
 */
public class CollectionManager {
    private ArrayList<City> cities;
    private Date initializationDate;

    public CollectionManager() {
        cities = new ArrayList<>();
        initializationDate = new Date();
    }

    public CollectionManager(ArrayList<City> cities) {
        this.cities = cities;
        initializationDate = new Date();
    }

    public void add(City city) {
        cities.add(city);
        Collections.sort(cities);
    }

    public boolean removeById(int id) {
        return cities.removeIf(city -> city.getId() == id);
    }

    public void clear() {
        cities.clear();
    }

    public String getInfo() {
        return "Тип: " + cities.getClass().getName() + "\nДата инициализации: " + initializationDate + "\nКоличество элементов: " + cities.size();
    }

    public ArrayList<City> getAll() {
        return cities;
    }

    public City getById(int id) {
        for (City city : cities) {
            if (city.getId() == id) {
                return city;
            }
        }
        return null;
    }

    public String update(int id, City newCity) {
        for (int i = 0; i < cities.size(); i++) {
            if (cities.get(i).getId() == id) {
                newCity.setId(id);
                cities.set(i, newCity);
                return "Элемент обновлён";
            }
        }
        return "Элемент с таким id не найден";
    }

    public void removeFirst() {
        if(cities.isEmpty()) {
            System.out.println("Коллекция пуста");
            return;
        }

        cities.remove(0);
        System.out.println("Первый элемент удален");
    }

    public String shuffle() {
        if (cities.isEmpty()) {
            return "Коллекция пуста";
        }

        Collections.shuffle(cities);
        return "Коллекция перемешана";
    }

    public String reorder() {
        if (cities.isEmpty()) {
            return "Коллекция пуста";
        }

        Collections.reverse(cities);
        return "Коллекция развернута";
    }

    public String filterByGovernment(Government government) {
        if (cities.isEmpty()) {
            return "Коллекция пуста";
        }

        return cities.stream()
                .filter(c -> c.getGovernment() == government)
                .map(City::toString)
                .collect(java.util.stream.Collectors.joining("\n"));
    }

    public String printDescending() {
        if (cities.isEmpty()) {
            return "Коллекция пуста";
        }

        return cities.stream()
                .sorted(Comparator.reverseOrder())
                .map(City::toString)
                .reduce("", (a, b) -> a + b + "\n");
    }

    public String show() {
        if (cities.isEmpty()) {
            return "Коллекция пуста";
        }

        StringBuilder sb = new StringBuilder();

        for (City city : cities) {
            sb.append(city).append("\n");
        }

        return sb.toString();
    }
}
