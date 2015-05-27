package com.example.chess.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.chess.R;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by kaitlynsussman on 4/15/15.
 */
public class StartScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);

        Button button_playgame = (Button) findViewById(R.id.button_play);
        Button button_replaygame = (Button) findViewById(R.id.button_replay);

        button_playgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });

        button_replaygame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(StartScreen.this);
                builder.setTitle("Pick a game you want to replay");


                final CharSequence[] items = {"Sort by date","Sort by title"};

                builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch(which)
                        {
                            case 0:
                                //TODO sort by date method
                                break;
                            case 1:
                                //TODO: sort by title method
                                break;
                        }
                    }
                });


                final ListView listview_games = new ListView(StartScreen.this);



                ArrayList<String> list = listFiles();

                if (list.size() == 0) {
                    return;
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(StartScreen.this, android.R.layout.simple_list_item_1, android.R.id.text1, list);

                listview_games.setAdapter(adapter);

                builder.setView(listview_games);

                final Dialog dialog = builder.create();

                dialog.show();

                listview_games.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String selected_game =(String) (listview_games.getItemAtPosition(position));
                        System.out.println("replaying game " + selected_game);
                        replayGame(selected_game);
                    }
                });


                //replayGame();
            }
        });

        // listFiles();

    }

    public void startGame() {
        Intent start_game_intent = new Intent(this, ChessGame.class);
        startActivity(start_game_intent);
    }

    public void replayGame(String game_to_play) {
        Intent replay_game_intent = new Intent(this, Replay.class);
        //Bundle bundle = new Bundle();
        //bundle.putString("name", game_to_play);
        //replay_game_intent.putExtra(game_to_play, bundle);
        replay_game_intent.putExtra("game", game_to_play);
        startActivity(replay_game_intent);
    }

    public ArrayList<String> listFiles() {
        ArrayList<String> list_of_files = new ArrayList<>();
        for (File file : StartScreen.this.getFilesDir().listFiles()) {
            list_of_files.add(file.getName());
        }

        return list_of_files;
    }
}

