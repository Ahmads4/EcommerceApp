package com.example.shoppingapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import javax.inject.Inject
import javax.inject.Provider


@Database(entities = [Cart::class], version = 1, exportSchema = false)
abstract class StoreRoomDatabase : RoomDatabase() {
    abstract fun storeDao(): StoreDao

    class Callback @Inject constructor(
        private val database: Provider<StoreRoomDatabase>
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

        }

    }

    companion object {
        @Volatile
        private var INSTANCE: StoreRoomDatabase? = null

        fun getDatabase(context: Context): StoreRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StoreRoomDatabase::class.java,
                    "cart_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }


        }


    }


}