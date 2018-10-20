package pl.michalboryczko.quickmaths.ui.main

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import pl.michalboryczko.quickmaths.R
import pl.michalboryczko.quickmaths.app.BaseActivity
import pl.michalboryczko.quickmaths.ui.game.GameActivity

class MainActivity : BaseActivity<MainViewModel>() {

    companion object {
        fun prepareIntent(activity: Activity) = Intent(activity, MainActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        levelOneButtonutton.setOnClickListener { navigator.navigateToGameActivity(this, 1) }
        levelTwoButton.setOnClickListener { navigator.navigateToGameActivity(this, 2) }
        levelThreeButton.setOnClickListener { navigator.navigateToGameActivity(this, 3) }

        dartButton.setOnClickListener { navigator.navigateToRegisterActivity(this)}


    }

    override fun initViewModel(){
        viewModel = getGenericViewModel()
    }

}
