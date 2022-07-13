package com.abhilash.githubissues.model.database

import android.content.Context
import androidx.room.*
import com.abhilash.githubissues.model.entities.Issue
import com.abhilash.githubissues.model.entities.User

@Database(entities = [Issue::class, /*Label::class, Reactions::class,*/ User::class], version = 1)
@TypeConverters(
    Converters::class,
    builtInTypeConverters = BuiltInTypeConverters(
        enums = BuiltInTypeConverters.State.DISABLED
    )
)
abstract class IssuesDatabase : RoomDatabase() {

    abstract fun issuesDao(): IssuesDao

    companion object {

        @Volatile
        private var INSTANCE: IssuesDatabase? = null

        fun getDatabase(context: Context): IssuesDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    IssuesDatabase::class.java,
                    "issues_database"
                ).build()
                INSTANCE = instance
                 return instance
                instance
            }
        }
    }
}