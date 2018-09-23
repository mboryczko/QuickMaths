package pl.michalboryczko.quickmaths.ui.game

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_game.*
import pl.michalboryczko.quickmaths.R
import pl.michalboryczko.quickmaths.app.BaseActivity

class GameActivity : BaseActivity() {

    lateinit var viewModel : GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        viewModel.timerInfo.observe(this, Observer{ timerTextView.text = it })
        viewModel.timerValue.observe(this, Observer{ timerValueTextView.text = it })

        viewModel.instruction.observe(this, Observer{ instructionTextView.text = it })
        viewModel.instructionColor.observe(this, Observer{ if(it != null) instructionTextView.setTextColor(it) })
        viewModel.pointsValue.observe(this, Observer{ scoreValueTextView.text = it.toString() })

        okButton.setOnClickListener {
            val input = userInputTextView.text.toString()
            if(input.isNotBlank()){
                viewModel.okClicked(userInputTextView.text.toString().toLong())
                userInputTextView.setText("")
            }
        }
        nextButton.setOnClickListener { viewModel.nextClicked() }
    }

    override fun initViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(GameViewModel::class.java)
    }
}
