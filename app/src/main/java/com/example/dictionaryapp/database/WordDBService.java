package com.example.dictionaryapp.database;

import com.example.dictionaryapp.Models.Word;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.room.Room;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WordDBService{

    public interface WordDBCallBack{
        void saveWord();
        void list(List<Word> list);
        void doneSave();
        void deleteWord();
        void deleteDone();
    }

    WordDB database;
    public WordDBCallBack listener;
    ExecutorService dbExecutor = Executors.newFixedThreadPool(4);
    Handler dbHandler = new Handler(Looper.getMainLooper());

    public WordDBCallBack getListener() {
        return listener;
    }

    public WordDB getInstance(Context context){
        if (database == null) {
            database = Room.databaseBuilder(context.getApplicationContext(),
                    WordDB.class, "word_history").build();
        }
        return database;
    }


    public void saveWord(Word word){
        dbExecutor.execute(new Runnable() {
            @Override
            public void run() {
                database.getDao().saveWordToDB(word);
                dbHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.doneSave();
                    }
                });
            }
        });
    }

    public void delete(){
        dbExecutor.execute(new Runnable() {
            @Override
            public void run() {
                database.getDao().deleteAll();
                dbHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.deleteDone();
                    }
                });
            }
        });
    }

    public void getSave(){
        dbExecutor.execute(new Runnable() {
            @Override
            public void run() {
                List<Word> wordList = database.getDao().getAllWords();
                dbHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.list(wordList);
                    }
                });
            }
        });
    }
}
