package com.PicsMix.bunchie.game;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Dialog;
import android.support.v7.app.AppCompatDialogFragment;

public class DialogTest extends AppCompatDialogFragment {

    private int playerTurn;
    private int playerAction;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
         playerTurn = Integer.valueOf(MainActivity.count) % MainActivity.playerNum;
         playerAction = Integer.valueOf(MainActivity.count + 1) % 2;

         if(Integer.valueOf(MainActivity.count) == 0) {
             builder.setTitle(Players.list.get(playerTurn) + "'s turn")
                     .setMessage("In this turn, you could draw whatever you want, since it's turn #1")
                     .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                         @Override
                         public void onClick(DialogInterface dialog, int i) {

                         }
                     });
         }
         else {
             builder.setTitle(Players.list.get(playerTurn) + "'s turn, round " + (MainActivity.count+1) + "/" + Integer.valueOf(First.turns))
                     .setMessage(playerAction == 1 ? "In this turn, you will try to draw what's in the text box." : "In this turn, you will type what you see in the drawing into the text box.")
                     .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                         @Override
                         public void onClick(DialogInterface dialog, int i) {

                         }
                     });
         }
        return builder.create();
    }
}
