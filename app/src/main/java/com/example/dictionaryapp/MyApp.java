package com.example.dictionaryapp;

import android.app.Application;

import com.example.dictionaryapp.Models.Word;
import com.example.dictionaryapp.database.WordDBService;

public class MyApp extends Application{

    public WordDBService wordDBService = new WordDBService();
    public Word word = new Word("Hello");
}
