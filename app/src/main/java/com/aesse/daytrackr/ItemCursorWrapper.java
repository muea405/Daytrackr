package com.aesse.daytrackr;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.Date;
import java.util.UUID;

public class ItemCursorWrapper extends CursorWrapper {
    public ItemCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Item getItem() {
        String uuidString = getString(getColumnIndex(ItemDbSchema.ItemTable.Cols.UUID));
        Float mood = getFloat(getColumnIndex(ItemDbSchema.ItemTable.Cols.MOOD));
        Float stress = getFloat(getColumnIndex(ItemDbSchema.ItemTable.Cols.STRESS));
        Float eat = getFloat(getColumnIndex(ItemDbSchema.ItemTable.Cols.EAT));
        Float sleep = getFloat(getColumnIndex(ItemDbSchema.ItemTable.Cols.SLEEP));
        Float ex = getFloat(getColumnIndex(ItemDbSchema.ItemTable.Cols.EX));
        String notes = getString(getColumnIndex(ItemDbSchema.ItemTable.Cols.NOTES));
        long date = getLong(getColumnIndex(ItemDbSchema.ItemTable.Cols.DATE));

        Item item = new Item(UUID.fromString(uuidString));
        item.setMood(mood);
        item.setStress(stress);
        item.setEat(eat);
        item.setSleep(sleep);
        item.setEx(ex);
        item.setNotes(notes);
        item.setDate(new Date(date));
        return item;
    }
}
