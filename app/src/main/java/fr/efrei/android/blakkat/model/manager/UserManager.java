package fr.efrei.android.blakkat.model.manager;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import fr.efrei.android.blakkat.model.MySQLite;
import fr.efrei.android.blakkat.model.User;

public class UserManager {
    private static final String TABLE_NAME = "USER";
    public static final String KEY_PSEUDO_USER="pseudo_user";
    public static final String CREATE_TABLE_USER = "CREATE TABLE "+TABLE_NAME+
            " (" +
            " "+KEY_PSEUDO_USER+" STRING PRIMARY KEY" +
            ");";

    private MySQLite maBaseSQLite;
    private SQLiteDatabase db;

    public UserManager(Context context)
    {
        maBaseSQLite = MySQLite.getInstance(context);
    }

    public void open()
    {
        db = maBaseSQLite.getWritableDatabase();
    }

    public void close()
    {
        db.close();
    }

    public void addUser(User user) {

        ContentValues newUser = new ContentValues();
        newUser.put(KEY_PSEUDO_USER, user.get_pseudo());
        db.insert(TABLE_NAME,null,newUser);
    }


    public int deleteUser(String pseudo) {

        String where = KEY_PSEUDO_USER+" = ?";
        String[] whereArgs = {pseudo+""};

        return db.delete(TABLE_NAME, where, whereArgs);
    }

    //checks if a user exists in the database
    public boolean checkUser(String pseudo) {
        Cursor c = getUsers();
        if (c.moveToFirst()) {

            do {
                System.out.println("coucou");
                if (c.getString(c.getColumnIndex(UserManager.KEY_PSEUDO_USER)).equals(pseudo)) {
                    c.close();
                    return true;
                }
            }while (c.moveToNext());

        }
        c.close();
        return false;
    }

    public Cursor getUsers() {
        return db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
    }
}
