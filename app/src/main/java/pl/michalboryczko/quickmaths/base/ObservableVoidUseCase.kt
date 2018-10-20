package pl.michalboryczko.quickmaths.base

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.observers.DisposableObserver
/**
 * Simple wrapper for [ObservableUseCase] to omit input type parameter
 *
 * @param R the output value type
 */
abstract class ObservableVoidUseCase<R> protected constructor(
        private val subscribeScheduler: Scheduler,
        private val observeOnScheduler: Scheduler
) : ObservableUseCase<Unit, R>(subscribeScheduler, observeOnScheduler) {

    override fun buildUseCaseObservable(item: Unit): Observable<R> = buildUseCaseObservable()

    protected abstract fun buildUseCaseObservable(): Observable<R>

    internal fun observable(): Observable<R> = buildUseCaseObservable()
}