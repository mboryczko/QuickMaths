package pl.michalboryczko.quickmaths.ui.register

import android.arch.lifecycle.MutableLiveData
import pl.michalboryczko.quickmaths.app.BaseViewModel
import pl.michalboryczko.quickmaths.interactor.InternetConnectivityChecker
import pl.michalboryczko.quickmaths.model.User
import pl.michalboryczko.quickmaths.model.exceptions.ApiErrorMessageException
import pl.michalboryczko.quickmaths.model.exceptions.NoInternetAccessException
import pl.michalboryczko.quickmaths.source.repository.UserRepository
import javax.inject.Inject
import pl.michalboryczko.quickmaths.R
import pl.michalboryczko.quickmaths.model.register.RegisterError
import timber.log.Timber


/**
 * Created by ${michal_boryczko} on 11.06.2018.
 */
class RegisterViewModel
    @Inject constructor(val userRepository: UserRepository,
                        internetConnectivityChecker: InternetConnectivityChecker
    ) :BaseViewModel() {

    val internetConnection: MutableLiveData<Boolean> = MutableLiveData()
    val errorInformation: MutableLiveData<RegisterError> = MutableLiveData()
    var error = RegisterError()


    init {
         disposables.add(
             internetConnectivityChecker
                     .isInternetAvailableObservable()
                     .subscribe{ internetConnection.value = it }
         )
    }

    fun registerClicked(user: User){
        disposables.add(
                userRepository
                .createUser(user)
                .subscribe(
                        {
                            Timber.d("subscribed success")
                            toastInfo.value = "Account created" },
                        {
                            when(it){
                                is NoInternetAccessException -> toastInfoResource.value = R.string.no_internet
                                is ApiErrorMessageException -> toastInfo.value = it.msg
                                else -> toastInfo.value = it.localizedMessage
                            }
                        }
                )
        )

    }

    fun validateUser(user: User): Boolean{
        errorInformation.value = null
        return (
                isEmailValid(user.email)
                        && isPasswordValid(user.password)
                        && isUsernameValid(user.username)
                )

    }

    private fun isPasswordValid(password: String): Boolean{
        val charNumbers = listOf('0', '1','2', '3', '4', '5', '6', '7', '8', '9')
        val numbers = password
                .asSequence()
                .filter { it in charNumbers }
                .count()

        if(password.length < 3){
            errorInformation.value = error.copy(passwordError = R.string.invalid_password_too_short)
            return false
        }

        if(numbers == 0){
            errorInformation.value = error.copy(passwordError = R.string.invalid_password_no_number)
            return false
        }

        return true
    }

    private fun isUsernameValid(username: String): Boolean{
        if(username.length < 3){
            errorInformation.value = error.copy(usernameError = R.string.invalid_username_too_short)
            return false
        }

        return true
    }


    private fun isEmailValid(email: String): Boolean {
        val before = email.substringBefore("@")
        val after = email.substringAfter("@")
        val statement = before.isNotEmpty() && after.isNotEmpty() && email.contains("@")

        if(!statement){
            errorInformation.value = error.copy(emailError = R.string.invalid_email)
            return false
        }

        return true
    }
}