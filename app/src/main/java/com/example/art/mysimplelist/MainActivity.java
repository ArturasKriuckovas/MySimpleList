package com.example.art.mysimplelist;


import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.art.mysimplelist.db.Note;
import com.example.art.mysimplelist.db.NoteHelper;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private NoteHelper nHelper;
    private ListView nNoteListView;
    private ArrayAdapter<String> nAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nHelper = new NoteHelper(this);
        nNoteListView = (ListView) findViewById(R.id.note_list);

        updateUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.action_add_note:
                final EditText noteEditText = new EditText(this);
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("New note")
                        .setMessage("Add new note: ")
                        .setView(noteEditText)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which){
                                String note = String.valueOf(noteEditText.getText());
                                SQLiteDatabase db = nHelper.getWritableDatabase();
                                ContentValues values = new ContentValues();
                                values.put(Note.NoteEntry.COLUMN,note);
                                db.insertWithOnConflict(Note.NoteEntry.TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
                                db.close();
                                updateUI();
                            }
                        })

                        .setNegativeButton("Cancel", null).create();
                dialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }
    private void updateUI(){
        ArrayList<String> noteList = new ArrayList<>();
        SQLiteDatabase db = nHelper.getReadableDatabase();
        Cursor cursor = db.query(Note.NoteEntry.TABLE,
                new String[] {Note.NoteEntry._ID, Note.NoteEntry.COLUMN}, null, null, null, null, null);
        while (cursor.moveToNext()){
            int index = cursor.getColumnIndex(Note.NoteEntry.COLUMN);
            noteList.add(cursor.getString(index));
        }
        if(nAdapter == null){
            nAdapter = new ArrayAdapter<>(this, R.layout.item_list, R.id.note_title, noteList);
            nNoteListView.setAdapter(nAdapter);
        }else{
            nAdapter.clear();
            nAdapter.addAll(noteList);
            nAdapter.notifyDataSetChanged();
        }
        cursor.close();
        db.close();

    }

    public void deleteNote(View view){
        View parent = (View) view.getParent();
        TextView noteTextView = (TextView) parent.findViewById(R.id.note_title);
        String note = String.valueOf(noteTextView.getText());
        SQLiteDatabase db = nHelper.getWritableDatabase();
        db.delete(Note.NoteEntry.TABLE, Note.NoteEntry.COLUMN + " = ?", new String[] {note});
        db.close();
        updateUI();
    }

}
//============================================================
//padariau pagal tutoriala, todel gali but matytas kodas.....
//============================================================









