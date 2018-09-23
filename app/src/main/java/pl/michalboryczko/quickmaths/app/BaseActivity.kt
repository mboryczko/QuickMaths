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
open abstract class BaseActivity : DaggerAppCompatActivity() {

    @Inject lateinit var  viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    abstract fun initViewModel()

    private val disposables :MutableList<Disposable> = mutableListOf()

    fun Disposable.addDisposable(){
        disposables.add(this)
    }

    override fun onStop() {
        super.onStop()
        for(disposable in disposables)
            if(!disposable.isDisposed)
                disposable.dispose()
    }
}