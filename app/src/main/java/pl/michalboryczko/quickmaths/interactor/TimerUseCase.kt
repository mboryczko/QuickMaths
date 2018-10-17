package pl.michalboryczko.quickmaths.interactor

import io.reactivex.Observable
import io.reactivex.Scheduler
import pl.michalboryczko.quickmaths.base.BaseObservable
import pl.michalboryczko.quickmaths.base.ObservableUseCase
import pl.michalboryczko.quickmaths.model.TimerInput
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class TimerUseCase @Inject constructor(private val subscribeScheduler: Scheduler,
                                       private val observeOnScheduler: Scheduler): BaseObservable<TimerInput, Long>(subscribeScheduler, observeOnScheduler) {



    override fun buildUseCaseObservable(p: TimerInput): Observable<Long> {
        if(p.startTime <= p.endTime){
            //going up
            return Observable.interval(p.initialDelay, p.interval, p.timeUnit, subscribeScheduler)
                    .take(p.endTime + 1  - p.startTime)
        }

        else{
            //countdown
            return Observable.interval(p.initialDelay, p.interval, p.timeUnit, subscribeScheduler)
                    .map { p.startTime - it }
                    .take(p.startTime + 1  - p.endTime)
        }
    }
}