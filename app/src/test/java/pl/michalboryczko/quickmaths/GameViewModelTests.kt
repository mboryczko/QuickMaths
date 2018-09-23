package pl.michalboryczko.quickmaths

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import org.junit.Test

import org.junit.Rule
import pl.michalboryczko.quickmaths.interactor.TimerUseCase
import pl.michalboryczko.quickmaths.model.TimerInput
import java.util.concurrent.TimeUnit
import io.reactivex.schedulers.TestScheduler
import pl.michalboryczko.quickmaths.model.Exercise
import pl.michalboryczko.quickmaths.ui.game.GameViewModel

import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.Mockito.`when`
import org.mockito.stubbing.OngoingStubbing


class GameViewModelTests {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    inline fun <reified T> mock() = Mockito.mock(T::class.java)
    inline fun <T> whenever(methodCall: T) : OngoingStubbing<T> =
            Mockito.`when`(methodCall)

    val scheduler = TestScheduler()
    val timerUseCase by lazy { TimerUseCase(scheduler, scheduler) }

    //val observerState = Observer<Int>{ System.out.print(21) }
    val observerState = mock<Observer<Int>>()
    val viewmodel by lazy { GameViewModel(timerUseCase) }


    @Test
    fun testCounter(){
        //so that time has passed and som value is prepared
        //scheduler.advanceTimeBy(10, TimeUnit.SECONDS)
        viewmodel.getNextEquation()
        viewmodel.startTimer()
        viewmodel.okClicked(viewmodel.currentExercise.result)
        viewmodel.pointsValue.observeForever(observerState)
        verify(observerState).onChanged(1)
    }
}
