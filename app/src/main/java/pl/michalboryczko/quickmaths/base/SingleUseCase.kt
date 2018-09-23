package pl.michalboryczko.quickmaths.base

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers


abstract class SingleUseCase<in T, R> {

    private val disposables: CompositeDisposable = CompositeDisposable()

    /**
     * Builds an [Single] which will be used when executing the current [SingleUseCase].
     */
    protected abstract fun buildUseCaseSingle(item: T): Single<R>

    /**
     * Returns built [Single]. This method is for composing and reusing existed UseCases
     */
    internal fun single(params: T): Single<R> = buildUseCaseSingle(params)

    /**
     * Executes the current use case.
     * @param observer [DisposableSingleObserver] which will be listening to the observable build
     * * by [.buildUseCaseSingle] ()} method.
     * @param params Parameters (Optional) used to build/execute this use case.
     */
    open fun execute(observer: DisposableSingleObserver<R>, params: T) {
        val single = this.buildUseCaseSingle(params)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
        addDisposable(single.subscribeWith(observer))
    }

    /**
     * Dispose from current [CompositeDisposable].
     */
    open fun dispose() {
        if (!disposables.isDisposed) {
            disposables.dispose()
        }
    }

    open fun checkStatus() =
            "UseCase is disposed: ${disposables.isDisposed}, size: ${disposables.size()}"


    /**
     * Clears all disposables
     */
    open fun clear() {
        if (!disposables.isDisposed) {
            disposables.clear()
        }
    }

    /**
     * Dispose from current [CompositeDisposable].
     */
    private fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }
}