package com.PicsMix.bunchie.game;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class First extends AppCompatActivity {

    private EditText input;
    private Button button;
    public static String turns;
    private Context context;
    private static final int REQ_PERMISSION = 120;
    private Button plus;
    private Button minus;
    private TextView text;
    private int count = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        input = (EditText) findViewById(R.id.editText2);
        button = (Button) findViewById(R.id.button4);
        reqPermission();
        button.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                turns = input.getText().toString();
                if(turns.isEmpty()) {
                    Toast.makeText(getApplicationContext(),"Input # of turns", Toast.LENGTH_SHORT).show();
                }
                else {
                    startAct();
                }
            }
        });
    }

    public void reqPermission() {
        int reqEX = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (reqEX != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},REQ_PERMISSION);
        }
    }

    public void onRequestPermissionsResult (int requestCode, @NonNull String[] permission, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permission, grantResults);
        if (requestCode == REQ_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "permission ok", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this, "permission failed", Toast.LENGTH_LONG).show();
        }
    }

    protected void startAct() {
        Intent intent = new Intent(getApplicationContext(), Players.class);
        startActivity(intent);
    }

}
