package com.appix.storemangaementapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class SQLiteDatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "   StoreData";

    // Country table name
    private static final String TABLE_ITEMS= "items";

    // Country Table Columns names
    private static final String KEY_ID = "id";
    private static final String ITEM_NAME = "item_name";
    private static final String ITEM_SCALE = "item_scale";
    private static final String ITEM_PRICE = "item_price";
    private static final String ITEM_QUANTITY = "item_quantity";
    private static final String ITEM_QEEMAT_FROKHT = "item_qeemat_frokht";
    private static final String ITEM_PRICE_TOTAL = "item_price_total";


    public SQLiteDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_STORE_TABLE = "CREATE TABLE " + TABLE_ITEMS + "("
               // + KEY_ID + " INTEGER PRIMARY KEY," + ITEM_NAME + " TEXT,"+ ITEM_SCALE + " TEXT,"+ ITEM_PRICE + " TEXT,"
                + KEY_ID + " INTEGER ," + ITEM_NAME + " TEXT,"+ ITEM_SCALE + " TEXT,"+ ITEM_PRICE + " TEXT,"
                + ITEM_QUANTITY + " TEXT,"
                + ITEM_QEEMAT_FROKHT + " TEXT,"
                + ITEM_PRICE_TOTAL + " REAL" + ")";
        db.execSQL(CREATE_STORE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new country
    void addCountry(Store country) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, country.getId());
        values.put(ITEM_NAME, country.getName());
        values.put(ITEM_SCALE, country.getItem_scale());
        values.put(ITEM_PRICE, country.getItem_price());
        values.put(ITEM_QUANTITY, country.getItem_quantity());
        values.put(ITEM_QEEMAT_FROKHT, country.getQeemat_frokht());
        values.put(ITEM_PRICE_TOTAL, country.getTotal_price());

        // Inserting Row
        db.insert(TABLE_ITEMS, null, values);
        db.close(); // Closing database connection
    }

    // Getting single country
//    Store getCountry(int id) {
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.query(TABLE_ITEMS, new String[] { KEY_ID,
//                        ITEM_NAME, POPULATION }, KEY_ID + "=?",
//                new String[] { String.valueOf(id) }, null, null, null, null);
//        if (cursor != null)
//            cursor.moveToFirst();
//
//        Store country = new Store(Integer.parseInt(cursor.getString(0)),
//                cursor.getString(1),
//                cursor.getString(2),
//                cursor.getString(3),
//                cursor.getString(4));
//        // return country
//        return country;
//    }

    // Getting All Countries
    public ArrayList<Store> getAllCountries() {
        ArrayList<Store> countryList = new ArrayList();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ITEMS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Store country = new Store();
                country.setId(Integer.parseInt(cursor.getString(0)));
                country.setName(cursor.getString(1));
                country.setItem_scale(cursor.getString(2));
                country.setItem_price(cursor.getString(3));
                country.setItem_quantity(cursor.getString(4));
                country.setQeemat_frokht(cursor.getString(5));
                country.setTotal_price(cursor.getFloat(6));
                // Adding country to list
                countryList.add(country);
            } while (cursor.moveToNext());
        }

        // return country list
        return countryList;
    }

    // Updating single country
    public int updateCountry(Store country) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ITEM_NAME, country.getName());
        values.put(ITEM_SCALE, country.getItem_scale());
        values.put(ITEM_PRICE, country.getItem_price());
        values.put(ITEM_QUANTITY, country.getItem_quantity());
        values.put(ITEM_QEEMAT_FROKHT, country.getQeemat_frokht());
        values.put(ITEM_PRICE_TOTAL, country.getTotal_price());

        // updating row
        return db.update(TABLE_ITEMS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(country.getId()) });

    }

   //  Deleting single country
    public void deleteCountry(Store country) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ITEMS, KEY_ID + " = ?",
                new String[] { String.valueOf(country.getId()) });
        db.close();
    }

    // Deleting all countries
//    public void deleteAllCountries() {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_ITEMS,null,null);
//        db.close();
//    }

    // Getting countries Count
    public float getCountriesCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT SUM(ITEM_PRICE_TOTAL) FROM " + TABLE_ITEMS, null);
        c.moveToFirst();
        float i = c.getFloat(0);
        c.close();
        return i;
//        String countQuery =    "SELECT SUM(" + ITEM_PRICE_TOTAL + ") FROM" +TABLE_ITEMS;
//        Log.d("",""+countQuery);
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(countQuery, null);
//        cursor.close();
//        return countQuery;

    }
}

