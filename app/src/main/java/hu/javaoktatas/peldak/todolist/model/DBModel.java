package hu.javaoktatas.peldak.todolist.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBModel implements IModel{

    private ToDoListDBHelper helper;

    public DBModel(Context context) {
        this.helper = new ToDoListDBHelper(context);
    }

    @Override
    public List<ToDoItem> getAllToDoItem() {
        List<ToDoItem> items = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM todolist",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt((int)cursor.getColumnIndex("_id"));
            String cim = cursor.getString((int)cursor.getColumnIndex("cim"));
            int teljesitve = cursor.getInt((int)cursor.getColumnIndex("teljesitve"));
            int ev = cursor.getInt((int)cursor.getColumnIndex("ev"));
            int honap = cursor.getInt((int)cursor.getColumnIndex("honap"));
            int nap = cursor.getInt((int)cursor.getColumnIndex("nap"));
            int fontossag = cursor.getInt((int)cursor.getColumnIndex("fontossag"));

            ToDoItem tdi = new ToDoItem(id, cim, teljesitve, ev, honap, nap, fontossag);
            items.add(tdi);
            cursor.moveToNext();
        }
        db.close();
        return items;
    }

    @Override
    public ToDoItem getToDoItem(int id) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM todolist WHERE _id=?",new String[]{id+""});
        cursor.moveToFirst();
            String cim = cursor.getString((int)cursor.getColumnIndex("cim"));
            int teljesitve = cursor.getInt((int)cursor.getColumnIndex("teljesitve"));
            int ev = cursor.getInt((int)cursor.getColumnIndex("ev"));
            int honap = cursor.getInt((int)cursor.getColumnIndex("honap"));
            int nap = cursor.getInt((int)cursor.getColumnIndex("nap"));
            int fontossag = cursor.getInt((int)cursor.getColumnIndex("fontossag"));

            ToDoItem tdi = new ToDoItem(id, cim, teljesitve, ev, honap, nap, fontossag);

        db.close();
        return tdi;
    }

    @Override
    public void saveOrUpdateToDoItem(ToDoItem tdi) {
        SQLiteDatabase db = helper.getWritableDatabase();

        Log.d("TDLAPP", "db SOU id: "+tdi.getId());

        ContentValues cv = new ContentValues();
        cv.put("cim",tdi.getCim());
        cv.put("teljesitve", tdi.getTeljesitve());
        cv.put("ev", tdi.getEv());
        cv.put("honap", tdi.getHonap());
        cv.put("nap", tdi.getNap());
        cv.put("fontossag", tdi.getFontossag());

        if(tdi.getId()==-1){  //új elem, még nincs az adatbázisban, tehát insert
            //db.execSQL("INSERT INTO wishitem (name,place) VALUES (?,?)",new String[]{wi.getName(),wi.getPlace()});
            int id = (int) db.insert("todolist",null,cv);
            tdi.setId(id);
            Log.d("TDLAPP", "db new run");
        }else{
            /*db.execSQL("UPDATE wishitem SET name=?, place=? WHERE _id=?",
                    new Object[]{wi.getName(), wi.getPlace(),wi.getId()});*/
            db.update("todolist",cv,"_id=?",new String[]{tdi.getId()+""});
            Log.d("TDLAPP", "db edit run");
        }
        db.close();
    }

    @Override
    public void removeAllToDoItem() {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("DELETE FROM todolist");
        db.close();
    }

    @Override
    public void removeToDoItem(ToDoItem tdi) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("DELETE FROM todolist WHERE _id=?",new Integer[]{tdi.getId()});
        db.close();
    }

}
