package city.model;

import city.manager.InputManager;
import city.utils.IdGenerator;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

/**
 * Класс City представляет город с его характеристиками:
 * уникальным идентификатором, названием, координатами, населением,
 * площадью, климатом, формой правления, губернатором и другими параметрами.
 */
public class City implements Comparable<City>, Serializable {
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.util.Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private float area; //Значение поля должно быть больше 0
    private Integer population; //Значение поля должно быть больше 0, Поле не может быть null
    private Double metersAboveSeaLevel;
    private double agglomeration;
    private Climate climate; //Поле не может быть null
    private Government government; //Поле может быть null
    private Human governor; //Поле не может быть null

    public City(String name, Coordinates coordinates, float area, Integer population, Double metersAboveSeaLevel, double agglomeration, Climate climate, Government government, Human governor) {
        if (name == null || name.length() == 0) {
            throw new IllegalArgumentException("name must be not null and not empty");
        }
        if (coordinates == null) {
            throw new IllegalArgumentException("coordinates must be not null");
        }
        if (area <= 0) {
            throw new IllegalArgumentException("area must be > 0");
        }
        if (population <= 0 || population == null) {
            throw new IllegalArgumentException("population must be > 0 and not null");
        }
        if (climate == null) {
            throw new IllegalArgumentException("climate must be not null");
        }
        if (governor == null) {
            throw new IllegalArgumentException("governor must be not null");
        }

        this.id = IdGenerator.generateId();
        this.creationDate = new Date();

        this.name = name;
        this.coordinates = coordinates;
        this.area = area;
        this.population = population;
        this.metersAboveSeaLevel = metersAboveSeaLevel;
        this.agglomeration = agglomeration;
        this.climate = climate;
        this.government = government;
        this.governor = governor;
    }

    public City() {}

    @Override
    public int compareTo(City other) {
        return this.population.compareTo(other.population);
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Integer getPopulation() {
        return this.population;
    }

    public double getAgglomeration() {
        return this.agglomeration;
    }

    public Government getGovernment() {
        return this.government;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", population=" + population +
                ", climate=" + climate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if(!(o instanceof City)) {
            return false;
        }
        City city = (City) o;
        return this.id != null && this.id.equals(city.id);
    }

    public void setId(int id) {
        this.id = id;
    }
}
