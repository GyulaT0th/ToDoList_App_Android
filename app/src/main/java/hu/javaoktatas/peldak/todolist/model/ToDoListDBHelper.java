package hu.javaoktatas.peldak.todolist.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class ToDoListDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "todolist.db";
    private static final int DATABASE_VERSION = 1;

    public ToDoListDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE todolist (_id INTEGER PRIMARY KEY AUTOINCREMENT, cim TEXT, teljesitve INTEGER, ev INTEGER, honap INTEGER, nap INTEGER, fontossag INTEGER)");
        db.execSQL("INSERT INTO todolist (cim, teljesitve, ev, honap, nap, fontossag) VALUES ('Bio tönköly kenyér', 1, 2023, 1, 4, 0)");
        db.execSQL("INSERT INTO todolist (cim, teljesitve, ev, honap, nap, fontossag) VALUES ('Fontos dolgot csinálni', 0, 2023, 2, 20, 1)");
        db.execSQL("INSERT INTO todolist (cim, teljesitve, ev, honap, nap, fontossag) VALUES ('Kokárda', 1, 2023, 3, 15, 2)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("TDLAPP", "DB Upgrade from: v."+oldVersion+", to: v."+newVersion);
        db.execSQL("DROP TABLE todolist"); //élesben nem ezt csináljuk, hanem mondjuk ALTER TABLE
        onCreate(db);
    }
}
