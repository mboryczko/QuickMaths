package pl.michalboryczko.quickmaths.ui.login

import android.arch.lifecycle.MutableLiveData
import pl.michalboryczko.quickmaths.app.BaseViewModel
import pl.michalboryczko.quickmaths.model.EmptyResource
import pl.michalboryczko.quickmaths.model.LoginInput
import pl.michalboryczko.quickmaths.model.Resource
import pl.michalboryczko.quickmaths.model.exceptions.ApiErrorMessageException
import pl.michalboryczko.quickmaths.model.exceptions.NoInternetAccessException
import pl.michalboryczko.quickmaths.source.repository.UserRepository
import javax.inject.Inject

class LoginViewModel
@Inject constructor(
        private var repository: UserRepository

) : BaseViewModel() {

    val loginStatus: MutableLiveData<EmptyResource> = MutableLiveData()

    init {

    }

    fun loginClicked(loginInput: LoginInput){
        loginStatus.value = EmptyResource.loading()
        repository.logIn(loginInput)
                .subscribe(
                        {
                            loginStatus.value = EmptyResource.success()
                        },
                        {
							if(it is NoInternetAccessException)
								loginStatus.value = EmptyResource.error("internet access error")

							else if(it is ApiErrorMessageException)
								loginStatus.value = EmptyResource.error(it.msg)
							else
								loginStatus.value = EmptyResource.error(it.message)
                        }
                )
    }


}