package com.teamcoffee.coffeewizard;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by Brendan on 3/9/2015.
 * This activity sets up the brew screen.
 */
public class RecipesCursorAdapter extends CursorAdapter {


    public RecipesCursorAdapter(Context context, Cursor cursor){
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.recipes_list, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView textBrewer = (TextView) view.findViewById(R.id.brewer);
        TextView textDensity = (TextView) view.findViewById(R.id.density);

        String brewer = cursor.getString(cursor.getColumnIndexOrThrow("machine"));
        String density = cursor.getString(cursor.getColumnIndexOrThrow("coffeeDensity"));

        textBrewer.setText(brewer);
        textDensity.setText(density);

    }
}
