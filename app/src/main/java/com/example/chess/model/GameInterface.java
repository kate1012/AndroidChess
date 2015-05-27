package com.example.chess.model;

import com.example.chess.model.Piece;

import java.util.ArrayList;

/**
 * Created by ben on 4/21/15.
 */
public interface GameInterface {

    Piece[] getSquares();

    boolean makeMove(Integer start, Integer end, String option);

    Piece getPieceAt(Integer pos);

    void switchWhoseTurn();

    String getWhoseTurn();

    boolean undoMove();

    ArrayList<Integer[]> getLog();

    boolean makeAIMove();
}
