package pl.michalboryczko.quickmaths.ui.register

import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_register.*
import pl.michalboryczko.quickmaths.R
import pl.michalboryczko.quickmaths.app.BaseActivity
import pl.michalboryczko.quickmaths.model.User

class RegisterActivity : BaseActivity<RegisterViewModel>() {

    companion object {
        fun prepareIntent(activity: Activity) = Intent(activity, RegisterActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        viewModel.internetConnection.observe(this, Observer{ internetTextView.visibility = if(it!!) View.GONE else View.VISIBLE})
        viewModel.internetConnection.observe(this, Observer{
            it?.let {
                internetTextView.visibility = if(it) View.GONE else View.VISIBLE
            }
        })


        registerButton.setOnClickListener { viewModel.registerClicked(
                User
                (
                        emailEditText.text.toString(),
                        passwordEditText.text.toString(),
                        usernameEditText.text.toString()
                )
        )}
    }

    override fun initViewModel() {
        viewModel = getGenericViewModel()
    }
}
