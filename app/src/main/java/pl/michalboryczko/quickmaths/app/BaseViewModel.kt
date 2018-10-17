package pl.michalboryczko.quickmaths.app

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.PersistableBundle
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * Created by ${michal_boryczko} on 12.06.2018.
 */
open abstract class BaseViewModel : ViewModel() {


    protected val disposables :MutableList<Disposable> = mutableListOf()

    override fun onCleared() {
        super.onCleared()
        disposables
                .filter { it.isDisposed }
                .forEach { it.dispose() }
    }
}