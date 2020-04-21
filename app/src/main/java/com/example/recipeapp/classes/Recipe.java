package com.example.recipeapp.classes;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.ArrayList;
import java.util.List;

import com.example.recipeapp.typeconverters.RecipeTypeConverter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "recipe")
public class Recipe {

    @PrimaryKey()
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("name")
    @Expose
    private String name;

    @TypeConverters(RecipeTypeConverter.class)
    @SerializedName("ingredients")
    @Expose
    private List<Ingridient> ingredients = null;

    @TypeConverters(RecipeTypeConverter.class)
    @SerializedName("steps")
    @Expose
    private List<Step> steps = null;

    @SerializedName("servings")
    @Expose
    private Integer servings;

    @SerializedName("image")
    @Expose
    private String image;

    //cons if needed

    public Recipe(Integer id, String name, List<Ingridient> ingredients, List<Step> steps, Integer servings, String image) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.servings = servings;
        this.image = image;
    }

    //cons end

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingridient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingridient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public Integer getServings() {
        return servings;
    }

    public void setServings(Integer servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}