package com.example.chess.view;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.example.chess.R;
import com.example.chess.model.Game;
import com.example.chess.model.Piece;

import java.util.Objects;


public class ChessGame extends ActionBarActivity {

    public GridView gridview;
    String square_color;
    int start_location, start_location2;
    int end_location, end_location2;
    int click_count = 0;
    Piece piece_to_move;
    Game board;
    private String save_name = "";

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
        setContentView(R.layout.chessboard);


        final TextView output = (TextView) findViewById(R.id.textview_output);

        Button undo_button = (Button)findViewById(R.id.button_undo);
        Button button_exit = (Button) findViewById(R.id.button_exit);
        Button button_save = (Button) findViewById(R.id.button_save);
        Button button_draw = (Button) findViewById(R.id.button_draw);
        Button button_resign = (Button) findViewById(R.id.button_resign);

        button_draw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                output.setText("Game over: tie");
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:

                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(ChessGame.this);
                builder.setMessage("Would you like to draw").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });

        button_resign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String player = board.getWhoseTurn();
                output.setText("Game over " + player + " wins!");
            }
        });

        undo_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                board.undoMove();
                output.setText(board.getWhoseTurn().concat("'s Turn"));
                refreshBoardGraphics(board);
            }
        });

        Button ai_button = (Button)findViewById(R.id.button_AI);
        ai_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (board.makeAIMove()) {
                    board.switchWhoseTurn();
                    output.setText(board.getWhoseTurn().concat("'s Turn"));
                    refreshBoardGraphics(board);
                }
            }
        });

        button_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back_to_home_intent = new Intent(ChessGame.this, StartScreen.class);
                startActivity(back_to_home_intent);
            }
        });

        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ChessGame.this);
                builder.setTitle("Save Game");


                final EditText save_title = new EditText(ChessGame.this);

                save_title.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(save_title);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    save_name = save_title.getText().toString();
                    board.writeToFile(save_name, ChessGame.this);
                    System.out.println("saved game to " + save_name);
                }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
                });

                builder.show();

            }
        });



        output.setText("White's Turn");

        gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this, squares_array));


        start_location = end_location = 0;

        board = new Game();





                gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



                        click_count++;

                        //position = changeSquarePositions(position);

                        //Toast.makeText(ChessGame.this, "" + position, Toast.LENGTH_SHORT).show();

                        square_color = getSquareColor(position);

                        //board.removePiece(position);

                        //Toast.makeText(ChessGame.this, "" + square_color, Toast.LENGTH_SHORT).show();

                        if (click_count == 1) {

                            piece_to_move = board.getPieceAt(position);
                            start_location = changeSquarePositions(position);
                            start_location2 = position;
                            end_location = 0;


                        }
                        if (click_count == 2) {
                            end_location = changeSquarePositions(position);
                            end_location2 = position;

                            if (board.makeMove(start_location, end_location, null)) {
                                board.switchWhoseTurn();
                                output.setText(board.getWhoseTurn().concat("'s Turn"));

                                System.out.println("\nlog:");
                                for (Integer[] ints : board.getLog()) {
                                    System.out.println(ints[0] + " " + ints[1]);
                                }
                            }

                            refreshBoardGraphics(board);

                            click_count = 0;
                        }


                    }
                });
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
        square_color = getResources().getResourceEntryName(squares_array[position]);

        if(square_color.contains("ws") || square_color.contains("white_square"))
            square_color = "white";
        else if(square_color.contains("bs") || square_color.contains("black_square"))
            square_color = "black";

        //Toast.makeText(ChessGame.this, square_color, Toast.LENGTH_SHORT).show();
        return square_color;
    }

    public String getPieceColor(int position)
    {
        String piece_type = getResources().getResourceEntryName(squares_array[position]);

        Piece piece = board.getPieceAt(position);

        if(piece != null)
        {
            if(piece_type.contains("white"))
                return "white";
            if(piece_type.contains("black"))
                return "black";
        }

        return null;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
