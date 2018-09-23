package pl.michalboryczko.quickmaths.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by ${michal_boryczko} on 12.06.2018.
 */


@Entity(tableName = "question")
data class Question(
        @PrimaryKey
        @ColumnInfo(name = "idQuestion")
        val id_question: Int,
        @ColumnInfo(name = "idTopic")
        val id_topic: Int,
        @ColumnInfo(name = "question")
        val question: String = ""
)