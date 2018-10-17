package pl.michalboryczko.quickmaths.app

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject
import kotlin.reflect.KClass

/**
 * Created by ${michal_boryczko} on 12.06.2018.
 */

open abstract class BaseActivity<T: BaseViewModel> : DaggerAppCompatActivity() {

    @Inject lateinit var  viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel : T

    @Inject
    lateinit var navigator: Navigator


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    private fun initViewModel(){
        viewModel = ViewModelProviders.of(this, viewModelFactory).get( viewModel.javaClass)
    }
/*
    abstract fun initViewModel()*/


}