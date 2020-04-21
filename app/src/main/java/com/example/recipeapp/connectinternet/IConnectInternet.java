package com.example.recipeapp.connectinternet;

import com.example.recipeapp.classes.Recipe;

import java.util.List;

public interface IConnectInternet {

    void callback(List<Recipe> obj);
}