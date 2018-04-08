package com.taregan.sqlitedemo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.taregan.sqlitedemo.database.DatabaseHelper;
import com.taregan.sqlitedemo.database.model.Note;
import com.taregan.sqlitedemo.utils.RecyclerTouchListner;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Note> noteList;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private DatabaseHelper dbHelper;
    private TextView noNotesText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);

        mRecyclerView= findViewById(R.id.recycler_view);

        noNotesText = findViewById(R.id.emptyNotesView);

        dbHelper = new DatabaseHelper(this);
        noteList = new ArrayList<>();

        noteList.addAll(dbHelper.getAllNotes());

        setSupportActionBar(toolbar);





        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNoteDialog(false,null,-1);
            }
        });

        mAdapter=new NotesAdapter(this,noteList);

        RecyclerView.LayoutManager llm = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(llm);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);

        toggleEmptyNotes();

        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListner(this, mRecyclerView, new RecyclerTouchListner.ClickListner() {
            @Override
            public void onClick(View v, int position) {

            }

            @Override
            public void onLongClick(View v, int position) {
                showActionsDialog(position);

            }
        }){

        });
    }

    private void showActionsDialog(final int position) {
        CharSequence actions[] = new CharSequence[]{"Edit", "Delete"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose option");
        builder.setItems(actions, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    showNoteDialog(true, noteList.get(position), position);
                } else{
                    deleteNote(position);
                }
            }
        });
        builder.show();
    }

    private void toggleEmptyNotes() {
        if(dbHelper.getNotesCount()>0){

            noNotesText.setVisibility(View.INVISIBLE);
        }else{

            noNotesText.setVisibility(View.VISIBLE);
        }
    }


    private void showNoteDialog(final boolean shouldUpdate,final Note note, final int position) {

        LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
        View view = layoutInflater.inflate(R.layout.note_dialog,null);

        AlertDialog.Builder dialogBuilder= new AlertDialog.Builder(MainActivity.this);
        dialogBuilder.setView(view);

        final EditText inputNote = view.findViewById(R.id.note);
        TextView dialogTitle = view.findViewById(R.id.dialog_title);
        dialogTitle.setText(!shouldUpdate ? getString(R.string.lbl_new_note_title) : getString(R.string.lbl_edit_note_title));

        if (shouldUpdate && note != null) {
            inputNote.setText(note.getNote());
        }
        dialogBuilder
                .setCancelable(false)
                .setPositiveButton(shouldUpdate ? "update" : "save", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {

                    }
                })
                .setNegativeButton("cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });
        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show toast message when no text is entered
                if (TextUtils.isEmpty(inputNote.getText().toString())) {
                    Toast.makeText(MainActivity.this, "Enter note!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    alertDialog.dismiss();
                }

                // check if user updating note
                if (shouldUpdate && note != null) {
                    // update note by it's id
                    updateNote(inputNote.getText().toString(), position);
                } else {
                    // create new note
                    createNote(inputNote.getText().toString());
                }
            }
        });
    }

    private void createNote(String noteString) {
        // inserting note in db and getting
        // newly inserted note id
        long id = dbHelper.insertNotes(noteString);

        // get the newly inserted note from db
        Note n = dbHelper.getNote(id);

        if (n != null) {
            // adding new note to array list at 0 position
            noteList.add(0, n);

            // refreshing the list
            mAdapter.notifyDataSetChanged();

            toggleEmptyNotes();
        }
    }

    private void updateNote(String noteString, int position) {

        Note n = noteList.get(position);
        // updating note text
        n.setNote(noteString);

        // updating note in db
        dbHelper.updateNote(n);

        // refreshing the list
        noteList.set(position, n);
        mAdapter.notifyItemChanged(position);

        toggleEmptyNotes();

    }

    private void deleteNote( int position){
        // deleting the note from db
        dbHelper.deleteNote(noteList.get(position));

        // removing the note from the list
        noteList.remove(position);
        mAdapter.notifyItemRemoved(position);

        toggleEmptyNotes();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
