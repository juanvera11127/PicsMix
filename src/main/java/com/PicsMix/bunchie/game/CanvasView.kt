package com.PicsMix.bunchie.game

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.Toast
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

class CanvasView(context: Context?, attrs: AttributeSet?) :
    View(context, attrs) {
    private var mBitmap: Bitmap
    private var mCanvas: Canvas
    private var mPath: Path
    private val mPaint: Paint
    private var mX = 0f
    private var mY = 0f
    private var hasStarted = false
    private val paths = ArrayList<Path>()
    private val colors = ArrayList<Int>()
    private val cornflakes = ArrayList<Float>()
    private var colorName: String
    private var n = 0
    private val SMALL = 6f
    private val BIG = 12f
    private var f = 0f

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        mCanvas = Canvas(mBitmap)
        hasStarted = true
    }

    fun saveCanvas() {
        if (GameActivity.isDrawing) {
            this.isDrawingCacheEnabled = true
            this.buildDrawingCache()
            setBackgroundColor(Color.WHITE)
            mBitmap = Bitmap.createBitmap(this.drawingCache)
            this.isDrawingCacheEnabled = false
        } else {
            colorName = "black"
            val v1: EditText? = GameActivity.text
            if (v1 != null) {
                v1.isDrawingCacheEnabled = true
            }
            if (v1 != null) {
                v1.setBackgroundColor(Color.WHITE)
            }
            if (v1 != null) {
                mBitmap = Bitmap.createBitmap(v1.drawingCache)
            }
            if (v1 != null) {
                v1.isDrawingCacheEnabled = false
            }
        }
        val file2 = File(Companion.filesDir, GameActivity.count.toString() + ".png")
        var fos: FileOutputStream?
        fos = null
        try {
            file2.createNewFile()
            fos = FileOutputStream(file2)
            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
            pics.add(file2)
            fos.flush()
            fos.close()
        } catch (e: IOException) {
            Toast.makeText(context, "epic fail: " + e.message, Toast.LENGTH_LONG).show()
            e.printStackTrace()
        } finally {
            if (fos != null) {
                try {
                    fos.close()
                    fos = null
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun changeColor(c: String) {
        colorName = c
    }

    fun changeSize(s: String) {
        f = if (s == "small") SMALL else BIG
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (i in colors.indices) {
            mPaint.color = colors[i]
            mPaint.strokeWidth = cornflakes[i]
            mPath = paths[i]
            canvas.drawPath(mPath, mPaint)
        }
    }

    private fun StartTouch(x: Float, y: Float) {
        n = Color.parseColor(colorName)
        colors.add(n)
        cornflakes.add(f)
        mPath = Path()
        paths.add(mPath)
        mPath.moveTo(x, y)
        mPath.quadTo(x, y, x + 1, y + 1)
        mX = x
        mY = y
    }

    private fun moveTouch(x: Float, y: Float) {
        val dx = Math.abs(x - mX)
        val dy = Math.abs(y - mY)
        if (dx >= TOLERANCE || dy >= TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2)
            mX = x
            mY = y
        }
    }

    fun clearCanvas() {
        mPath.reset()
        paths.clear()
        colors.clear()
        cornflakes.clear()
        val n = Color.parseColor("black")
        colors.add(n)
        cornflakes.add(SMALL)
        f = SMALL
        mPath = Path()
        paths.add(mPath)
        invalidate()
    }

    fun undo() {
        if (paths.size > 1) {
            paths.removeAt(paths.size - 1)
            colors.removeAt(colors.size - 1)
            cornflakes.removeAt(cornflakes.size - 1)
            mPath = paths[colors.size - 1]
            invalidate()
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (GameActivity.isDrawing) {
            val x = event.x
            val y = event.y
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    StartTouch(x, y)
                    invalidate()
                }
                MotionEvent.ACTION_MOVE -> {
                    moveTouch(x, y)
                    invalidate()
                }
            }
        }
        return true
    }

    companion object {
        val filesDir: File? = null
        private const val TOLERANCE = 5f
        var folderNum = 0
        var color = 0
        var pics = ArrayList<File>()
        var Dir: File? = null
    }

    init {
        mBitmap = Bitmap.createBitmap(1080, 1920, Bitmap.Config.ARGB_8888)
        mCanvas = Canvas(mBitmap)
        mPath = Path()
        paths.add(mPath)
        mPaint = Paint()
        mPaint.isAntiAlias = true
        mPaint.color = Color.BLACK
        colors.add(Color.BLACK)
        colorName = "black"
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeJoin = Paint.Join.ROUND
        mPaint.strokeWidth = SMALL
        f = SMALL
        cornflakes.add(SMALL)
    }
}