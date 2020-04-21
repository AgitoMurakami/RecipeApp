package com.example.recipeapp.typeconverters;

import androidx.room.TypeConverter;

import com.example.recipeapp.classes.Ingridient;
import com.example.recipeapp.classes.Step;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecipeTypeConverter {

    @TypeConverter
    public static List<Ingridient> stringToIngridientObjectList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Ingridient>>() {}.getType();
        return new Gson().fromJson(data, listType);
    }

    @TypeConverter
    public static String ingridientObjectListToString(List<Ingridient> ingridientsObjects) {

        Gson gson = new Gson();
        String json = gson.toJson(ingridientsObjects);
        return json;
    }

    @TypeConverter
    public static List<Step> stringToStepObjectList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Step>>() {}.getType();
        return new Gson().fromJson(data, listType);
    }

    @TypeConverter
    public static String stepObjectListToString(List<Step> stepsObjects) {
        Gson gson = new Gson();
        String json = gson.toJson(stepsObjects);
        return json;
    }
}
