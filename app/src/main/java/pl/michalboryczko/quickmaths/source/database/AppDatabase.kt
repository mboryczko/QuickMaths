package pl.michalboryczko.quickmaths.source.database


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context
import pl.michalboryczko.quickmaths.model.Question
import pl.michalboryczko.quickmaths.source.database.dao.QuestionDAO
import pl.michalboryczko.quickmaths.utils.Constants

/**
 * Created by ${michal_boryczko} on 13.06.2018.
 */

@Database(entities = arrayOf(Question::class), version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun getQuestionDAO(): QuestionDAO

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getAppDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.applicationContext,
                        AppDatabase::class.java, Constants.DATABASE_NAME)
                        .build()
            }
            return INSTANCE!!
        }
    }

}