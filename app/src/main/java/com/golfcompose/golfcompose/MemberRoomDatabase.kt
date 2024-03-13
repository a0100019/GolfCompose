package com.golfcompose.golfcompose

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [(Member::class)], version = 1)
abstract class MemberRoomDatabase: RoomDatabase() {

    abstract fun memberDao(): MemberDao

    companion object {
        private var INSTANCE: MemberRoomDatabase? = null

        fun getInstance(context: Context): MemberRoomDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MemberRoomDatabase::class.java,
                        "product_database"
                    ).fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}