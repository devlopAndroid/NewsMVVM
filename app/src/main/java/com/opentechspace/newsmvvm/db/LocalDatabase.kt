package com.opentechspace.newsmvvm.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.opentechspace.newsmvvm.Model.Article


@Database(entities = [Article::class], version = 1, exportSchema = false)

@TypeConverters(Converts::class)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun articleDoa() : ArticleDao

    companion object
    {
        @Volatile
        private var Instance : LocalDatabase? = null
        fun getDatabase(context: Context) : LocalDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            synchronized(this){
                if(Instance == null)
                {
                    Instance = Room.databaseBuilder(context,LocalDatabase::class.java,
                        "NewsDatabase.db")
                        .build()
                }
            }
            return Instance!!
        }
    }
}