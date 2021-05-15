package com.PicsMix.bunchie.game

import android.app.ListActivity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.util.*

class Players : ListActivity() {
    var adapter: ArrayAdapter<String>? = null
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_players)
        val btn = findViewById<View>(R.id.btnAdd) as Button
        val undo = findViewById<View>(R.id.button3) as Button
        val done = findViewById<View>(R.id.startButton) as Button
        done.setOnClickListener {
            if (!list.isEmpty()) {
                val intent = Intent(applicationContext, GameActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(applicationContext, "Name List cannot be empty", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        undo.setOnClickListener {
            if (!list.isEmpty()) {
                list.removeAt(list.size - 1)
                adapter!!.notifyDataSetChanged()
            }
        }
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)
        val listener = View.OnClickListener {
            val edit = findViewById<View>(R.id.txtItem) as EditText
            if (edit.text.toString().isEmpty()) {
                Toast.makeText(applicationContext, "Name cannot be empty.", Toast.LENGTH_SHORT)
                    .show()
            } else {
                list.add(edit.text.toString())
                edit.setText("")
                adapter!!.notifyDataSetChanged()
            }
        }
        btn.setOnClickListener(listener)
        listAdapter = adapter
    }

    companion object {
        var list = ArrayList<String>()
    }
}