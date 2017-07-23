package utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aisha on 7/23/2017.
 */

public class DetailDataSourceBridge {

    private SQLiteDatabase database;
    private MySQLiteDbHelper dbHelper;
    private String[] allColumns = {MySQLiteDbHelper.COLUMN_ID, MySQLiteDbHelper.COLUMN_PRIMARY_HEXCODE, MySQLiteDbHelper.COLUMN_PRIMARYDARK_HEXCODE, MySQLiteDbHelper.COLUMN_ACCENT_HEXCODE};
    Context main;

    public DetailDataSourceBridge(Context context) {
        dbHelper = new MySQLiteDbHelper(context);
        main = context;
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Detail createColorObject(String primary, String primarydark, String accent) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteDbHelper.COLUMN_PRIMARY_HEXCODE, primary);
        values.put(MySQLiteDbHelper.COLUMN_PRIMARYDARK_HEXCODE, primarydark);
        values.put(MySQLiteDbHelper.COLUMN_ACCENT_HEXCODE, accent);

        long insertId = database.insert(MySQLiteDbHelper.TABLE_COLORS, null, values);
        Cursor cursor = database.query(MySQLiteDbHelper.TABLE_COLORS, allColumns, MySQLiteDbHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();

        Detail newDetail = cursorToDetail(cursor);
        cursor.close();
        return newDetail;
    }

    public ArrayList<Detail> getAllSavedColors() {
        ArrayList<Detail> savedColors = new ArrayList<Detail>();

        Cursor cursor = database.query(MySQLiteDbHelper.TABLE_COLORS, allColumns, null, null, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Detail colorDetail = cursorToDetail(cursor);

            savedColors.add(colorDetail);
            //  Toast.makeText(main, "message added in dabase" + de.toString(), Toast.LENGTH_SHORT).show();
            //  Toast.makeText(main, "message added in dabase"+message.toString(), Toast.LENGTH_SHORT).show();
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return savedColors;
    }

    public Detail cursorToDetail(Cursor cursor) {

        Detail detail = new Detail();
        detail.setId(cursor.getLong(0));
        detail.setPrimaryHexCode(cursor.getString(1));
        detail.setPrimaryDarkHexCode(cursor.getString(2));
        detail.setAccentHexCode(cursor.getString(3));

        return detail;
    }

}
