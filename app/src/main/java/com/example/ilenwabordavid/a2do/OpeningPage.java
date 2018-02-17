package com.example.ilenwabordavid.a2do;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.zip.Inflater;

public class OpeningPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening_page);
    }
    public void startListActivity(View view) {
        Intent intent = new Intent(OpeningPage.this, ToDoList.class);
        startActivity(intent);
    }
}
