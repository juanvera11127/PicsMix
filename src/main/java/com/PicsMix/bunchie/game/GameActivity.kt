package com.PicsMix.bunchie.game

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

const val TAG = "GAMEACTIVITY"

class GameActivity : AppCompatActivity() {
    private var canvasView: CanvasView? = null
    var context: Context? = null
    var doneButton: Button? = null
    var undoButton: Button? = null
    var r1: RadioButton? = null
    var r2: RadioButton? = null
    var r3: RadioButton? = null
    var r4: RadioButton? = null
    var r5: RadioButton? = null
    var r6: RadioButton? = null
    var r: RadioGroup? = null
    var rg: RadioGroup? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        playerNum = Players.list.size
        count = 0
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_game)
        canvasView = findViewById<View>(R.id.canvas) as CanvasView
        doneButton = findViewById<View>(R.id.done_button) as Button
        doneButton!!.setOnClickListener { saveCanvas(canvasView) }
        undoButton = findViewById<View>(R.id.undo_button) as Button
        undoButton!!.setOnClickListener { undo() }
        text = findViewById<View>(R.id.editText3) as EditText
        r = findViewById<View>(R.id.radioGroup) as RadioGroup
        rg = findViewById<View>(R.id.radioGroup2) as RadioGroup
        r1 = findViewById<View>(R.id.toggle_green) as RadioButton
        r2 = findViewById<View>(R.id.toggle_blue) as RadioButton
        r2!!.setOnClickListener {
            val s = "blue"
            changeColor(s)
        }
        r3 = findViewById<View>(R.id.toggle_red) as RadioButton
        r3!!.setOnClickListener {
            val s = "red"
            changeColor(s)
        }
        r4 = findViewById<View>(R.id.toggle_black) as RadioButton
        r4!!.setOnClickListener {
            val s = "black"
            changeColor(s)
        }
        r5 = findViewById<View>(R.id.radio5) as RadioButton
        r5!!.setOnClickListener {
            val s = "small"
            changeSize(s)
        }
        r6 = findViewById<View>(R.id.radio6) as RadioButton
        r6!!.setOnClickListener {
            val s = "big"
            changeSize(s)
        }
        r4!!.toggle()
        r5!!.toggle()
        val i = intent
        isDrawing = true
        text!!.isEnabled = false
        openDialog()
        CanvasView.pics.clear()
    }

    private fun changeColor(s: String) {
        canvasView!!.changeColor(s)
    }

    private fun changeSize(s: String) {
        canvasView!!.changeSize(s)
    }

    fun clearCanvas(v: View?) {
        r!!.clearCheck()
        r4!!.toggle()
        canvasView?.clearCanvas()
    }

    fun saveCanvas(v: View?) {
        count++
        Log.i(TAG, "count:" + count + " first turns:" + First.turns!!.toInt())

        if (count <= First.turns!!.toInt()) {
            canvasView?.saveCanvas()
        }
        if (count < First.turns!!.toInt() && isDrawing) {
            isDrawing = false
            text!!.isEnabled = true
            undoButton!!.isEnabled = false
            text!!.setText("")
            text!!.hint = "What do you see?"
            r!!.clearCheck()
            r!!.visibility = View.INVISIBLE
            rg!!.clearCheck()
            rg!!.visibility = View.INVISIBLE
            openDialog()
        } else if (!isDrawing && count < First.turns!!.toInt()) {
            isDrawing = true
            clearCanvas(v)
            text!!.isEnabled = false
            undoButton!!.isEnabled = true
            r!!.visibility = View.VISIBLE
            rg!!.visibility = View.VISIBLE
            r4!!.toggle()
            openDialog()
            r5!!.toggle()
        }
        if (count == First.turns!!.toInt()) {
            Toast.makeText(this, "GAME!", Toast.LENGTH_SHORT).show()
            openPictures()
            text!!.isEnabled = false
            text!!.setText("")
            r!!.clearCheck()
            r!!.visibility = View.INVISIBLE
            undoButton!!.isEnabled = false
        }
        if (count > First.turns!!.toInt()) {
            val i = Intent(this@GameActivity, First::class.java)
            startActivity(i)
        }
    }

    fun openDialog() {
        val dialog = DialogTest()
        dialog.show(supportFragmentManager, "example dialog")
    }

    fun openPictures() {
        val i = Intent(this@GameActivity, OpenImages::class.java)
        startActivity(i)
    }

    fun undo() {
        canvasView?.undo()
    }

    companion object {
        var count: Int = 0
        var text: EditText? = null
        var isDrawing = true
        var playerNum = 0
    }
}