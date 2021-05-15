package com.PicsMix.bunchie.game

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

class OpenImages : AppCompatActivity() {
    var b1: Button? = null
    var b2: Button? = null
    var b3: Button? = null
    var b4: Button? = null
    var img: ImageView? = null
    private var count = 1
    var turn: TextView? = null
    var player: TextView? = null
    var context: Context? = null
    var Dir: File? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_images)
        b1 = findViewById<View>(R.id.next) as Button
        b1!!.setOnClickListener {
            if (CanvasView.pics.size > count) {
                count++
                show()
            }
        }
        b2 = findViewById<View>(R.id.back) as Button
        b2!!.setOnClickListener {
            if (count > 1) {
                count--
                show()
            }
        }
        b3 = findViewById<View>(R.id.button5) as Button
        b3!!.setOnClickListener {
            save()
            Toast.makeText(applicationContext, "Pics saved to: $Dir", Toast.LENGTH_LONG).show()
            b3!!.isEnabled = false
        }
        b4 = findViewById<View>(R.id.button6) as Button
        b4!!.setOnClickListener {
            val intent = Intent(applicationContext, First::class.java)
            startActivity(intent)
        }
        img = findViewById<View>(R.id.imageView) as ImageView
        turn = findViewById<View>(R.id.textView3) as TextView
        player = findViewById<View>(R.id.textView4) as TextView
        show()
    }

    protected fun show() {
        val imgFile = File(CanvasView.filesDir, "$count.png")
        val myBitmap = BitmapFactory.decodeFile(imgFile.absolutePath)
        img!!.setImageBitmap(myBitmap)
        turn!!.text = "Turn: $count"
        player!!.text = "Player: " + Players.list[(count - 1) % GameActivity.playerNum]
    }

    private fun save() {
        val file: File? = null
        val state: String
        val folderNum: Int
        state = Environment.getExternalStorageState()
        if (Environment.MEDIA_MOUNTED == state) {
            val Root = Environment.getExternalStorageDirectory()
            val mm = File("$Root/PicsMix")
            if (!mm.exists()) {
                mm.mkdir()
            }
            val files = mm.listFiles()
            folderNum = files.size + 1
            Dir = File(Root.absolutePath + "/PicsMix/" + folderNum)
            if (!Dir!!.exists()) {
                Dir!!.mkdir()
            }
        } else {
            Toast.makeText(context, "External storage not found", Toast.LENGTH_LONG).show()
        }
        val fos: FileOutputStream?
        fos = null
        try {
            for (i in 1..CanvasView.pics.size) {
                val source =
                    FileInputStream(CanvasView.filesDir.toString() + "/" + i + ".png").channel
                val destination = FileOutputStream(Dir.toString() + "/" + i + ".png").channel
                destination.transferFrom(source, 0, source.size())
            }
            //mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            // pics.add(file);
            //fos.close();
        } catch (e: IOException) {
            Toast.makeText(applicationContext, "Error: " + e.message, Toast.LENGTH_LONG).show()
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

    companion object {
        var isSaving = false
    }
}