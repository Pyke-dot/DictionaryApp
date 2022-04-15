package com.example.dictionaryapp.Models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dictionaryapp.R;

import java.util.ArrayList;
public class HistoryRecyclerAdapter extends
        RecyclerView.Adapter<HistoryRecyclerAdapter.SaveViewHolder> {

    ArrayList<Word> listOfWord;
    Context context;

    HistoryClickListener listener;

    public interface HistoryClickListener {
        void saveSelected(Word bird);
    }
    public HistoryRecyclerAdapter( Context context, ArrayList<Word> listOfWord) {
        this.listener = (HistoryClickListener) context;
        this.listOfWord = listOfWord;
        this.context = context;
    }

    @NonNull
    @Override
    public HistoryRecyclerAdapter.SaveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.history_item, parent,false);
        return new HistoryRecyclerAdapter.SaveViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryRecyclerAdapter.SaveViewHolder holder, int position) {
        holder.name.setText(listOfWord.get(position).getName());

        //set up the onClickListener
        int index = position;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.saveSelected(listOfWord.get(index));
            }
        });
    }


    @Override
    public int getItemCount() {
        return listOfWord.size();
    }




    public class SaveViewHolder extends RecyclerView.ViewHolder
    {

        TextView name;
        public SaveViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.save_word);
        }
    }
}
