package com.example.chess.model;

import android.content.Context;

import com.example.chess.control.MoveProcessor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

/**
 * Created by ben on 4/21/15.
 */
public class Game implements GameInterface, Serializable{

    private static final long serialVersionUID = 0L;

    private Board board;
    private Board last_move;
    private ArrayList<Integer[]> move_log;

    public Game() {
        this.board = new Board();
        this.last_move = null;
        this.move_log = new ArrayList<>();
    }

    @Override
    public Piece[] getSquares() {
        Piece[] pieces = new Piece[64];
        for (int x = 0; x < 65; x++) {
            pieces[x] = board.getPieceAt(x+1);
        }
        return pieces;
    }

    @Override
    public boolean makeMove(Integer start, Integer end, String option) {
        Board temp = board.duplicate();
        if (MoveProcessor.processMove(board, start, end, option)) {
            last_move = temp;
            move_log.add(new Integer[]{start, end});
            return true;
        }
        return false;
    }

    @Override
    public Piece getPieceAt(Integer pos) {
        return board.getPieceAt(pos);
    }

    @Override
    public void switchWhoseTurn() {
        board.switchPlayer();
    }

    @Override
    public String getWhoseTurn() {
        if (board.getWhoseTurn().equals("w")) {
            return "White";
        } else {
            return "Black";
        }
    }

    @Override
    public boolean undoMove() {
        if (last_move == null) {
            return false;
        } else {
            board = last_move.duplicate();
            move_log.remove(move_log.size()-1);
            last_move = null;
            return true;
        }
    }

    @Override
    public ArrayList<Integer[]> getLog() {
        return this.move_log;
    }

    @Override
    public boolean makeAIMove() {
        Integer[] ai_move = MoveProcessor.findMove(board);
        if (ai_move != null) {
            Board temp = board.duplicate();
            makeMove(ai_move[0], ai_move[1], null);
            last_move = temp;
            return true;
        }
        return false;
    }

    public boolean writeToFile(String filename, Context context) {

        try{
            FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE); //Select where you wish to save the file...
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
            oos.flush();
            oos.close();
            System.out.println("successfully wrote file");
            return true;
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }

        System.out.println("what the heck");

        return false;

    }

    public static Game readFromFile(String filename, Context context) {

        System.out.println("reading file " + filename + " in now!");


        File test = new File(filename);
        System.out.println(test.getAbsolutePath() + " " + test.exists());


        try {
            FileInputStream fis = context.openFileInput(filename);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Game game = (Game)ois.readObject();
            fis.close();
            ois.close();
            return game;
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
            return null;
        } catch (StreamCorruptedException e) {
            System.out.println("stream corrupted");
            return null;
        } catch (IOException e) {
            System.out.println("ioexception");
            return null;
        } catch (ClassNotFoundException e) {
            System.out.println("classnotfound");
            return null;
        }

    }
}
