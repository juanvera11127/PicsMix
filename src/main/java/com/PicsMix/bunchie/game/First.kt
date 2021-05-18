package com.PicsMix.bunchie.game

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class First : AppCompatActivity() {
    private var input: EditText? = null
    private var button: Button? = null
    private val context: Context? = null
    private val plus: Button? = null
    private val minus: Button? = null
    private val text: TextView? = null
    private val count = 2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)
        input = findViewById<View>(R.id.editText2) as EditText
        button = findViewById<View>(R.id.button4) as Button
        reqPermission()
        button!!.setOnClickListener {
            turns = input!!.text.toString()
            if (turns!!.isEmpty()) {
                Toast.makeText(applicationContext, "Input # of turns", Toast.LENGTH_SHORT).show()
            } else {
                startAct()
            }
        }
        applicationContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.deleteRecursively()
    }

    fun reqPermission() {
        val reqEX =
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        if (reqEX != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                REQ_PERMISSION
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permission: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permission, grantResults)
        if (requestCode == REQ_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "permission ok", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "permission failed", Toast.LENGTH_LONG).show()
        }
    }

    protected fun startAct() {
        val intent = Intent(applicationContext, Players::class.java)
        startActivity(intent)
    }

    companion object {
        var turns: String? = null
        private const val REQ_PERMISSION = 120
    }
}