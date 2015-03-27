package com.teamcoffee.coffeewizard;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.HashMap;

/**
 * Created by Brendan on 3/9/2015.
 * This activity sets up the brew screen.
 */
public class RecipesCursorAdapterFavorites extends CursorAdapter {

    String name;

    public RecipesCursorAdapterFavorites(Context context, Cursor cursor){
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.recipes_list_favorites, parent, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        final TextView textBrewer = (TextView) view.findViewById(R.id.brewer);
        final TextView textDensity = (TextView) view.findViewById(R.id.density);
        final TextView textVolume = (TextView) view.findViewById(R.id.volume);
        final ToggleButton toggleFavorite = (ToggleButton) view.findViewById(R.id.toggleButton);
        final Button brewButton = (Button) view.findViewById(R.id.brewButton);
        final TextView textWeight = (TextView) view.findViewById(R.id.weight);
        final TextView textName = (TextView) view.findViewById(R.id.favoriteName);

        final String brewer = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TableOne.COLUMN1_NAME));
        final String density = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TableOne.COLUMN4_NAME));
        final String volume = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TableOne.COLUMN2_NAME));
        final String weight = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TableOne.COLUMN3_NAME));
        final String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TableThree.COLUMN5_NAME));

        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = DatabaseContract.TableThree.selectQuery(brewer, volume, density, weight);

        Cursor c = db.rawQuery(selectQuery, null);

        if(c.moveToNext()){
            toggleFavorite.setChecked(true);
        }
        else {
            toggleFavorite.setChecked(false);
        }

        c.close();
        db.close();

        textBrewer.setText(brewer);
        textDensity.setText(density);
        textVolume.setText(volume);
        textWeight.setText(weight);
        textName.setText(name);


        final String deleteFavoriteQuery = DatabaseContract.TableThree.deleteQuery(brewer, volume, density, weight);

        toggleFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper dbHelper = new DatabaseHelper(context);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                if(toggleFavorite.isChecked()){
                    try {
                        final String insertFavoriteQuery = DatabaseContract.TableThree.insertQuery(brewer, volume, density, weight, name);
                        db.execSQL(insertFavoriteQuery);
                    }catch (SQLiteConstraintException e){
                        //This ignores a "unique" constraint error, which is in place to prevent
                        //duplicate database entries.
                    }
                }
                else{
                    db.execSQL(deleteFavoriteQuery);
                }
                db.close();
                dbHelper.close();

            }
        });

        brewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String volume, density, brewer;
                Cursor c;
                int time, weight;
                HashMap<Integer, String> timerEvents = new HashMap<Integer, String>();

                DatabaseHelper dbHelper = new DatabaseHelper(context);
                SQLiteDatabase db = dbHelper.getReadableDatabase();

                brewer = textBrewer.getText().toString();
                volume = textVolume.getText().toString();
                density = textDensity.getText().toString();

                String time_select_query = DatabaseContract.TableOne.createSelect(brewer, volume, density);
                String event_select_query = DatabaseContract.TableTwo.createSelect(brewer, volume, density);

                if (brewer.equals("Press Pot")){
                    time_select_query = DatabaseContract.TableOne.createSelect(brewer, "0", "n/a");
                    event_select_query = DatabaseContract.TableTwo.createSelect(brewer, "0", "n/a");
                }


                c = db.rawQuery(time_select_query, null);
                try {
                    if (c.moveToFirst()) {
                        time = c.getInt(c.getColumnIndex(DatabaseContract.TableOne.COLUMN5_NAME));
                        weight = c.getInt(c.getColumnIndex(DatabaseContract.TableOne.COLUMN3_NAME));
                    } else {
                        time = -1;
                        weight = -1;
                    }
                }
                finally{
                    c.close();
                }


                c = db.rawQuery(event_select_query, null);

                try {

                    int startTimeIndex = c.getColumnIndex(DatabaseContract.TableTwo.COLUMN5_NAME);
                    int eventIndex = c.getColumnIndex(DatabaseContract.TableTwo.COLUMN4_NAME);

                    while (c.moveToNext()) {

                        int startTime = c.getInt(startTimeIndex);
                        String event = c.getString(eventIndex);

                        timerEvents.put(startTime, event);

                    }
                }
                finally {
                    c.close();
                }

                db.close();

                if(brewer.equals("Press Pot")){
                    timerEvents.put(0, "Pour to " + volume + "g of Water");
                }

                Intent intent = new Intent(context, CountdownActivity.class);
                intent.putExtra("time", Integer.toString(time));
                intent.putExtra("events", timerEvents);
                intent.putExtra("weight", Integer.toString(weight));

                context.startActivity(intent);

            }
        });

    }
}