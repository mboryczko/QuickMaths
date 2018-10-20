package pl.michalboryczko.quickmaths.source.api

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.*
import pl.michalboryczko.quickmaths.model.User
import javax.inject.Inject
import javax.inject.Singleton
import pl.michalboryczko.quickmaths.model.LoginInput
import pl.michalboryczko.quickmaths.source.repository.UserRepository


/**
 * Created by mjbor on 6/11/2018.
 */

@Singleton
class FirebaseApiService
@Inject constructor() : UserRepository {


    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun logIn(input: LoginInput): Single<Boolean> {
        return Single.just(true)
    }

    override fun isUserLoggedIn(): Single<Boolean> {
        return Single.just(auth.currentUser)
                .map { it != null }
    }

    override fun createUser(user: User): Single<Boolean> {
        return Single.create { emitter ->
                auth.createUserWithEmailAndPassword(user.email, user.password)
                        .addOnSuccessListener{ emitter.onSuccess(true) }
                        .addOnFailureListener{
                            emitter.onSuccess(false)
                            Log.d("firebaseLog", "message: ${it.localizedMessage}")
                            Log.d("firebaseLog", "tostring: ${it.toString()}")
                        }
            }
    }
}