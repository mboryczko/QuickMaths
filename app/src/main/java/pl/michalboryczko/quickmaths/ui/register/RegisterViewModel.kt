package pl.michalboryczko.quickmaths.ui.register

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import io.reactivex.Observable
import pl.michalboryczko.quickmaths.app.BaseViewModel
import pl.michalboryczko.quickmaths.model.Question
import pl.michalboryczko.quickmaths.model.User
import pl.michalboryczko.quickmaths.source.repository.QuestionRepository
import pl.michalboryczko.quickmaths.source.repository.UserRepository
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by ${michal_boryczko} on 11.06.2018.
 */
class RegisterViewModel
    @Inject constructor(val userRepository: UserRepository) :BaseViewModel() {


    fun registerClicked(){
        userRepository.createUser(User("a@b.com", "testtest1", "test"))
                .subscribe(
                        {e -> Log.d("firebaseLog", "success: $e")},
                        {e -> Log.d("firebaseLog", "error: $e")}
                )
    }


    
}