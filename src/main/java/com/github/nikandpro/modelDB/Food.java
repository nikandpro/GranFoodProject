package com.github.nikandpro.modelDB;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "food")
public class Food {
    @DatabaseField(generatedId = true, columnName = "foodID")
    private int id;
    @DatabaseField(columnName = "name")
    private String name;
    @DatabaseField(columnName = "description")
    private String description;
    @DatabaseField(columnName = "ingredients")
    private String ingredients;
    @DatabaseField(columnName = "portions")
    private String portions;
    @DatabaseField(columnName = "cookingTime")
    private String cookingTime;
    @DatabaseField( columnName = "price")
    private String price;
    @DatabaseField(columnName = "have")
    private String have;
    @DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, columnName = "user")
    private User user;
    @DatabaseField(columnName = "rating")
    private int rating;
    @DatabaseField(columnName = "location")
    private String location;


    public Food() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPortions() {
        return portions;
    }

    public void setPortions(String portions) {
        this.portions = portions;
    }

    public String getCookingTime() {
        return cookingTime;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setCookingTime(String cookingTime) {
        this.cookingTime = cookingTime;
    }

    public String getHave() {
        return have;
    }

    public void setHave(String have) {
        this.have = have;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Food{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", ingredients='" + ingredients + '\'' +
                ", portions='" + portions + '\'' +
                ", cookingTime='" + cookingTime + '\'' +
                ", price='" + price + '\'' +
                ", have='" + have + '\'' +
                ", user=" + user +
                ", rating=" + rating +
                '}';
    }
}
