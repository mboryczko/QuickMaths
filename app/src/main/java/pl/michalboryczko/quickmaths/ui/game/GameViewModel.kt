package pl.michalboryczko.quickmaths.ui.game

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.graphics.Color
import io.reactivex.observers.DisposableObserver
import pl.michalboryczko.quickmaths.interactor.TimerUseCase
import pl.michalboryczko.quickmaths.model.Exercise
import pl.michalboryczko.quickmaths.model.Question
import pl.michalboryczko.quickmaths.model.TimerInput
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by ${michal_boryczko} on 11.06.2018.
 */
class GameViewModel @Inject constructor(private val timerUseCase: TimerUseCase) :ViewModel() {

    lateinit var currentExercise: Exercise

    val timerInfo: MutableLiveData<String> = MutableLiveData()
    val timerValue: MutableLiveData<String> = MutableLiveData()
    val instruction: MutableLiveData<String> = MutableLiveData()
    val instructionColor: MutableLiveData<Int> = MutableLiveData()
    val pointsValue: MutableLiveData<Int> = MutableLiveData()



    init {
        startTimer()
        instructionColor.value = Color.BLACK
        pointsValue.value = 0
    }

    fun nextClicked(){
        getNextEquation()
    }

    fun okClicked(userInput: Long){
        checkUserAnswer(userInput)
        getNextEquation()
    }


    private fun checkUserAnswer(userInput: Long){
        val isUserCorrect = userInput == currentExercise.result
        if(isUserCorrect) pointsValue.value = pointsValue.value!! + 1
        instructionColor.value = if(isUserCorrect) Color.GREEN else Color.RED



        timerUseCase.execute(object: DisposableObserver<Long>(){
            override fun onComplete() {
                instructionColor.value = Color.BLACK
            }

            override fun onNext(t: Long) {
                instructionColor.value = if(isUserCorrect) Color.GREEN else Color.RED
            }

            override fun onError(e: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }, TimerInput(0, 0, 2, 1, TimeUnit.SECONDS))
    }


    fun getNextEquation(){
        val r = Random()
        val first = r.nextInt(100)
        val second = r.nextInt(100)
        val equation = "$first + $second = ?"
        val result = first.toLong() + second.toLong()
        currentExercise = Exercise(equation, result)
        instruction.value = currentExercise.equation
    }

    fun startTimer(){
        timerUseCase.execute(object: DisposableObserver<Long>(){
            override fun onComplete() {
                startGameTimer()
            }

            override fun onNext(t: Long) {
                timerInfo.value = "Test starts in: "
                timerValue.value = t.toString()
                Timber.d("received: $t")
            }

            override fun onError(e: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }, TimerInput(0, 3, 0, 1, TimeUnit.SECONDS))

    }

    private fun startGameTimer(){
        getNextEquation()
        timerUseCase.execute(object: DisposableObserver<Long>(){
            override fun onComplete() {
                timerInfo.value = "End of time"
                timerValue.value = ""
            }

            override fun onNext(t: Long) {
                timerInfo.value = "Time left "
                timerValue.value = t.toString()
            }

            override fun onError(e: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }, TimerInput(0, 0, 30, 1, TimeUnit.SECONDS))
    }



    
}