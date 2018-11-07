package pl.michalboryczko.quickmaths.ui.login

import android.arch.lifecycle.MutableLiveData
import pl.michalboryczko.quickmaths.app.BaseViewModel
import pl.michalboryczko.quickmaths.interactor.InternetConnectivityChecker
import pl.michalboryczko.quickmaths.model.EmptyResource
import pl.michalboryczko.quickmaths.model.LoginInput
import pl.michalboryczko.quickmaths.R
import pl.michalboryczko.quickmaths.model.exceptions.ApiErrorMessageException
import pl.michalboryczko.quickmaths.model.exceptions.NoInternetAccessException
import pl.michalboryczko.quickmaths.source.repository.UserRepository
import javax.inject.Inject

class LoginViewModel
@Inject constructor(
        private var repository: UserRepository,
        internetConnectivityChecker: InternetConnectivityChecker

) : BaseViewModel() {

    val loginStatus: MutableLiveData<EmptyResource> = MutableLiveData()
    val internetConnection: MutableLiveData<Boolean> = MutableLiveData()

    init {
        disposables.add(
                internetConnectivityChecker
                        .isInternetAvailableObservable()
                        .subscribe{ internetConnection.value = it }
        )
    }

    fun loginClicked(loginInput: LoginInput){
        loginStatus.value = EmptyResource.loading()
        if(validateLoginInput(loginInput))
            loginUser(loginInput)
    }

    private fun loginUser(loginInput: LoginInput){
        disposables.add(
                repository
                        .logIn(loginInput)
                        .subscribe(
                                {
                                    loginStatus.value = EmptyResource.success()
                                },
                                {

                                    when(it){
                                        is NoInternetAccessException -> loginStatus.value = EmptyResource.error(R.string.no_internet)
                                        is ApiErrorMessageException -> {
                                            toastInfo.value = it.msg
                                            loginStatus.value = EmptyResource.error(null)
                                        }
                                        else -> loginStatus.value = EmptyResource.error(R.string.unknown_error)
                                    }
                                }
                        )
        )
    }

    fun validateLoginInput(loginInput: LoginInput): Boolean{
        return ( isLoginValid(loginInput.email)
                    && isPasswordValid(loginInput.password) )
    }

    private fun isLoginValid(login: String): Boolean{
        if(login.isEmpty()){
            loginStatus.value = EmptyResource.error(R.string.login_empty)
            return false
        }

        return true
    }

    private fun isPasswordValid(password: String): Boolean{
        if(password.isEmpty()){
            loginStatus.value = EmptyResource.error(R.string.password_empty)
            return false
        }

        return true
    }


}