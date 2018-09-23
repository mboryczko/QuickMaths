package pl.michalboryczko.quickmaths.source.api

import android.content.Context
import io.reactivex.Observable
import pl.michalboryczko.quickmaths.model.Question
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by mjbor on 6/11/2018.
 */

@Singleton
class ApiService
@Inject constructor(var context: Context, var endpoint: String, var api: Api)
{
    fun getAllQuestions(): Observable<List<Question>> {
        return api.getQuestions("all")
    }

}