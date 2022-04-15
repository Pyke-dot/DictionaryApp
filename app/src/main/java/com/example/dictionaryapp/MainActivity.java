package com.example.dictionaryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dictionaryapp.Adapters.MeaningAdapter;
import com.example.dictionaryapp.Adapters.PhoneticAdapter;
import com.example.dictionaryapp.Models.APIResponse;
import com.example.dictionaryapp.Models.History;

import com.example.dictionaryapp.Models.Word;
import com.example.dictionaryapp.database.WordDBService;

import java.util.List;

public class MainActivity extends AppCompatActivity implements WordDBService.WordDBCallBack{
    SearchView search_view;
    TextView textView_word;
    RecyclerView recycler_phonetics, recycler_meanings;
    ProgressDialog dialog;
    PhoneticAdapter phoneticAdapter;
    MeaningAdapter meaningAdapter;
    Toolbar toolbar;
    Boolean check;
    WordDBService wordDBService;
    Word word = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        search_view = findViewById(R.id.search_view);
        textView_word = findViewById(R.id.textView_word);
        recycler_phonetics = findViewById(R.id.recycler_phonetics);
        recycler_meanings = findViewById(R.id.recycler_meanings);
        toolbar = findViewById(R.id.toolbar);
        dialog = new ProgressDialog(this);

        check = false;
        setSupportActionBar(toolbar);

        dialog.setTitle("Loading...");
        dialog.show();
        RequestManager manager = new RequestManager(MainActivity.this);
        manager.getWordMeaning(listener, ((MyApp) getApplication()).word.getName() );

        wordDBService = ((MyApp) getApplication()).wordDBService;
        wordDBService.getInstance(this);
        wordDBService.listener = this;

        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                dialog.setTitle("Fetching response for " + query);
                dialog.show();
                RequestManager manager = new RequestManager(MainActivity.this);
                manager.getWordMeaning(listener, query);
                check = true;
                if(check){
                    word = new Word(query);
                    ((MyApp) getApplication()).word = word;
                }else{
                    word = null;
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private final OnFetchDataListener listener = new OnFetchDataListener() {
        @Override
        public void onFetchData(APIResponse apiResponse, String message) {
            dialog.dismiss();
            if (apiResponse==null){
                Toast.makeText(MainActivity.this, "No Data Found!", Toast.LENGTH_SHORT).show();
                return;
            }
            showResult(apiResponse);

        }

        @Override
        public void onError(String message) {
            dialog.dismiss();
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    private void showResult(APIResponse response){
        String word1 = getString(R.string.word) + " " + response.getWord();
        textView_word.setText(word1);


        recycler_phonetics.setHasFixedSize(true);
        recycler_phonetics.setLayoutManager(new GridLayoutManager(this, 1));
        phoneticAdapter = new PhoneticAdapter(this, response.getPhonetics());
        recycler_phonetics.setAdapter(phoneticAdapter);

        recycler_meanings.setHasFixedSize(true);
        recycler_meanings.setLayoutManager(new GridLayoutManager(this, 1));
        meaningAdapter = new MeaningAdapter(this, response.getMeanings());
        recycler_meanings.setAdapter(meaningAdapter);
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(this, History.class);
        if(word!=null) {
            wordDBService.saveWord(word);
        }
        startActivity(intent);
        return true;
    }

    @Override
    public void saveWord() {

    }

    @Override
    public void list(List<Word> list) {

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
}