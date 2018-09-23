package pl.michalboryczko.quickmaths.source.api

import io.reactivex.Observable
import pl.michalboryczko.quickmaths.model.Question
import retrofit2.http.GET
import retrofit2.http.Query
/**
 * Created by mjbor on 6/11/2018.
 */
interface Api {

    @GET("rxjava/getQuestions.php")
    fun getQuestions(@Query("topic") topic: String) : Observable<List<Question>>

}