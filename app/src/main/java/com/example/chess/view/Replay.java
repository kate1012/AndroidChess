package com.example.chess.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.example.chess.R;
import com.example.chess.model.Game;
import com.example.chess.model.Piece;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by kaitlynsussman on 4/23/15.
 */
public class Replay extends Activity {

    GridView gridview;
    Iterator<Integer[]> log;
    Game board;
    String game_to_play;

    public Integer[] squares_array = {
            R.drawable.ws_black_rook, R.drawable.bs_black_knight,
            R.drawable.ws_black_bishop, R.drawable.bs_black_queen,
            R.drawable.ws_black_king, R.drawable.bs_black_bishop,
            R.drawable.ws_black_knight, R.drawable.bs_black_rook,
            R.drawable.bs_black_pawn, R.drawable.ws_black_pawn,
            R.drawable.bs_black_pawn, R.drawable.ws_black_pawn,
            R.drawable.bs_black_pawn, R.drawable.ws_black_pawn,
            R.drawable.bs_black_pawn, R.drawable.ws_black_pawn,
            R.drawable.white_square, R.drawable.black_square,
            R.drawable.white_square, R.drawable.black_square,
            R.drawable.white_square, R.drawable.black_square,
            R.drawable.white_square, R.drawable.black_square,
            R.drawable.black_square, R.drawable.white_square,
            R.drawable.black_square, R.drawable.white_square,
            R.drawable.black_square, R.drawable.white_square,
            R.drawable.black_square, R.drawable.white_square,
            R.drawable.white_square, R.drawable.black_square,
            R.drawable.white_square, R.drawable.black_square,
            R.drawable.white_square, R.drawable.black_square,
            R.drawable.white_square, R.drawable.black_square,
            R.drawable.black_square, R.drawable.white_square,
            R.drawable.black_square, R.drawable.white_square,
            R.drawable.black_square, R.drawable.white_square,
            R.drawable.black_square, R.drawable.white_square,
            R.drawable.ws_white_pawn, R.drawable.bs_white_pawn,
            R.drawable.ws_white_pawn, R.drawable.bs_white_pawn,
            R.drawable.ws_white_pawn, R.drawable.bs_white_pawn,
            R.drawable.ws_white_pawn, R.drawable.bs_white_pawn,
            R.drawable.bs_white_rook, R.drawable.ws_white_knight,
            R.drawable.bs_white_bishop, R.drawable.ws_white_queen,
            R.drawable.bs_white_king, R.drawable.ws_white_bishop,
            R.drawable.bs_white_knight, R.drawable.ws_white_rook,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_replay_screen);

        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            game_to_play = extras.getString("game");

        }

        Button button_cancel = (Button) findViewById(R.id.button_cancel);
        Button button_step = (Button) findViewById(R.id.button_next_step);

        gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this, squares_array));

        Game game = Game.readFromFile(game_to_play, Replay.this);
        this.log = game.getLog().iterator();

        System.out.println("printing log");
        for (Integer[] thing : game.getLog()) {
            System.out.println("fuck" + " " + thing[0] + " " + thing[1]);
        }

        this.board = new Game();

        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backHome();
            }
        });

        button_step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stepThrough();
            }
        });
    }

    public void backHome() {
        Intent back_to_home_intent = new Intent(this, StartScreen.class);
        startActivity(back_to_home_intent);
    }

    public void stepThrough() {
        if (log.hasNext()) {
            Integer[] next = log.next();
            System.out.println("replaying move " + next[0] + " to " + next[1]);
            board.makeMove(next[0], next[1], null);
            board.switchWhoseTurn();
            refreshBoardGraphics(board);
        } else {
            System.out.println("end of log");
        }
    }


    private void refreshBoardGraphics(Game board) {
        for (int x = 0; x < 64; x++) {
            Integer image = getPieceImage(board.getPieceAt(changeSquarePositions(x)), x);
            squares_array[x] = image;
        }
        gridview.setAdapter(new ImageAdapter(this, squares_array));
    }

    private Integer getPieceImage(Piece piece, Integer square_pos) {
        if (piece == null) {
            switch (getSquareColor(square_pos)) {
                case ("black"):
                    return R.drawable.black_square;
                case("white"):
                    return R.drawable.white_square;
                default:
                    System.err.println("empty square without color D:");
            }
        } else {
            switch (piece.getType()) {
                case ("p"):
                    switch (piece.getColor()) {
                        case "b":
                            switch (getSquareColor(square_pos)) {
                                case "black":
                                    return R.drawable.bs_black_pawn;
                                case "white":
                                    return R.drawable.ws_black_pawn;
                            }
                        case "w":
                            switch (getSquareColor(square_pos)) {
                                case "black":
                                    return R.drawable.bs_white_pawn;
                                case "white":
                                    return R.drawable.ws_white_pawn;
                            }
                    }

                case ("R"):
                    switch (piece.getColor()) {
                        case "b":
                            switch (getSquareColor(square_pos)) {
                                case "black":
                                    return R.drawable.bs_black_rook;
                                case "white":
                                    return R.drawable.ws_black_rook;
                            }
                        case "w":
                            switch (getSquareColor(square_pos)) {
                                case "black":
                                    return R.drawable.bs_white_rook;
                                case "white":
                                    return R.drawable.ws_white_rook;
                            }
                    }

                case ("N"):
                    switch (piece.getColor()) {
                        case "b":
                            switch (getSquareColor(square_pos)) {
                                case "black":
                                    return R.drawable.bs_black_knight;
                                case "white":
                                    return R.drawable.ws_black_knight;
                            }
                        case "w":
                            switch (getSquareColor(square_pos)) {
                                case "black":
                                    return R.drawable.bs_white_knight;
                                case "white":
                                    return R.drawable.ws_white_knight;
                            }
                    }

                case ("B"):
                    switch (piece.getColor()) {
                        case "b":
                            switch (getSquareColor(square_pos)) {
                                case "black":
                                    return R.drawable.bs_black_bishop;
                                case "white":
                                    return R.drawable.ws_black_bishop;
                            }
                        case "w":
                            switch (getSquareColor(square_pos)) {
                                case "black":
                                    return R.drawable.bs_white_bishop;
                                case "white":
                                    return R.drawable.ws_white_bishop;
                            }
                    }

                case ("K"):
                    switch (piece.getColor()) {
                        case "b":
                            switch (getSquareColor(square_pos)) {
                                case "black":
                                    return R.drawable.bs_black_king;
                                case "white":
                                    return R.drawable.ws_black_king;
                            }
                        case "w":
                            switch (getSquareColor(square_pos)) {
                                case "black":
                                    return R.drawable.bs_white_king;
                                case "white":
                                    return R.drawable.ws_white_king;
                            }
                    }

                case ("Q"):
                    switch (piece.getColor()) {
                        case "b":
                            switch (getSquareColor(square_pos)) {
                                case "black":
                                    return R.drawable.bs_black_queen;
                                case "white":
                                    return R.drawable.ws_black_queen;
                            }
                        case "w":
                            switch (getSquareColor(square_pos)) {
                                case "black":
                                    return R.drawable.bs_white_queen;
                                case "white":
                                    return R.drawable.ws_white_queen;
                            }
                    }


            }
        }

        return null;
    }

    public int changeSquarePositions(int array_pos)
    {
        array_pos = array_pos+1;

        if(array_pos<=8)
            array_pos = array_pos + 56;
        else if(array_pos<=16 && array_pos >= 9)
            array_pos = array_pos + 40;
        else if(array_pos<=24 && array_pos >= 17)
            array_pos = array_pos + 24;
        else if(array_pos<=32 && array_pos >= 25)
            array_pos = array_pos + 8;
        else if(array_pos<=40 && array_pos >= 33)
            array_pos = array_pos - 8;
        else if(array_pos<=48 && array_pos >= 41)
            array_pos = array_pos - 24;
        else if(array_pos<=56 && array_pos >= 49)
            array_pos = array_pos - 40;
        else if(array_pos<=64 && array_pos >= 57)
            array_pos = array_pos - 56;

        return array_pos;
    }

    public String getSquareColor(int position)
    {
        String square_color = getResources().getResourceEntryName(squares_array[position]);

        if(square_color.contains("ws") || square_color.contains("white_square"))
            square_color = "white";
        else if(square_color.contains("bs") || square_color.contains("black_square"))
            square_color = "black";

        //Toast.makeText(ChessGame.this, square_color, Toast.LENGTH_SHORT).show();
        return square_color;
    }

}