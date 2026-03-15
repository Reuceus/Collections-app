package city.manager;

import city.model.City;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Класс FileManager управляет загрузкой и сохранением коллекции City в файл и из файла.
 */
public class FileManager {
    private final String fileName;
    private final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();

    public FileManager(String fileName) {
        this.fileName = fileName;
    }

    public ArrayList<City> load() {
        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(fileName))) {
            Type collectionType = new TypeToken<ArrayList<City>>() {}.getType();
            ArrayList<City> cities = gson.fromJson(reader, collectionType);

            if (cities == null) {
                return new ArrayList<>();
            }

            return cities;
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден, создается новая коллекция");
            return new ArrayList<>();
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла");
            return new ArrayList<>();
        }
    }
    public void save(ArrayList<City> cities) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(gson.toJson(cities));
            System.out.println("Коллекция сохранена");
        } catch (IOException e) {
            System.out.println("Ошибка сохранения файла");
        }
    }
}
