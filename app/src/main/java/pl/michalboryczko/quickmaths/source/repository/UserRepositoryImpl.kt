package pl.michalboryczko.quickmaths.source.repository

import io.reactivex.Single
import pl.michalboryczko.quickmaths.model.LoginInput
import pl.michalboryczko.quickmaths.model.User
import pl.michalboryczko.quickmaths.source.api.FirebaseApiService
import javax.inject.Inject

class UserRepositoryImpl
@Inject constructor(private var firebaseApiService: FirebaseApiService): UserRepository {
    override fun logIn(input: LoginInput): Single<Boolean> {
        return firebaseApiService.logIn(input)
    }

    override fun isUserLoggedIn(): Single<Boolean> {
        return firebaseApiService.isUserLoggedIn()
    }

    override fun createUser(user: User): Single<Boolean> {
        return firebaseApiService.createUser(user)
    }
}