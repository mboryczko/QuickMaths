package pl.michalboryczko.quickmaths.ui.register

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.util.Log
import pl.michalboryczko.quickmaths.app.BaseViewModel
import pl.michalboryczko.quickmaths.interactor.InternetConnectivityChecker
import pl.michalboryczko.quickmaths.model.User
import pl.michalboryczko.quickmaths.model.exceptions.ApiErrorMessageException
import pl.michalboryczko.quickmaths.model.exceptions.NoInternetAccessException
import pl.michalboryczko.quickmaths.source.repository.UserRepository
import javax.inject.Inject
import pl.michalboryczko.quickmaths.R
import pl.michalboryczko.quickmaths.model.register.RegisterError
import java.util.regex.Pattern


/**
 * Created by ${michal_boryczko} on 11.06.2018.
 */
class RegisterViewModel
    @Inject constructor(val userRepository: UserRepository,
                        val internetConnectivityChecker: InternetConnectivityChecker
    ) :BaseViewModel() {

    val internetConnection: MutableLiveData<Boolean> = MutableLiveData()
    val errorInformation: MutableLiveData<RegisterError> = MutableLiveData()
    var error = RegisterError()


    init {
         internetConnectivityChecker.isInternetAvailableObservable()
                .subscribe{ internetConnection.value = it }
    }

    fun registerClicked(user: User){
        userRepository
                .createUser(user)
                .subscribe(
                        {
                            Log.d("apiLog", "subscribed success")
                            toastInfo.value = "Account created" },
                        {
                            if(it is NoInternetAccessException){
                                Log.d("apiLog", "subscribed error no internett")
                                toastInfo.value = "no internet connection baby"
                            }
                            else if (it is ApiErrorMessageException){
                                Log.d("apiLog", "subscribed error ${it.msg}")
                                toastInfo.value = it.msg
                            }

                            else{
                                Log.d("apiLog", "subscribed error ${it.localizedMessage}")
                                toastInfo.value = it.localizedMessage
                            }
                        }
                )
    }

    fun validateUser(user: User){
        isEmailValid(user.email)
        isPasswordValid(user.password)
        isUsernameValid(user.username)
    }

    private fun isPasswordValid(password: String){
        val charNumbers = listOf('0', '1','2', '3', '4', '5', '6', '7', '8', '9')
        val numbers = password
                .asSequence()
                .filter { it in charNumbers }
                .count()

        if(password.length < 3)
            errorInformation.value = error.copy(passwordError = R.string.invalid_password_too_short)

        if(numbers == 0)
            errorInformation.value = error.copy(passwordError = R.string.invalid_password_no_number)


    }

    private fun isUsernameValid(username: String){
        if(username.length < 3)
            errorInformation.value = error.copy(usernameError = R.string.invalid_username_too_short)
    }


    fun isEmailValid(email: String) {
        val before = email.substringBefore("@")
        val after = email.substringAfter("@")
        val statement = before.isNotEmpty() && after.isNotEmpty() && email.contains("@")

        if(statement)
            errorInformation.value = error.copy(emailError = R.string.invalid_email)
    }
}