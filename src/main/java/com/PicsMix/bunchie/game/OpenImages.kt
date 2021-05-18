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
    var b4: Button? = null
    var img: ImageView? = null
    private var count = 1
    var turn: TextView? = null
    var player: TextView? = null
    var context: Context? = null
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
        b4 = findViewById<View>(R.id.button6) as Button
        b4!!.setOnClickListener {
            val intent = Intent(applicationContext, First::class.java)
            startActivity(intent)
        }
        img = findViewById<View>(R.id.imageView) as ImageView
        turn = findViewById<View>(R.id.textView3) as TextView
        player = findViewById<View>(R.id.textView4) as TextView
        Toast.makeText(applicationContext, "Files Saved in ${this.getExternalFilesDir(Environment.DIRECTORY_PICTURES)}", Toast.LENGTH_LONG).show()
        show()
    }

    protected fun show() {
        val imgFile = File(this.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "$count.png")
        val myBitmap = BitmapFactory.decodeFile(imgFile.absolutePath)
        img!!.setImageBitmap(myBitmap)
        turn!!.text = "Turn: $count"
        player!!.text = "Player: " + Players.list[(count - 1) % GameActivity.playerNum]
    }
}