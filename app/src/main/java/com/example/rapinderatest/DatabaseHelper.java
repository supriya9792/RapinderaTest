package com.example.rapinderatest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "image_db";

    public static final String TABLE_NAME = "favorite_images";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + "fav_img_id" + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "album_id" + " INTEGER,"
                    + "id" + " INTEGER,"
                    + "title" + " TEXT,"
                    + "url" + " TEXT,"
                    + "thumbnailurl" + " TEXT"
                    + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // Create tables again
        onCreate(db);
        }

    public long insertFavImage(String albumid,String id,String title,String url,String thumbnailurl) {
        // get writable database as we want to write data

        String sql="SELECT * FROM "+TABLE_NAME+" WHERE `id`='" +id+"'";
        SQLiteDatabase sQLiteDatabase2 = getReadableDatabase();
        Cursor cursor = sQLiteDatabase2.rawQuery(sql, null);
        cursor.moveToFirst();

        Log.d("Cursor Count", String.valueOf(cursor.getCount()));
        if (cursor.getCount() == 0) {

            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("album_id", albumid);
            values.put("id", id);
            values.put("title", title);
            values.put("url", url);
            values.put("thumbnailurl", thumbnailurl);
            // insert row
            long id1 = db.insert(TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id1;
        } return 0;
    }
    public ArrayList<ImageList> getAllFavorites() {
        ArrayList<ImageList> favorites = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ImageList note = new ImageList();
                note.setAlbumid(String.valueOf(cursor.getInt(cursor.getColumnIndex("album_id"))));
                note.setId(cursor.getString(cursor.getColumnIndex("id")));
                note.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                note.setUrl(cursor.getString(cursor.getColumnIndex("url")));
                note.setThumbnailurl(cursor.getString(cursor.getColumnIndex("thumbnailurl")));

                favorites.add(note);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return favorites list
        return favorites;
    }

}
