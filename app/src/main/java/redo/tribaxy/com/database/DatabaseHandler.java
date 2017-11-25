package redo.tribaxy.com.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static redo.tribaxy.com.database.RedoDatabaseSchema.REDO_DB;
import static redo.tribaxy.com.database.RedoDatabaseSchema.VERSION;

/**
 * Created by dalafiari on 11/20/17.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    public DatabaseHandler(Context context) {

        super(context, REDO_DB, null, VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
