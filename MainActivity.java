package com.PicsMix.bunchie.game;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private CanvasView canvasView;
    public static int count = 0;
    Context context;
    Button b;
    Button clear;
    public static EditText text;
    public static boolean isDrawing = true;
    RadioButton r1;
    RadioButton r2;
    RadioButton r3;
    RadioButton r4;
    RadioButton r5;
    RadioButton r6;
    RadioGroup r;
    RadioGroup rg;
    public static int playerNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        playerNum = Players.list.size();
        count = 0;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        canvasView = (CanvasView) findViewById(R.id.canvas);
        b = (Button) findViewById(R.id.button2);
        clear = (Button) findViewById(R.id.button);
        text = (EditText) findViewById(R.id.editText3);
        r = (RadioGroup) findViewById(R.id.radioGroup);
        rg = (RadioGroup) findViewById(R.id.radioGroup2);
        r1 = (RadioButton) findViewById(R.id.radio1);
        r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = "#00e600";
                changeColor(s);
            }
        });
        r2 = (RadioButton) findViewById(R.id.radio2);
        r2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = "blue";
                changeColor(s);
            }
        });
        r3 = (RadioButton) findViewById(R.id.radio3);
        r3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = "red";
                changeColor(s);
            }
        });
        r4 = (RadioButton) findViewById(R.id.radio4);
        r4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = "black";
                changeColor(s);
            }
        });
        r5 = (RadioButton) findViewById(R.id.radio5);
        r5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = "small";
                changeSize(s);
            }
        });
        r6 = (RadioButton) findViewById(R.id.radio6);
        r6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = "big";
                changeSize(s);
            }
        });
        r4.toggle();
        r5.toggle();
        Intent i = getIntent();
        isDrawing = true;
        text.setEnabled(false);
        openDialog();
        CanvasView.pics.clear();
    }

    private void changeColor(String s) {
        canvasView.changeColor(s);
    }
    private void changeSize(String s) {
        canvasView.changeSize(s);
    }

    public void clearCanvas(View v) {
        r.clearCheck();
        r4.toggle();
        canvasView.clearCanvas();
    }

    public void saveCanvas(View v) {
        count++;
        if(count <= Integer.valueOf(First.turns)) {
            canvasView.saveCanvas();
        }
        if (count < Integer.valueOf(First.turns) && isDrawing) {
            isDrawing = false;
            text.setEnabled(true);
            clear.setEnabled(false);
            text.setText("");
            text.setHint("What do you see?");
            r.clearCheck();
            r.setVisibility(View.INVISIBLE);
            rg.clearCheck();
            rg.setVisibility(View.INVISIBLE);
            openDialog();
        }

        else if (!isDrawing && count < Integer.valueOf(First.turns)) {
            isDrawing = true;
            clearCanvas(v);
            text.setEnabled(false);
            clear.setEnabled(true);
            r.setVisibility(View.VISIBLE);
            rg.setVisibility(View.VISIBLE);
            r4.toggle();
            openDialog();
            r5.toggle();
        }
        if (count == Integer.valueOf(First.turns)) {
            Toast.makeText(this, "GAME!", Toast.LENGTH_SHORT).show();
            openPictures();
            text.setEnabled(false);
            text.setText("");
            r.clearCheck();
            r.setVisibility(View.INVISIBLE);
            clear.setEnabled(false);
        }
        if (count > Integer.valueOf(First.turns)) {
            Intent intent = new Intent(getApplicationContext(), First.class);
            startActivity(intent);
        }
    }

    public void openDialog() {
        DialogTest dialog = new DialogTest();
        dialog.show(getSupportFragmentManager(), "example dialog");
    }

    public void openPictures() {
        Intent intent = new Intent(getApplicationContext(), OpenImages.class);
        startActivity(intent);
    }

    public void undo(View v) {
        canvasView.undo();
    }
}
