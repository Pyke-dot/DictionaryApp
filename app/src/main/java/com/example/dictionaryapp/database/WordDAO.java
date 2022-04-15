package com.example.dictionaryapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.dictionaryapp.Models.Word;

import java.util.List;

@Dao
public interface WordDAO {

    @Query("SELECT * FROM Word")
    List<Word> getAllWords();

    @Insert
    void saveWordToDB(Word... word);

    @Query("DELETE FROM Word")
    void deleteAll();
}
