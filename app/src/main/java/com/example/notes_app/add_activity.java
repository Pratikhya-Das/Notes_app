package com.example.notes_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.List;

public class add_activity extends AppCompatActivity {
    Dbhelper helper;
    private Button mIbtn;
    private EditText mEttitle, mEttext;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_activity);
        String intentType = getIntent().getStringExtra("EXTRA_TYPE");



        mIbtn = findViewById(R.id.badd);
        mEttitle = findViewById(R.id.et_title);
        mEttext = findViewById(R.id.et_text);


        if (intentType.equals("ADD")){

            mIbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String title = mEttitle.getText().toString().trim();
                    String text = mEttext.getText().toString().trim();
                    savetoDb(title, text);


                }
            });
        }
        else if (intentType.equals("UPDATE"))
        {

            final Note note=getIntent().getParcelableExtra("NOTE");
            mEttitle.setText(note.getTitle());
            mEttext.setText(note.getText());
//            helper = new Dbhelper(this);
//            helper.updateNote(note);
            mIbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String title = mEttitle.getText().toString().trim();
                    String text = mEttext.getText().toString().trim();
                    Note note1 = new Note();
                    note1.setId(note.getId());
                    note1.setText(text);
                    note1.setTitle(title);
                    updateDb(note1);

                }
            });


        }


    }

    private void updateDb(Note note) {
        helper = new Dbhelper(add_activity.this);
        helper.updateNote(note);
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();

    }


    private void savetoDb(String title, String text) {
        helper = new Dbhelper(add_activity.this);
        helper.insertNote(title, text);
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();

    }


}
