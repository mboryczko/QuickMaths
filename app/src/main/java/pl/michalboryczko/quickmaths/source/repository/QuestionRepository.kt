package pl.michalboryczko.quickmaths.source.repository

import android.util.Log
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import pl.michalboryczko.quickmaths.source.api.ApiService
import pl.michalboryczko.quickmaths.model.Question
import pl.michalboryczko.quickmaths.source.database.AppDatabase
import pl.michalboryczko.quickmaths.source.database.dao.QuestionDAO
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by mjbor on 6/11/2018.
 */
class QuestionRepository
@Inject constructor(private var apiService: ApiService, private var questionDAO: QuestionDAO){

    fun getQuestions() : Observable<List<Question>>{
        return Observable.concatArrayEager(
                getAllQuestionsFromDb(),
                getQuestionsFromApi()
        )
                .debounce(400, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    private fun getQuestionsFromApi(): Observable<List<Question>>{
        return apiService.getAllQuestions()
                .flatMap { storeQuestionsInDb(it) }
                .doOnError{Log.d("repoLog", "errorGettingFromAPI" + it.toString())}
    }

    private fun getAllQuestionsFromDb(): Observable<List<Question>>{
        return questionDAO.getAllQuestions()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { Log.d("repoLog", "errorGettingFromDB" + it.toString()) }
                .toObservable()
    }


    private fun storeQuestionsInDb(questions: List<Question>): Observable<List<Question>>{
        questionDAO.insertQuestions(questions)
        return getAllQuestionsFromDb()
    }

}