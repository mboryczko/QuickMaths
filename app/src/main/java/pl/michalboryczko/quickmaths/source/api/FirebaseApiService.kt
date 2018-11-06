package pl.michalboryczko.quickmaths.source.api

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.*
import pl.michalboryczko.quickmaths.interactor.InternetConnectivityChecker
import pl.michalboryczko.quickmaths.model.Exercise
import pl.michalboryczko.quickmaths.model.User
import javax.inject.Inject
import javax.inject.Singleton
import pl.michalboryczko.quickmaths.model.LoginInput
import pl.michalboryczko.quickmaths.model.exceptions.ApiErrorException
import pl.michalboryczko.quickmaths.model.exceptions.ApiErrorMessageException
import pl.michalboryczko.quickmaths.model.exceptions.NoInternetAccessException
import pl.michalboryczko.quickmaths.model.exceptions.UnauthorizedException
import pl.michalboryczko.quickmaths.source.repository.UserRepository
import retrofit2.Response
import java.lang.Exception


/**
 * Created by mjbor on 6/11/2018.
 */

@Singleton
class FirebaseApiService
@Inject constructor() : UserRepository {


    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun logIn(input: LoginInput): Single<Boolean> {
        return Single
                .create { emitter ->
                    auth.signInWithEmailAndPassword(input.email, input.password)
                            .addOnSuccessListener{
                                emitter.onSuccess(true)

                            }
                            .addOnFailureListener{
                                emitter.onError(Exception("no internet"))
                            }
                }
    }

    override fun isUserLoggedIn(): Single<Boolean> {
        return Single.just(auth.currentUser)
                .map { it != null }
    }

    override fun createUser(user: User): Single<Boolean> {
        return Single
                .create { emitter ->
                auth.createUserWithEmailAndPassword(user.email, user.password)
                        .addOnSuccessListener{

                            Log.d("apiLog", "onsuccess ")
                            emitter.onSuccess(true)

                        }
                        .addOnFailureListener{
                            Log.d("apiLog", "onfailure listener message: ${it.localizedMessage}")
                            emitter.onError(Exception("no internet"))
                        }
            }
    }

}