package com.teamcoffee.coffeewizard;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.SimpleCursorTreeAdapter;

/**
 * Created by Brendan on 3/31/2015.
 */
public class FavoritesExpandableCursorAdapter extends SimpleCursorTreeAdapter{

    private Context context1;

    public FavoritesExpandableCursorAdapter(Cursor cursor, Context context,int groupLayout,
                                            int childLayout, String[] groupFrom, int[] groupTo, String[] childrenFrom,
                                            int[] childrenTo){

        super(context, cursor, groupLayout, groupFrom, groupTo,
                childLayout, childrenFrom, childrenTo);
        context1 = context;

    }


    @Override
    protected Cursor getChildrenCursor(Cursor groupCursor) {
        DatabaseHelper dbHelper = new DatabaseHelper(context1);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM " + DatabaseContract.TableThree.TABLE_NAME + " WHERE name = '" + groupCursor.getString(groupCursor.getColumnIndex("name")) + "'";
        Cursor childCursor = db.rawQuery(query, null);
        childCursor.moveToFirst();
        db.close();
        return childCursor;
    }
}
