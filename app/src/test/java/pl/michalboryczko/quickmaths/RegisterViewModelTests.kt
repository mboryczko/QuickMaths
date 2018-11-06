package pl.michalboryczko.quickmaths

import android.arch.lifecycle.Observer
import io.reactivex.Observable
import org.junit.Assert.*
import org.junit.Test

import org.junit.Before

import org.mockito.Mockito.*
import pl.michalboryczko.quickmaths.interactor.InternetConnectivityChecker
import pl.michalboryczko.quickmaths.model.User
import pl.michalboryczko.quickmaths.model.register.RegisterError
import pl.michalboryczko.quickmaths.source.repository.UserRepository
import pl.michalboryczko.quickmaths.ui.register.RegisterViewModel




class RegisterViewModelTests: BaseTest() {

    private val observerBooleanState = mock<Observer<Boolean>>()
    private val observerErrorState = mock<Observer<RegisterError>>()
    private val checker = mock(InternetConnectivityChecker::class.java)
    private val repo = mock(UserRepository::class.java)
    private val viewmodel by lazy { RegisterViewModel(repo, checker) }


    @Before
    fun setUp(){
        whenever(checker.isInternetAvailableObservable()).thenReturn(Observable.just(true))
        viewmodel.errorInformation.observeForever(observerErrorState)
        viewmodel.errorInformation.value = RegisterError()
    }

    fun generateValidUser(): User{
        val email = random.generateRandomEmail()
        val username = random.generateRandomString(7)
        val password = random.generateStrongPassword()

        return User(email, password, username)
    }

    @Test
    fun internetCheckerTest(){
        viewmodel.internetConnection.observeForever(observerBooleanState)
    }


    @Test
    fun registerValidationsTest(){

        assertTrue(viewmodel.validateUser(generateValidUser()))
        /*assertNull( (viewmodel.errorInformation.value as RegisterError).emailError)
        assertNull( (viewmodel.errorInformation.value as RegisterError).passwordError)
        assertNull( (viewmodel.errorInformation.value as RegisterError).usernameError)*/
    }

    @Test
    fun registerValidationsUserEmptyFieldTest(){
        viewmodel.validateUser(User("", "", ""))
        assertNotNull( (viewmodel.errorInformation.value as RegisterError).emailError)

        viewmodel.validateUser(User(random.generateRandomString(), "", ""))
        assertNotNull( (viewmodel.errorInformation.value as RegisterError).emailError)

        viewmodel.validateUser(User("", random.generateStrongPassword(), ""))
        assertNotNull( (viewmodel.errorInformation.value as RegisterError).passwordError)
    }


}
