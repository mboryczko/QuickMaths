package pl.michalboryczko.quickmaths.base

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers


abstract class SingleUseCase<in T, R> {

    /**
     * Builds an [Single] which will be used when executing the current [SingleUseCase].
     */
    protected abstract fun buildUseCaseSingle(params: T): Single<R>

    /**
     * Returns built [Single]. This method is for composing and reusing existed UseCases
     */
    internal fun single(params: T): Single<R>
            = buildUseCaseSingle(params)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())


}