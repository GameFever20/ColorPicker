package utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Aisha on 7/23/2017.
 */

public class MySQLiteDbHelper extends SQLiteOpenHelper {


    public static final String TABLE_COLORS = "COLOR_STORAGE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_PRIMARY_HEXCODE = "PRIMARY_HEXCODE";
    public static final String COLUMN_PRIMARYDARK_HEXCODE = "PRIMARYDRAK_HEXCODE";
    public static final String COLUMN_ACCENT_HEXCODE = "ACCENT_HEXCODE";

    private static final String DATABASE_NAME = "color_storage.db";
    private static final int DATABASE_VERSION = 4;
    Context main;

    private static final String DATABASE_CREATE = "create table " + TABLE_COLORS + "( " + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_PRIMARY_HEXCODE + " " + " text not null, "
            + COLUMN_PRIMARYDARK_HEXCODE + " " + " text not null, "
            + COLUMN_ACCENT_HEXCODE + " text not null );"
            ;


    public MySQLiteDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        main = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_CREATE);
        Log.d("in o create ofdatabes", "databse new create");
        Toast.makeText(main, "on create of databse", Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.d("in upgrade", "databse new create");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_COLORS);
        onCreate(sqLiteDatabase);

    }
}
