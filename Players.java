package com.PicsMix.bunchie.game;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Players extends ListActivity {

    public static ArrayList<String> list = new ArrayList<String>();

    ArrayAdapter<String> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);
        Button btn = (Button) findViewById(R.id.btnAdd);
        Button undo = (Button) findViewById(R.id.button3);
        Button done = (Button) findViewById(R.id.startButton);
        done.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!list.isEmpty()) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Name List cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
        undo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!list.isEmpty()) {
                    list.remove(list.size() - 1);
                    adapter.notifyDataSetChanged();
                }
            }
        });
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);

        OnClickListener listener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edit = (EditText) findViewById(R.id.txtItem);
                if(edit.getText().toString().isEmpty())  {
                    Toast.makeText(getApplicationContext(), "Name cannot be empty.", Toast.LENGTH_SHORT).show();
                }
                else {
                    list.add(edit.getText().toString());
                    edit.setText("");
                    adapter.notifyDataSetChanged();
                }
            }
        };
        btn.setOnClickListener(listener);
        setListAdapter(adapter);
    }


}