package com.teamcoffee.coffeewizard;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorTreeAdapter;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.HashMap;

/**
 * Created by Brendan on 4/1/2015.
 */
public class FavoriteViewBinder implements SimpleCursorTreeAdapter.ViewBinder{

    private Context localContext;

    public FavoriteViewBinder(Context context){
            localContext = context;
    }

    @Override
    public boolean setViewValue(View view, final Cursor cursor, int columnIndex) {
        if (view.getId() == R.id.toggleButton) {
            final ToggleButton toggleButton = (ToggleButton) view;
            toggleButton.setChecked(true);
            toggleButton.setTextOff("");
            toggleButton.setTextOff("");
            toggleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(toggleButton.isChecked()){
                        final AlertDialog.Builder favoriteNamePrompt = new AlertDialog.Builder(localContext);
                        favoriteNamePrompt.setTitle("Name This Brew!");
                        favoriteNamePrompt.setMessage("Choose a name for this brew:");
                        final EditText input = new EditText(localContext);
                        favoriteNamePrompt.setView(input);
                        favoriteNamePrompt.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DatabaseHelper dbHelper = new DatabaseHelper(localContext);
                                SQLiteDatabase db = dbHelper.getWritableDatabase();
                                String name = input.getText().toString();
                                if (name.equals("")) {
                                    favoriteNamePrompt.setMessage("Please enter a name or push cancel.");
                                } else {
                                    try {
                                        String brewer = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TableOne.COLUMN1_NAME));
                                        String density = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TableOne.COLUMN4_NAME));
                                        String volume = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TableOne.COLUMN2_NAME));
                                        String weight = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TableOne.COLUMN3_NAME));
                                        final String insertFavoriteQuery = DatabaseContract.TableThree.insertQuery(brewer, volume, density, weight, name);
                                        db.execSQL(insertFavoriteQuery);
                                    } catch (SQLiteConstraintException e) {
                                        //This ignores a "unique" constraint error, which is in place to prevent
                                        //duplicate database entries.
                                    }
                                }
                            }
                        });
                        favoriteNamePrompt.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                toggleButton.setChecked(false);
                                dialog.cancel();

                            }
                        });
                        favoriteNamePrompt.show();
                    }
                    else{
                        final AlertDialog.Builder verificationPrompt = new AlertDialog.Builder(localContext);
                        verificationPrompt.setTitle("Delete a Favorite!");
                        verificationPrompt.setMessage("Are you sure you want to delete this brew?");
                        verificationPrompt.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String brewer = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TableOne.COLUMN1_NAME));
                                String density = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TableOne.COLUMN4_NAME));
                                String volume = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TableOne.COLUMN2_NAME));
                                String weight = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TableOne.COLUMN3_NAME));
                                final String deleteFavoriteQuery = DatabaseContract.TableThree.deleteQuery(brewer, volume, density, weight);
                                DatabaseHelper dbHelper = new DatabaseHelper(localContext);
                                SQLiteDatabase db = dbHelper.getWritableDatabase();
                                db.execSQL(deleteFavoriteQuery);
                                db.close();
                                dbHelper.close();
                            }
                        });
                        verificationPrompt.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                toggleButton.setChecked(true);
                                dialog.cancel();
                            }
                        });
                        verificationPrompt.show();
                    }
                }
            });
            return true;
        }
        else if (view.getId() == R.id.brewButton) {
            Button brewButton = (Button) view;
            brewButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String volume, density, brewer;
                    Cursor c;
                    int time, weight;
                    HashMap<Integer, String> timerEvents = new HashMap<Integer, String>();

                    DatabaseHelper dbHelper = new DatabaseHelper(localContext);
                    SQLiteDatabase db = dbHelper.getReadableDatabase();

                    brewer = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TableOne.COLUMN1_NAME));
                    density = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TableOne.COLUMN4_NAME));
                    volume = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TableOne.COLUMN2_NAME));

                    //TODO: get the value from the correct text box
                    //TODO: Make a helper method in the database to retrieve the cursor instead of connecting in various different places.
                    //brewer = "V60";
                    //volume = "200";
                    //density = "high";

                    String time_select_query = DatabaseContract.TableOne.createSelect(brewer, volume, density);
                    String event_select_query = DatabaseContract.TableTwo.createSelect(brewer, volume, density);

                    if (brewer.equals("Press Pot")) {
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
                    } finally {
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
                    } finally {
                        c.close();
                    }

                    db.close();

                    if (brewer.equals("Press Pot")) {
                        timerEvents.put(0, "Pour to " + volume + "g of Water");
                    }

                    Intent intent = new Intent(localContext, CountdownActivity.class);
                    intent.putExtra("time", Integer.toString(time));
                    intent.putExtra("events", timerEvents);
                    intent.putExtra("weight", Integer.toString(weight));

                    localContext.startActivity(intent);
                }
            });
            return true;
        }
        return false;
    }

}
