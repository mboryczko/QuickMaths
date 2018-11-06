package pl.michalboryczko.quickmaths

import android.arch.lifecycle.Observer
import io.reactivex.Observable
import org.junit.Assert.*
import org.junit.Test

import org.junit.Before

import org.mockito.Mockito.*
import pl.michalboryczko.quickmaths.interactor.InternetConnectivityChecker
import pl.michalboryczko.quickmaths.model.EmptyResource
import pl.michalboryczko.quickmaths.model.LoginInput
import pl.michalboryczko.quickmaths.model.User
import pl.michalboryczko.quickmaths.model.register.RegisterError
import pl.michalboryczko.quickmaths.source.repository.UserRepository
import pl.michalboryczko.quickmaths.ui.login.LoginViewModel


class LoginViewModelTests: BaseTest() {

    private val observerState = mock<Observer<EmptyResource>>()
    private val checker = mock(InternetConnectivityChecker::class.java)
    private val repo = mock(UserRepository::class.java)
    private val viewmodel by lazy { LoginViewModel(repo, checker) }


    @Before
    fun setUp(){
        whenever(checker.isInternetAvailableObservable()).thenReturn(Observable.just(true))
        viewmodel.loginStatus.observeForever(observerState)
        viewmodel.loginStatus.value = EmptyResource.loading()
    }

    private fun generateLoginInput(): LoginInput{
        val email = random.generateRandomEmail()
        val password = random.generateStrongPassword()

        return LoginInput(email, password)
    }


    @Test
    fun loginValidationsTest(){
        assertTrue( viewmodel.validateLoginInput(generateLoginInput()))
        assertNull( (viewmodel.loginStatus.value as EmptyResource).message)
    }

    @Test
    fun loginValidationsEmptyFieldTest(){
        assertFalse(viewmodel.validateLoginInput(LoginInput("", random.generateStrongPassword())))
    }

    @Test
    fun passwordValidationsEmptyFieldTest(){
        assertFalse(viewmodel.validateLoginInput(LoginInput(random.generateRandomEmail(), "")))
    }


}
