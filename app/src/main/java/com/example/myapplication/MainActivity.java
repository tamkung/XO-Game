package com.example.myapplication;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[3][3];


    private  boolean P1Turn = true;

    private int rc;

    private int P1P;
    private int P2P;

    private TextView textViewP1;
    private TextView textViewP2;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewP1 = findViewById(R.id.text_p1);
        textViewP2 = findViewById(R.id.text_p2);

        for (int i =0; i < 3; i++){
            for (int t =0; t < 3; t++){
                String buttonID ="button_" + i + t;
                int resID = getResources().getIdentifier(buttonID,"id",getPackageName());
                buttons[i][t] =findViewById(resID);
                buttons[i][t].setOnClickListener(this);
            }
        }

        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });

        final Button btn00 = findViewById(R.id.button_00);
        final Button btn01 = findViewById(R.id.button_01);
        final Button btn02 = findViewById(R.id.button_02);
        final Button btn10 = findViewById(R.id.button_10);
        final Button btn11 = findViewById(R.id.button_11);
        final Button btn12 = findViewById(R.id.button_12);
        final Button btn20 = findViewById(R.id.button_20);
        final Button btn21 = findViewById(R.id.button_21);
        final Button btn22 = findViewById(R.id.button_22);
        final Button btnre = findViewById(R.id.button_reset);
        final Button btnres = findViewById(R.id.button_restart);
        final Button btnst = findViewById(R.id.button_start);

        Button btnstart = findViewById(R.id.button_start);
        btnst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
                btn00.setEnabled(true);
                btn01.setEnabled(true);
                btn02.setEnabled(true);
                btn10.setEnabled(true);
                btn11.setEnabled(true);
                btn12.setEnabled(true);
                btn20.setEnabled(true);
                btn21.setEnabled(true);
                btn22.setEnabled(true);
                btnre.setEnabled(true);
                btnres.setEnabled(true);
                btnst.setEnabled(false);

            }
        });

        Button btnrestart = findViewById(R.id.button_restart);
        btnrestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartGame();
                btn00.setEnabled(false);
                btn01.setEnabled(false);
                btn02.setEnabled(false);
                btn10.setEnabled(false);
                btn11.setEnabled(false);
                btn12.setEnabled(false);
                btn20.setEnabled(false);
                btn21.setEnabled(false);
                btn22.setEnabled(false);
                btnre.setEnabled(false);
                btnres.setEnabled(false);
                btnst.setEnabled(true);

            }
        });
    }


    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")){
            return;
        }

        if (P1Turn){
            ((Button)v).setText("X");
        } else {
            ((Button)v).setText("O");
        }

        rc++;

        if (checkWin()) {
            if (P1Turn) {
                Player1Win();
            } else {
                Player2Win();
            }
        } else if (rc == 9){
            Draw();
        } else {
            P1Turn = !P1Turn;
        }

    }

    private boolean checkWin(){
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++){
            for (int t = 0; t < 3; t++){
                field[i][t] = buttons [i][t].getText().toString();
            }
        }

        for (int i = 0; i < 3; i++){
            if (field[i][0].equals(field[i][1])
                && field[i][0].equals(field[i][2])
                && !field[i][0].equals("")){
            return true;
            }
        }

        for (int i = 0; i < 3; i++){
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")){
                return true;
            }
        }

        if (field[0][0].equals(field[1][1])
                    && field[0][0].equals(field[2][2])
                    && !field[0][0].equals("")){
                return true;
        }

        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")){
            return true;
        }

        return false;
    }

    private void Player1Win() {
        P1P++;
        Toast.makeText(this,"Player1Win",Toast.LENGTH_SHORT).show();
        updatePointText();
        resetBoard();
    }

    private void Player2Win() {
        P2P++;
        Toast.makeText(this,"Player2Win",Toast.LENGTH_SHORT).show();
        updatePointText();
        resetBoard();
    }

    private void Draw() {
        Toast.makeText(this,"Draw!",Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void updatePointText(){
        textViewP1.setText("Player 1 :" + P1P);
        textViewP2.setText("Player 2 :" + P2P);
    }

    private void resetBoard(){
        for (int i = 0; i < 3; i++) {
            for (int t = 0; t < 3; t++) {
                buttons[i][t].setText("");
            }
        }

        rc = 0;
        P1Turn = true;
    }

    private void  resetGame(){
        P1P = 0;
        P2P = 0;
        updatePointText();
        resetBoard();

    }

    private void startGame(){

    }

    private void restartGame(){
        P1P = 0;
        P2P = 0;
        updatePointText();
        resetBoard();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount",rc);
        outState.putInt("Player1Point",P1P);
        outState.putInt("Player2Point",P2P);
        outState.putBoolean("Player1Turn",P1Turn);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        rc = savedInstanceState.getInt("roundCount");
        P1P =savedInstanceState.getInt("Player1Point");
        P2P =savedInstanceState.getInt("Player2Point");
        P1Turn =savedInstanceState.getBoolean("Player1Turn");
    }



}
