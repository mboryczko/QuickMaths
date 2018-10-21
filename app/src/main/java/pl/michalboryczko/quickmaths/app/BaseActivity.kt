package pl.michalboryczko.quickmaths.app

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.disposables.Disposable
import pl.michalboryczko.quickmaths.interactor.InternetConnectivityChecker
import javax.inject.Inject

/**
 * Created by ${michal_boryczko} on 12.06.2018.
 */
open abstract class BaseActivity<T: BaseViewModel>  : DaggerAppCompatActivity() {

    @Inject lateinit var  viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var navigator: Navigator


    lateinit var viewModel : T


    inline fun <reified T: BaseViewModel> getGenericViewModel(): T {
        return ViewModelProviders.of(this, viewModelFactory).get(T::class.java)
    }

    fun showToastMessage(msg: String, isLong: Boolean = true){
        Toast.makeText(this, msg,
                if(isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT)
                .show()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        viewModel.toastInfo.observe(this, Observer{ showToastMessage(it!!)})
    }

    abstract fun initViewModel()

}