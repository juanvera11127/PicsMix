package com.PicsMix.bunchie.game

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private lateinit var howToPlayButton: Button
    private lateinit var startNewGameButton: Button
    private lateinit var resumeGameButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        howToPlayButton = findViewById(R.id.how_to_play_button)
        startNewGameButton = findViewById(R.id.start_new_game_button)
        resumeGameButton = findViewById(R.id.resume_game_button)

        howToPlayButton.setOnClickListener {
            val i = Intent(this@MainActivity, HowToPlayActivity::class.java)
            startActivity(i)
        }

        startNewGameButton.setOnClickListener {
            val i = Intent(this@MainActivity, First::class.java)
            startActivity(i)
        }

        resumeGameButton.visibility = View.INVISIBLE
    }
}