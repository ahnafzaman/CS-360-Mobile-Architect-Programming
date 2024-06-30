package com.example.projectthree_cs360;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "app.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_USERS = "users";
    private static final String COLUMN_USER_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";

    private static final String TABLE_ITEMS = "items";
    private static final String COLUMN_ITEM_ID = "id";
    private static final String COLUMN_ITEM_NAME = "name";
    private static final String COLUMN_ITEM_QUANTITY = "quantity";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USERNAME + " TEXT,"
                + COLUMN_PASSWORD + " TEXT" + ")";
        db.execSQL(CREATE_USERS_TABLE);

        String CREATE_ITEMS_TABLE = "CREATE TABLE " + TABLE_ITEMS + "("
                + COLUMN_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_ITEM_NAME + " TEXT,"
                + COLUMN_ITEM_QUANTITY + " INTEGER" + ")";
        db.execSQL(CREATE_ITEMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        onCreate(db);
    }

    public boolean insertUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        long result = db.insert(TABLE_USERS, null, values);
        return result != -1;
    }

    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, null, COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?",
                new String[]{username, password}, null, null, null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    public boolean insertItem(InventoryItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ITEM_NAME, item.getName());
        values.put(COLUMN_ITEM_QUANTITY, item.getQuantity());
        long result = db.insert(TABLE_ITEMS, null, values);
        return result != -1;
    }

    public boolean updateItem(InventoryItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ITEM_NAME, item.getName());
        values.put(COLUMN_ITEM_QUANTITY, item.getQuantity());
        int result = db.update(TABLE_ITEMS, values, COLUMN_ITEM_ID + "=?", new String[]{String.valueOf(item.getId())});
        return result > 0;
    }

    public boolean deleteItem(int itemId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_ITEMS, COLUMN_ITEM_ID + "=?", new String[]{String.valueOf(itemId)});
        return result > 0;
    }

    public ArrayList<InventoryItem> getAllItems() {
        ArrayList<InventoryItem> itemList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ITEMS, null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") InventoryItem item = new InventoryItem(
                        cursor.getInt(cursor.getColumnIndex(COLUMN_ITEM_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_NAME)),
                        cursor.getInt(cursor.getColumnIndex(COLUMN_ITEM_QUANTITY))
                );
                itemList.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return itemList;
    }
}
