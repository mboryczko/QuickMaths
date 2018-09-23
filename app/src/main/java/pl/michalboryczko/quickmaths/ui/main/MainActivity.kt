package pl.michalboryczko.quickmaths.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import pl.michalboryczko.quickmaths.R
import pl.michalboryczko.quickmaths.app.BaseActivity
import pl.michalboryczko.quickmaths.ui.game.GameActivity

class MainActivity : BaseActivity() {

    private lateinit var viewModel :MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.currentQuestion.observe(this, Observer{
            label.text = it?.question
        })

        questionCard.setOnClickListener {
            val i = Intent(this, GameActivity::class.java)
            startActivity(i)
        }
    }

    override fun initViewModel(){
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
    }

}
