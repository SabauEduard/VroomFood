package Models;

import java.util.List;

public class Recipe {
    private static int idCounter = 0;
    private int id;
    private String name;
    private String description;
    private Integer price;
    private Integer preparationTime;
    private List<String> ingredientList;

    public Recipe(String name, String description, Integer price, Integer preparationTime, List<String> ingredientList) {
        this.id = idCounter++;
        this.name = name;
        this.description = description;
        this.price = price;
        this.preparationTime = preparationTime;
        this.ingredientList = ingredientList;
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

    public List<String> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(List<String> ingredientList) {
        this.ingredientList = ingredientList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
