package pl.michalboryczko.quickmaths

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import org.junit.Test

import org.junit.Rule
import pl.michalboryczko.quickmaths.interactor.TimerUseCase
import pl.michalboryczko.quickmaths.model.TimerInput
import java.util.concurrent.TimeUnit
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import pl.michalboryczko.quickmaths.model.Exercise
import pl.michalboryczko.quickmaths.ui.game.GameViewModel

import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.Mockito.`when`
import org.mockito.stubbing.OngoingStubbing


class GameViewModelTests: BaseTest() {

    val scheduler = TestScheduler()
    val timerUseCase by lazy { TimerUseCase(scheduler, scheduler) }

    val observerState = mock<Observer<Int>>()
    val viewmodel by lazy { GameViewModel(timerUseCase) }


    @Before
    fun setUp(){
        viewmodel.initViewModel(1)
    }

    @Test
    fun testCounter(){
        viewmodel.getNextEquation()
        viewmodel.startTimer()
        viewmodel.okClicked(viewmodel.currentExercise.result)
        viewmodel.pointsValue.observeForever(observerState)
        verify(observerState).onChanged(1)
    }
}
