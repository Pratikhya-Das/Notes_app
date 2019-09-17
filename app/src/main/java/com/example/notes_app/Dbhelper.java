package com.example.notes_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import static com.example.notes_app.Note.COLUMN_ID;
import static com.example.notes_app.Note.TABLE_NAME;

public class Dbhelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION=1;
    public static final String DATABASE_NAME="NOTE.db";
    Context context;

    public Dbhelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Note.CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);


    }

    public long insertNote(String title,String text) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(Note.TITLE,title);
        cv.put(Note.TEXT,text);

        long id=db.insert(TABLE_NAME,null,cv);
        db.close();

        Toast.makeText(context,"Success",Toast.LENGTH_SHORT);
        return id;
    }



    public List<Note> getNotes() {

        List<Note> notes=new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();

        String getQuery="SELECT * FROM "+ TABLE_NAME;

        Cursor cursor=db.rawQuery(getQuery,null);

        if(cursor.moveToFirst()) {
            do {
                Note note=new Note();
                note.setTitle(cursor.getString(cursor.getColumnIndex(Note.TITLE)));
                note.setText(cursor.getString(cursor.getColumnIndex(Note.TEXT)));
                notes.add(note);
            }while (cursor.moveToNext());
        }

        return notes;

    }
    public int  updateNote(Note note)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(note.COLUMN_ID,note.getId());
        values.put(note.TITLE,note.getTitle());
        values.put(note.TEXT,note.getText());
        return db.update(TABLE_NAME,values, COLUMN_ID + " = ?",new String[]{String.valueOf(note.getId())});
    }
    public void deleteNote(Note note)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(note.TABLE_NAME, COLUMN_ID + " = ?",new String[]{String.valueOf(note.getId())});
        db.close();
    }
}
