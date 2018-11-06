package pl.michalboryczko.quickmaths.source.repository

import android.util.Log
import io.reactivex.Single
import io.reactivex.SingleTransformer
import pl.michalboryczko.quickmaths.interactor.InternetConnectivityChecker
import pl.michalboryczko.quickmaths.model.LoginInput
import pl.michalboryczko.quickmaths.model.User
import pl.michalboryczko.quickmaths.model.exceptions.ApiErrorMessageException
import pl.michalboryczko.quickmaths.model.exceptions.NoInternetAccessException
import pl.michalboryczko.quickmaths.model.exceptions.UnknownException
import pl.michalboryczko.quickmaths.source.api.FirebaseApiService
import java.io.IOException
import javax.inject.Inject

class UserRepositoryImpl
@Inject constructor(
        private var firebaseApiService: FirebaseApiService,
        private val checker: InternetConnectivityChecker
): UserRepository {
    override fun logIn(input: LoginInput): Single<Boolean> {
        return firebaseApiService.logIn(input)
                .compose(handleExceptions())
    }

    override fun isUserLoggedIn(): Single<Boolean> {
        return firebaseApiService.isUserLoggedIn()
                .compose(handleNetworkConnection())
                .compose(handleExceptions())
    }

    override fun createUser(user: User): Single<Boolean> {

        Log.d("apiLog", "create use called")
        return firebaseApiService.createUser(user)
                .compose(handleNetworkConnection())
                .compose(handleExceptions())
    }


    private fun <T> handleNetworkConnection(): SingleTransformer<T, T> = SingleTransformer {

        Log.d("apiLog", "handleNetworkConnection")
        it.flatMap {
            if(checker.isInternetAvailable()){
                Log.d("apiLog", "internet available")
                Single.just(it)
            }
            else{
                Log.d("apiLog", "internet not available")
                Single.error(NoInternetAccessException())
            }
        }
    }

    private fun <T> handleExceptions(): SingleTransformer<T, T> = SingleTransformer {
        it.onErrorResumeNext { throwable ->
            Single.error(when (throwable) {
                is UnknownException -> UnknownException("unexpected error")
                else -> {
                    if(!checker.isInternetAvailable()){
                        NoInternetAccessException()
                    }
                    else{
                        ApiErrorMessageException(throwable.localizedMessage)
                    }
                }
            })
        }
    }

}