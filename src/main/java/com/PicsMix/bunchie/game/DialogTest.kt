package com.PicsMix.bunchie.game

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialogFragment

class DialogTest : AppCompatDialogFragment() {
    private var playerTurn = 0
    private var playerAction = 0
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        playerTurn = Integer.valueOf(GameActivity.count) % GameActivity.playerNum
        playerAction = Integer.valueOf(GameActivity.count + 1) % 2
        if (Integer.valueOf(GameActivity.count) == 0) {
            builder.setTitle(Players.list[playerTurn] + "'s turn")
                .setMessage("In this turn, you could draw whatever you want, since it's turn #1")
                .setPositiveButton(
                    "Okay"
                ) { dialog, i -> }
        } else {
            builder.setTitle(
                Players.list[playerTurn] + "'s turn, round " + (GameActivity.count + 1) + "/" + Integer.valueOf(
                    First.turns!!
                )
            )
                .setMessage(if (playerAction == 1) "In this turn, you will try to draw what's in the text box." else "In this turn, you will type what you see in the drawing into the text box.")
                .setPositiveButton(
                    "Okay"
                ) { dialog, i -> }
        }
        return builder.create()
    }
}