package com.PicsMix.bunchie.game;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class OpenImages extends AppCompatActivity {

    Button b1;
    Button b2;
    Button b3;
    Button b4;
    ImageView img;
    private int count = 1;
    TextView turn;
    TextView player;
    public static boolean isSaving;
    Context context;
    File Dir;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_images);
        b1 = (Button) findViewById(R.id.next);
        b1.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(CanvasView.pics.size() > count) {
                    count++;
                    show();
                }
            }
        });
        b2 = (Button) findViewById(R.id.back);
        b2.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(count > 1) {
                    count--;
                    show();
                }
            }
        });
        b3 = (Button) findViewById(R.id.button5);
        b3.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                save();
                Toast.makeText(getApplicationContext(), "Pics saved to: " + Dir, Toast.LENGTH_LONG).show();
                b3.setEnabled(false);
            }
        });
        b4 = (Button) findViewById(R.id.button6);
        b4.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), First.class);
                startActivity(intent);
            }
        });
        img = (ImageView) findViewById(R.id.imageView);
        turn = (TextView) findViewById(R.id.textView3);
        player = (TextView) findViewById(R.id.textView4);
        show();
    }

    protected void show() {
        File imgFile = new File(CanvasView.context.getFilesDir(), count+".png");
        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        img.setImageBitmap(myBitmap);
        turn.setText("Turn: "+ count);
        player.setText("Player: " + Players.list.get((count-1) % MainActivity.playerNum));
    }

    private void save() {

        File file = null;
        String state;
        int folderNum;
        state = Environment.getExternalStorageState();

        if(Environment.MEDIA_MOUNTED.equals((state))) {
            File Root = Environment.getExternalStorageDirectory();
            File mm = new File(Root+"/PicsMix");
            if(!mm.exists()) {
                mm.mkdir();
            }
            File[] files = mm.listFiles();

            folderNum = files.length + 1;

            Dir = new File(Root.getAbsolutePath()+"/PicsMix/" + folderNum);
            if(!Dir.exists()) {
                Dir.mkdir();
            }
        }
        else {
            Toast.makeText(context, "External storage not found", Toast.LENGTH_LONG).show();
        }
        FileOutputStream fos;
        fos = null;
        try {
            for(int i = 1; i <= CanvasView.pics.size(); i++) {
                FileChannel source = new FileInputStream(CanvasView.context.getFilesDir()+"/" + i + ".png").getChannel();
                FileChannel destination = new FileOutputStream(Dir + "/" + i + ".png").getChannel();
                destination.transferFrom(source, 0, source.size());
            }
            //mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
           // pics.add(file);
            //fos.close();
        }
        catch(IOException e) {
            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        finally {
            if (fos != null) {
                try {
                    fos.close();
                    fos = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
