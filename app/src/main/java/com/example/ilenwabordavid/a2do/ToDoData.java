package com.example.ilenwabordavid.a2do;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ToDoData extends AppCompatActivity {
    SQLiteOpenHelper sqLiteOpenHelper;
    SQLiteDatabase data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_data);
        sqLiteOpenHelper= new ToDoDatabase(this);
        final EditText todolist = (EditText) findViewById(R.id.todo_message);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        String message = todolist.getText().toString();
                        Log.d("ToDoData", "Entered task" + message);
                        data = sqLiteOpenHelper.getWritableDatabase();
                       ContentValues values = new ContentValues();
                        values.put("TODO", message);
                        data.insert("LIST",null, values);
                        data.close();
                        Intent intent = new Intent(ToDoData.this,ToDoList.class);
                        startActivity(intent);

                    }
                }
        );
    }
}
