package pl.michalboryczko.quickmaths.base

import io.reactivex.Observable
import io.reactivex.Scheduler


abstract class BaseObservable<in T, R> protected constructor(
        private val subscribeScheduler: Scheduler,
        private val observeOnScheduler: Scheduler
) {


    protected abstract fun buildUseCaseObservable(p: T): Observable<R>
    internal fun buildObservable(params: T): Observable<R>
            = this.buildUseCaseObservable(params)
                .subscribeOn(subscribeScheduler)
                .observeOn(observeOnScheduler)


}