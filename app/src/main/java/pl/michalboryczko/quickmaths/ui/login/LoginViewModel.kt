package pl.michalboryczko.quickmaths.ui.login

import android.arch.lifecycle.MutableLiveData
import pl.michalboryczko.quickmaths.app.BaseViewModel
import pl.michalboryczko.quickmaths.model.BaseCommunication
import pl.michalboryczko.quickmaths.model.LoginInput
import pl.michalboryczko.quickmaths.model.Resource
import pl.michalboryczko.quickmaths.model.State
import pl.michalboryczko.quickmaths.model.login.LoginCommunication
import pl.michalboryczko.quickmaths.source.repository.QuestionRepository
import pl.michalboryczko.quickmaths.source.repository.UserRepository
import javax.inject.Inject

class LoginViewModel
@Inject constructor(
        private var repository: UserRepository

) : BaseViewModel() {

    val errorInformation: MutableLiveData<Resource<String>> = MutableLiveData()

    init {

    }

    fun loginClicked(loginInput: LoginInput){
        errorInformation.value = Resource.loading()
        repository.logIn(loginInput)
                .subscribe(
                        {
                            errorInformation.value = Resource.success("zalogowano pomyślnie")
                        },
                        {
                            errorInformation.value = Resource.error("błąd podczas logowania")
                        }
                )
    }


}