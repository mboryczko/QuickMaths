package pl.michalboryczko.quickmaths.ui.game

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.graphics.Color
import io.reactivex.observers.DisposableObserver
import pl.michalboryczko.quickmaths.app.BaseViewModel
import pl.michalboryczko.quickmaths.interactor.TimerUseCase
import pl.michalboryczko.quickmaths.model.Exercise
import pl.michalboryczko.quickmaths.model.TimerInput
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by ${michal_boryczko} on 11.06.2018.
 */
class GameViewModel @Inject constructor(private val timerUseCase: TimerUseCase) :BaseViewModel() {

    lateinit var currentExercise: Exercise

    val timerInfo: MutableLiveData<String> = MutableLiveData()
    val timerValue: MutableLiveData<String> = MutableLiveData()
    val instruction: MutableLiveData<String> = MutableLiveData()
    val instructionColor: MutableLiveData<Int> = MutableLiveData()
    val pointsValue: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<String> = MutableLiveData()
    private lateinit var exerciseProvider: ExerciseProvider


    init {
        startTimer()
        instructionColor.value = Color.BLACK
        pointsValue.value = 0
    }

    fun initViewModel(level: Int){
        this.exerciseProvider = ExerciseProvider(level)
    }

    fun nextClicked(){
        getNextEquation()
    }

    fun okClicked(userInput: Int){
        checkUserAnswer(userInput)
        getNextEquation()
    }

    fun provideError(msg: String?){
        if(msg != null){
            errorMessage.value = msg
        }

    }

    private fun checkUserAnswer(userInput: Int){
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
                provideError(e.message)
            }
        }, TimerInput(0, 0, 2, 1, TimeUnit.SECONDS))
    }


    fun getNextEquation(){
        currentExercise = exerciseProvider.getEquation()
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
                provideError(e.message)
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
                provideError(e.message)
            }
        }, TimerInput(0, 0, 30, 1, TimeUnit.SECONDS))
    }



    
}