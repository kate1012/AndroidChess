package com.example.chess.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.chess.R;

import static com.example.chess.R.drawable;

/**
 * Created by kaitlynsussman on 4/13/15.
 */

public class ImageAdapter extends BaseAdapter {
    private Context context;
    private Integer[] squares_array;

    public ImageAdapter(Context c, Integer[] squares_array) {
        context = c;
        this.squares_array = squares_array;
    }

    public int getCount() {
        return squares_array.length;
    }

    public Object getItem(int position) {
        return squares_array[position].toString();
    }

    public long getItemId(int position) {
        return 0;
    }


    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {

        View myView = convertView;

        ImageView imageView;



        if (convertView == null) {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        } else {
            imageView = (ImageView) convertView;
        }



        imageView.setImageResource(squares_array[position]);



        return imageView;
    }

}