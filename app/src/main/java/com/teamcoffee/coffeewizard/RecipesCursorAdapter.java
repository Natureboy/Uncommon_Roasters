package com.teamcoffee.coffeewizard;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.ToggleButton;

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
    public void bindView(View view, final Context context, Cursor cursor) {
        TextView textBrewer = (TextView) view.findViewById(R.id.brewer);
        TextView textDensity = (TextView) view.findViewById(R.id.density);
        TextView textVolume = (TextView) view.findViewById(R.id.volume);
        final ToggleButton toggleFavorite = (ToggleButton) view.findViewById(R.id.toggleButton);


        String brewer = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TableOne.COLUMN1_NAME));
        String density = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TableOne.COLUMN4_NAME));
        String volume = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TableOne.COLUMN2_NAME));

        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = DatabaseContract.TableThree.selectQuery(brewer, volume, density);

        Cursor c = db.rawQuery(selectQuery, null);

        if(c.moveToNext()){
            toggleFavorite.setChecked(true);
        }
        else {
            toggleFavorite.setChecked(false);
        }

        textBrewer.setText(brewer);
        textDensity.setText(density);
        textVolume.setText(volume);

        final String insertFavoriteQuery = DatabaseContract.TableThree.insertQuery(brewer, volume, density);
        final String deleteFavoriteQuery = DatabaseContract.TableThree.deleteQuery(brewer, volume, density);

        toggleFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper dbHelper = new DatabaseHelper(context);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                if(toggleFavorite.isChecked()){
                    try {
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

            }
        });


    }
}
