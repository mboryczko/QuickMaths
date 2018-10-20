package pl.michalboryczko.quickmaths.ui.game

import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_game.*
import pl.michalboryczko.quickmaths.R
import pl.michalboryczko.quickmaths.app.BaseActivity
import pl.michalboryczko.quickmaths.utils.Constants

class GameActivity : BaseActivity<GameViewModel>() {

    companion object {
        fun prepareIntent(activity: Activity, level: Int) = Intent(activity, GameActivity::class.java)
                .putExtra(Constants.GAME_LEVEL, level)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        if(intent.hasExtra(Constants.GAME_LEVEL))
            viewModel.initViewModel(intent.getIntExtra(Constants.GAME_LEVEL, 1))

        viewModel.timerInfo.observe(this, Observer{ timerTextView.text = it })
        viewModel.timerValue.observe(this, Observer{ timerValueTextView.text = it })

        viewModel.instruction.observe(this, Observer{ instructionTextView.text = it })
        viewModel.instructionColor.observe(this, Observer{ if(it != null) instructionTextView.setTextColor(it) })
        viewModel.pointsValue.observe(this, Observer{ scoreValueTextView.text = it.toString() })

        viewModel.errorMessage.observe(this, Observer { Toast.makeText(this, it, Toast.LENGTH_SHORT).show() })

        okButton.setOnClickListener {
            val input = userInputTextView.text.toString()
            if(input.isNotBlank()){
                viewModel.okClicked(userInputTextView.text.toString().toInt())
                userInputTextView.setText("")
            }
        }
        nextButton.setOnClickListener { viewModel.nextClicked() }

    }

    override fun initViewModel() {
        viewModel = getGenericViewModel()
    }
}
