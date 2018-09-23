package pl.michalboryczko.quickmaths.source.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Flowable
import pl.michalboryczko.quickmaths.model.Question

/**
 * Created by ${michal_boryczko} on 13.06.2018.
 */

@Dao
interface QuestionDAO {

    @Query("select * from question")
    fun getAllQuestions(): Flowable<List<Question>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertQuestions(questions: List<Question>)

}