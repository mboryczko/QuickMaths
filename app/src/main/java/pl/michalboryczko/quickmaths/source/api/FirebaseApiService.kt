package pl.michalboryczko.quickmaths.source.api

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.*
import pl.michalboryczko.quickmaths.model.User
import javax.inject.Inject
import javax.inject.Singleton
import pl.michalboryczko.quickmaths.model.LoginInput
import pl.michalboryczko.quickmaths.source.repository.UserRepository
import java.lang.Exception




/**
 * Created by mjbor on 6/11/2018.
 */

@Singleton
class FirebaseApiService
@Inject constructor() : UserRepository {

    init {
    }

    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val db: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

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
                            emitter.onError(it)
                        }
            }
    }

    override fun findFriendByEmail(email: String): Single<User> {
        val dbRef = db.collection("users");
        return Single
                .create { emitter ->
                    dbRef
                            .whereEqualTo("email", email)
                            .get()
                            .addOnCompleteListener{ task ->
                                if (task.isSuccessful) {
                                    val document = task.result
                                    document?.let {
                                        val foundUsers = it.toObjects(User::class.java)
                                        if(foundUsers.isNotEmpty())
                                            emitter.onSuccess(foundUsers.first())
                                        else
                                            emitter.onError(NoSuchElementException())
                                    }
                                } else {
                                    emitter.onError(Exception("no friend found"))
                                }
                            }
                }
    }

    fun saveUserToDatabase(user: User): Single<Boolean> {
        return Single
                .create { emitter ->
                    db.collection("users")
                            .add(user)
                            .addOnSuccessListener { emitter.onSuccess(true) }
                            .addOnFailureListener{ emitter.onError(it)}
                }
    }
}