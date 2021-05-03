package com.PicsMix.bunchie.game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) {
            if (savedInstanceState.isEmpty) {
                var resumeGameButton = findViewById<Button>(R.id.resume_game_button)
                resumeGameButton.visibility = View.GONE
            }
        }
    }
}