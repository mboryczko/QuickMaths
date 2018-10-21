package pl.michalboryczko.quickmaths

import android.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Test

import org.junit.Rule
import pl.michalboryczko.quickmaths.interactor.TimerUseCase
import pl.michalboryczko.quickmaths.model.TimerInput
import java.util.concurrent.TimeUnit
import io.reactivex.schedulers.TestScheduler
import timber.log.Timber


class TimerUseCaseTests {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    val scheduler = TestScheduler()
    val timerUseCase by lazy { TimerUseCase(scheduler, scheduler) }

    @Test
    fun testCoundownInMilliseconds(){
        val input = TimerInput(0, 500, 0, 1, TimeUnit.MILLISECONDS)
        val expectedOutput = mutableListOf<Long>()
        for(i in 500 downTo  0){ expectedOutput.add(i.toLong()) }

        val testObserver = timerUseCase
                .observable(input)
                .test()

        scheduler.advanceTimeBy(500, TimeUnit.MILLISECONDS)
        testObserver.assertValues(*(expectedOutput).toTypedArray())
    }

    @Test
    fun testCoundown(){
        val input = TimerInput(0, 5, 0, 1, TimeUnit.SECONDS)
        val expectedOutput = arrayOf<Long>(5, 4, 3, 2, 1, 0)

        val testObserver = timerUseCase
                .observable(input)
                .test()

        scheduler.advanceTimeBy(5, TimeUnit.SECONDS)
        testObserver.assertValues(*expectedOutput)
    }

    @Test
    fun testCounter(){
        val input = TimerInput(0, 0, 6, 1, TimeUnit.SECONDS)
        val expectedOutput = arrayOf<Long>(0, 1, 2, 3, 4, 5, 6)

        val testObserver = timerUseCase
                .observable(input)
                .test()

        scheduler.advanceTimeBy(6, TimeUnit.SECONDS)
        testObserver.assertValues(*expectedOutput)
    }
}
