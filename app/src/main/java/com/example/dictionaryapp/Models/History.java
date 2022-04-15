package com.example.dictionaryapp.Models;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dictionaryapp.MainActivity;
import com.example.dictionaryapp.database.WordDBService;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


import com.example.dictionaryapp.MyApp;
import com.example.dictionaryapp.R;

import java.util.ArrayList;
import java.util.List;

public class History extends AppCompatActivity implements WordDBService.WordDBCallBack, HistoryRecyclerAdapter.HistoryClickListener {

    RecyclerView HistoryRecyclerView;

    ArrayList<Word> wordList;
    Toolbar toolbar;
    HistoryRecyclerAdapter adapter;
    WordDBService wordDBService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        wordDBService = ((MyApp) getApplication()).wordDBService;
        wordDBService.getInstance(this);
        wordDBService.listener = this;
        wordDBService.getSave();

        HistoryRecyclerView = findViewById(R.id.history_list);
        HistoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<Word> wordList= new ArrayList<>();
        adapter = new HistoryRecyclerAdapter(this,wordList);
        HistoryRecyclerView.setAdapter(adapter);


    }

    @Override
    public void list(List<Word> list) {
        ArrayList<Word> wordList= new ArrayList<>(list);
        adapter = new HistoryRecyclerAdapter(this,wordList);
        HistoryRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.clear_history:
                wordDBService.delete();
                startActivity(new Intent(this, History.class));
                return true;
            case R.id.home_page:
                startActivity(new Intent(this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void saveWord() {
    }

    @Override
    public void doneSave() {

    }

    @Override
    public void deleteWord() {

    }

    @Override
    public void deleteDone() {

    }

    @Override
    public void saveSelected(Word bird) {

    }
}
