package com.example.roompersistancedatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {EntitySutdentRecord.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {


    public abstract StudentDao studentDao();
    private static AppDatabase INSTANCE;
    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class,
                    "student-database")  //database Name
                    .build();
        }
        return INSTANCE;

    }
}
