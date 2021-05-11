package com.math.watermelon.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(entities = [mathdata::class], version = 1,exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun DataDao(): DataDao

    companion object{
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "watermelondata.db")
                        .createFromAsset("mathdata.db")
                    .build()
            }
            return INSTANCE as AppDatabase
        }

    }
}