package city.manager;

import city.model.City;
import city.model.Government;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

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

    public boolean update(int id, City newCity) {
        for (int i = 0; i < cities.size(); i++) {
            if (cities.get(i).getId() == id) {
                cities.set(i, newCity);
                return true;
            }
        }
        return false;
    }

    public void removeFirst() {
        if(cities.isEmpty()) {
            System.out.println("Коллекция пуста");
            return;
        }

        cities.remove(0);
        System.out.println("Первый элемент удален");
    }

    public void shuffle() {
        Collections.shuffle(cities);
    }

    public void reorder() {
        Collections.reverse(cities);
    }

    public void filterByGovernment(Government government) {
        boolean found = false;
        for (City city : cities) {
            if (city.getGovernment() == government) {
                System.out.println(city);
                found = true;
            }
        }

        if (!found) {
            System.out.println("Элементы с таким government не найдены");
        }
    }

    public void printDescending() {
        if (cities.isEmpty()) {
            System.out.println("Коллекция пуста");
            return;
        }

        List<City> sorted = new ArrayList<>(cities);
        sorted.sort(Collections.reverseOrder());

        for (City city : sorted) {
            System.out.println(city);
        }
    }
}
