package pl.michalboryczko.quickmaths.ui.find

import android.arch.lifecycle.MutableLiveData
import pl.michalboryczko.quickmaths.R
import pl.michalboryczko.quickmaths.app.BaseViewModel
import pl.michalboryczko.quickmaths.interactor.InternetConnectivityChecker
import pl.michalboryczko.quickmaths.model.User
import pl.michalboryczko.quickmaths.model.exceptions.ApiErrorMessageException
import pl.michalboryczko.quickmaths.model.exceptions.NoInternetAccessException
import pl.michalboryczko.quickmaths.source.repository.UserRepository
import timber.log.Timber
import javax.inject.Inject

class FindFriendViewModel
@Inject constructor(val userRepository: UserRepository,
                    internetConnectivityChecker: InternetConnectivityChecker
) : BaseViewModel() {

    val internetConnection: MutableLiveData<Boolean> = MutableLiveData()
    val userObservable: MutableLiveData<User> = MutableLiveData()


    init {
        disposables.add(
                internetConnectivityChecker
                        .isInternetAvailableObservable()
                        .subscribe{ internetConnection.value = it }
        )
    }


    fun searchFriendClicked(friendEmail: String){
        disposables.add(
                userRepository.findFriendByEmail(friendEmail)
                        .subscribe(
                                {
                                    Timber.d("friend found")
                                    userObservable.value = it },
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


}