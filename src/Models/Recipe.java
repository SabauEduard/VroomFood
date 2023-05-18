package Models;

import java.util.List;

public class Recipe implements java.io.Serializable{
    private static int idCounter = 0;
    private int id;
    private String name;
    private String description;
    private Integer price;
    private Integer preparationTime;

    public Recipe(String name, String description, Integer price, Integer preparationTime) {
        this.id = idCounter++;
        this.name = name;
        this.description = description;
        this.price = price;
        this.preparationTime = preparationTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(Integer preparationTime) {
        this.preparationTime = preparationTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
