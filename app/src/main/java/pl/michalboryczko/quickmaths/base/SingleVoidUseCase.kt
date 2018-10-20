package pl.michalboryczko.quickmaths.base

import io.reactivex.Single
import io.reactivex.observers.DisposableSingleObserver

/**
 * Simple wrapper for [SingleUseCase] to omit input type parameter
 *
 * @param R the output value type
 */
abstract class SingleVoidUseCase<R> protected constructor() : SingleUseCase<Unit, R>() {

    override fun buildUseCaseSingle(unit: Unit): Single<R> = buildUseCaseSingle()

    protected abstract fun buildUseCaseSingle(): Single<R>

    internal fun single(): Single<R> = buildUseCaseSingle()
}