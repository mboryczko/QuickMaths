package pl.michalboryczko.quickmaths.base

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver

/**
 * Abstract class for a Use Case (Interactor in terms of Clean Architecture).
 * This interface represents a execution unit for different use cases (this means any use case
 * in the application should implement this contract).
 *
 * By convention each UseCase implementation will return the result using a [DisposableObserver]
 * that will execute its job in a background thread and will post the result in the UI thread.
 *
 * Class is parametrized with two types, where:
 * @param T the input value type
 * @param R the output value type
 */

abstract class ObservableUseCase<in T, R> protected constructor(
        private val subscribeScheduler: Scheduler,
        private val observeOnScheduler: Scheduler
) {

    /**
     * Builds an [Observable] which will be used when executing the current [ObservableUseCase].
     */
    protected abstract fun buildUseCaseObservable(p: T): Observable<R>

    /**
     * Returns built [Observable]. This method is for composing and reusing existed UseCases
     */
    internal fun observable(params: T): Observable<R>
            = buildUseCaseObservable(params)
            .subscribeOn(subscribeScheduler)
            .observeOn(observeOnScheduler)

}