package com.PicsMix.bunchie.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class CanvasView extends View {

    public int width;
    public int height;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Path mPath;
    private Paint mPaint;
    private float mX, mY;
    private static final float TOLERANCE = 5;
    public static Context context;
    public static int folderNum;
    private boolean hasStarted = false;
    public static int color;
    private ArrayList<Path> paths = new ArrayList<Path>();
    private ArrayList<Integer> colors = new ArrayList<Integer>();
    public static ArrayList<File> pics = new ArrayList<File>();
    private ArrayList<Float> cornflakes = new ArrayList<Float>();
    private String colorName;
    private int n;
    public static File Dir;
    private final float SMALL = 6f;
    private final float BIG = 12f;
    private float f = 0f;


    public CanvasView(Context context, AttributeSet attrs) {
        super(context,attrs);
        mBitmap = Bitmap.createBitmap(1080, 1920, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);

        this.context = context;

        mPath = new Path();
        paths.add(mPath);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        colors.add(Color.BLACK);
        colorName = "black";
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(SMALL);
        f = SMALL;
        cornflakes.add(SMALL);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        hasStarted = true;
    }

    protected void saveCanvas() {
        if(MainActivity.isDrawing) {
            this.setDrawingCacheEnabled(true);
            this.buildDrawingCache();
            this.setBackgroundColor(Color.WHITE);
            mBitmap = Bitmap.createBitmap(this.getDrawingCache());
            this.setDrawingCacheEnabled(false);
        }
        else {
            colorName = "black";
            View v1 = MainActivity.text;
            v1.setDrawingCacheEnabled(true);
            v1.setBackgroundColor(Color.WHITE);
            mBitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);
        }
        File file2 = new File(context.getFilesDir(), MainActivity.count + ".png");

        FileOutputStream fos;
        fos = null;
        try {
            file2.createNewFile();
            fos = new FileOutputStream(file2);

            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            pics.add(file2);
            fos.flush();
            fos.close();
        }
        catch(IOException e) {
            Toast.makeText(context, "epic fail: " + e.getMessage(), Toast.LENGTH_LONG).show();
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

    protected void changeColor(String c) {
        colorName = c;
    }
    protected  void changeSize(String s) {
        f = (s.equals("small") ? SMALL : BIG);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for(int i = 0; i < colors.size(); i++) {
            mPaint.setColor(colors.get(i));
            mPaint.setStrokeWidth(cornflakes.get(i));
            mPath = paths.get(i);
            canvas.drawPath(mPath, mPaint);
        }
    }

    private void StartTouch(float x, float y) {
        n = Color.parseColor(colorName);
        colors.add(n);
        cornflakes.add(f);
        mPath = new Path();
        paths.add(mPath);
        mPath.moveTo(x,y);
        mPath.quadTo(x,y,x+1,y+1);
        mX = x;
        mY = y;
    }

    private void moveTouch(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if(dx >= TOLERANCE || dy >= TOLERANCE) {
            mPath.quadTo(mX,mY,(x+mX)/2, (y+mY)/2);
            mX=x;
            mY=y;
        }
    }

    public void clearCanvas() {
        mPath.reset();
        paths.clear();
        colors.clear();
        cornflakes.clear();
        int n = Color.parseColor("black");
        colors.add(n);
        cornflakes.add(SMALL);
        f = SMALL;
        mPath = new Path();
        paths.add(mPath);
        invalidate();
    }

    public void undo() {
        if(paths.size() > 1) {
            paths.remove(paths.size()-1);
            colors.remove(colors.size()-1);
            cornflakes.remove(cornflakes.size()-1);
            mPath = paths.get(colors.size()-1);
            invalidate();
        }

    }


    @Override
    public boolean onTouchEvent (MotionEvent event) {
        if(MainActivity.isDrawing) {

            float x = event.getX();
            float y = event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    StartTouch(x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_MOVE:
                    moveTouch(x, y);
                    invalidate();
                    break;
            }
        }
        return true;
    }

}
