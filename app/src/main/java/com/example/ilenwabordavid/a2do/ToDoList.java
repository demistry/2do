package com.example.ilenwabordavid.a2do;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ToDoList extends AppCompatActivity {
    ListView listView;

    ArrayAdapter listAdapter;
    SQLiteOpenHelper sqLiteOpenHelper;
    SQLiteDatabase data;
    ArrayList<String> tasks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);
        tasks = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listview);
        sqLiteOpenHelper = new ToDoDatabase(this);
        data = sqLiteOpenHelper.getReadableDatabase();
        Cursor cursor = data.query("LIST", new String[]{"TODO"}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int id = cursor.getColumnIndex("TODO");
            tasks.add(cursor.getString(id));
        }
        if (listAdapter == null) {
            listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tasks);
            listView.setAdapter(listAdapter);
        } else {
            listAdapter.clear();
            listAdapter.addAll(tasks);
            listAdapter.notifyDataSetChanged();
        }
        cursor.close();
        data.close();
        registerForContextMenu(listView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if(v.getId()==R.id.listview){
            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.menu, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final int listPosition = info.position;
        final String text = listView.getItemAtPosition(listPosition).toString();
       switch (item.getItemId()){
           case R.id.view:
               AlertDialog alertDialog= new AlertDialog.Builder(this)
                       .setTitle("Task")
                       .setMessage(text)
                       .create();
               alertDialog.show();
               return true;
           case R.id.edit:
               final EditText input = new EditText(ToDoList.this);
               input.setText(text);
               LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                       LinearLayout.LayoutParams.MATCH_PARENT,
                       LinearLayout.LayoutParams.MATCH_PARENT);
               input.setLayoutParams(lp);
               AlertDialog alertDialog2= new AlertDialog.Builder(this)
                       .setTitle("Task")
                       .setView(input)
                       .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               ContentValues contentValues = new ContentValues();
                               contentValues.put("TODO",input.getText().toString());
                               SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
                               db.update("LIST",contentValues,"TODO =?",new String[]{text});
                               listAdapter.notifyDataSetChanged();
                               listAdapter.notifyDataSetInvalidated();
                               db.close();

                           }
                       })
                       .create();
               alertDialog2.show();

               return true;
           case R.id.delete:
               SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
               db.delete("LIST","TODO =?", new String[]{text});
               tasks.remove(listPosition);
               listAdapter.notifyDataSetChanged();
               db.close();
               return true;
           default:return super.onContextItemSelected(item);
       }

    }

    public void enterToDO(View view) {
        Intent intent = new Intent(ToDoList.this,ToDoData.class);
        startActivity(intent);
    }
}