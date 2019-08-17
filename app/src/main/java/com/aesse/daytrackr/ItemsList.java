package com.aesse.daytrackr;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ItemsList {
    private static ItemsList sItemsList;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static ItemsList get(Context context) {
        if (sItemsList == null) {
            sItemsList = new ItemsList(context);
        }
        return sItemsList;
    }
    private ItemsList(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new ItemBaseHelper(mContext)
                .getWritableDatabase();
    }
    public void addItem(Item c) {
        ContentValues values = getContentValues(c);
        mDatabase.insert(ItemDbSchema.ItemTable.NAME, null, values);
    }
    public List<Item> getItems() {
        List<Item> items = new ArrayList<>();
        ItemCursorWrapper cursor = queryItems(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                items.add(cursor.getItem());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return items;
    }
    public Item getItems(UUID id) {
        ItemCursorWrapper cursor = queryItems(
                ItemDbSchema.ItemTable.Cols.UUID + " = ?",
                new String[] { id.toString() }
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getItem();
        } finally {
            cursor.close();
        }

    }

    public void updateItem(Item item) {
        String uuidString = item.getId().toString();
        ContentValues values = getContentValues(item);
        mDatabase.update(ItemDbSchema.ItemTable.NAME, values,
                ItemDbSchema.ItemTable.Cols.UUID + " = ?",
                new String[] { uuidString });
    }

    private ItemCursorWrapper queryItems(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                ItemDbSchema.ItemTable.NAME,
                null, // columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                "date DESC" // orderBy
        );
        return new ItemCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(Item item) {
        ContentValues values = new ContentValues();
        values.put(ItemDbSchema.ItemTable.Cols.UUID, item.getId().toString());
        values.put(ItemDbSchema.ItemTable.Cols.MOOD, item.getMood());
        values.put(ItemDbSchema.ItemTable.Cols.STRESS, item.getStress());
        values.put(ItemDbSchema.ItemTable.Cols.EAT, item.getEat());
        values.put(ItemDbSchema.ItemTable.Cols.SLEEP, item.getSleep());
        values.put(ItemDbSchema.ItemTable.Cols.EX, item.getEx());
        values.put(ItemDbSchema.ItemTable.Cols.NOTES, item.getNotes());
        values.put(ItemDbSchema.ItemTable.Cols.DATE, item.getDate().getTime());
        return values;
    }


}
