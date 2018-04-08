package com.taregan.sqlitedemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.taregan.sqlitedemo.database.model.Note;
import com.taregan.sqlitedemo.utils.MyUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by pitambar on 4/5/18.
 */

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.MyViewHolder> {

    Context context;
    List<Note> notes;


    public NotesAdapter(Context context, List<Note> notes) {
        this.context = context;
        this.notes = notes;
    }

    @Override
    public NotesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_list_row,null);

        return new NotesAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(NotesAdapter.MyViewHolder holder, int position) {

        Note note = notes.get(position);
        holder.note.setText(note.getNote());
        holder.dot.setText(Html.fromHtml("&#8226;"));
        holder.timestamp.setText(MyUtils.formatDate(note.getTimeStamp()));

    }



    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView note,dot,timestamp;

        public MyViewHolder(View itemView) {
            super(itemView);
            note = (TextView) itemView.findViewById(R.id.tvNote);
            dot = (TextView) itemView.findViewById(R.id.dot);
            timestamp = (TextView) itemView.findViewById(R.id.tvTimestamp);
        }


    }
}
