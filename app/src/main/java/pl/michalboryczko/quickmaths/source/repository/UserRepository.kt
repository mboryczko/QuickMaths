package pl.michalboryczko.quickmaths.source.repository

import io.reactivex.Single
import pl.michalboryczko.quickmaths.model.LoginInput
import pl.michalboryczko.quickmaths.model.User

interface UserRepository {
    fun logIn(input: LoginInput): Single<Boolean>
    fun isUserLoggedIn(): Single<Boolean>
    fun createUser(user: User): Single<Boolean>
}