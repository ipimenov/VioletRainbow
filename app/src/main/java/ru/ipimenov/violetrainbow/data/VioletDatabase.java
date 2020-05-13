package ru.ipimenov.violetrainbow.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Violet.class}, version = 1, exportSchema = false)
public abstract class VioletDatabase extends RoomDatabase {

    private static final String DB_NAME = "violets.db";
    private static VioletDatabase database;
    private static final Object LOCK = new Object();

    public static VioletDatabase getInstance(Context context) {
        synchronized (LOCK) {
            if (database == null) {
                database = Room.databaseBuilder(context, VioletDatabase.class, DB_NAME).build();
            }
        }
        return database;
    }

    public abstract VioletDao violetDao();
}
