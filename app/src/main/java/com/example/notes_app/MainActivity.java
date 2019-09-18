package com.example.notes_app;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static com.example.notes_app.Note.COLUMN_ID;
import static com.example.notes_app.Note.TABLE_NAME;

public class MainActivity extends AppCompatActivity implements NoteAdapter.OnItemClickListener {
    FloatingActionButton mBtn;
    Dbhelper helper;
    RecyclerView mRv;
    private NoteAdapter adapter;
    TextView mTvNoItems;
    NoteAdapter.OnItemClickListener listener;
    private static int REQ_CODE_ADD = 100;
    private static int REQ_CODE_UPDATE = 101;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_CODE_ADD && resultCode == RESULT_OK) {
            assert data != null;
            helper = new Dbhelper(this);

            setupAdapter();

        } else if (requestCode == REQ_CODE_UPDATE && resultCode == RESULT_OK) {
            helper = new Dbhelper(this);

            setupAdapter();

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTvNoItems = findViewById(R.id.no_of_items);
        mBtn = findViewById(R.id.fab);
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, add_activity.class);
                intent.putExtra("EXTRA_TYPE", "ADD");
                startActivityForResult(intent, REQ_CODE_ADD);
            }
        });

        mRv = findViewById(R.id.rview);
        mRv.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        setupAdapter();


    }

    private void setupAdapter() {
        List<Note> list;
        helper = new Dbhelper(this);
        list = helper.getNotes();

        if (list.size() > 0) {
            mTvNoItems.setVisibility(View.GONE);
            mRv.setVisibility(View.VISIBLE);
            NoteAdapter adapter = new NoteAdapter(MainActivity.this, list);
            adapter.setListener(this);
            mRv.setAdapter(adapter);
            adapter.notifyDataSetChanged();


        } else {
            mTvNoItems.setVisibility(View.VISIBLE);
            mRv.setVisibility(View.GONE);
        }


    }

    @Override
    public void onUpdate(Note note) {

        Intent updateIntent = new Intent(MainActivity.this, add_activity.class);
        updateIntent.putExtra("EXTRA_TYPE", "UPDATE");
        updateIntent.putExtra("NOTE", note);
        startActivityForResult(updateIntent, REQ_CODE_UPDATE);
        //startActivity(updateIntent);

        helper = new Dbhelper(this);
        helper.updateNote(note);
        setupAdapter();
    }

    @Override
    public void onDelete(Note note) {
//        Note note = new Note();
        // id = note.getId();
        helper = new Dbhelper(this);
        helper.deleteNote(note);
        setupAdapter();

    }


}