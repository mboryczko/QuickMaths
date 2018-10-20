package pl.michalboryczko.quickmaths.ui.main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.Observable
import pl.michalboryczko.quickmaths.app.BaseViewModel
import pl.michalboryczko.quickmaths.model.Question
import pl.michalboryczko.quickmaths.source.repository.QuestionRepository
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by ${michal_boryczko} on 11.06.2018.
 */
class MainViewModel
    @Inject constructor(private var repository : QuestionRepository ) :BaseViewModel() {

    var questions :List<Question> = mutableListOf()
    val currentQuestion: MutableLiveData<Question> = MutableLiveData()
    val timerInfo: MutableLiveData<String> = MutableLiveData()
    val timerValue: MutableLiveData<String> = MutableLiveData()


    init {
        getQuestions()
    }

    fun onCardClicked(){
        //getQuestions()
        emitNextCard()
    }

    private fun startTimer(startValue: Int){
        Observable.range(startValue, 3)
                .subscribe {
                    if(it > 0){
                        timerInfo.value = "Test starts in: "
                        timerValue.value = it.toString()
                    }

                    else{

                    }
                }
    }

    private fun getQuestions()  {
        repository.getQuestions()
                .subscribe (
                        {
                            questions = it },
                        {
                            it.printStackTrace() }
                )
    }

    private fun emitNextCard(){
        if(!questions.isEmpty()){
            val index = Random().nextInt(questions.size)
            currentQuestion.value = questions[index]
        }

    }

    
}