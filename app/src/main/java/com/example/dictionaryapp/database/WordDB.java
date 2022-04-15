package com.example.dictionaryapp.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.dictionaryapp.Models.Word;

@Database(version = 1,entities = {Word.class})
abstract public class WordDB extends RoomDatabase {
    public abstract WordDAO getDao();
}