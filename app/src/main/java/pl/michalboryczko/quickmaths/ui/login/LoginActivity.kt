package pl.michalboryczko.quickmaths.ui.login

import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import pl.michalboryczko.quickmaths.R
import kotlinx.android.synthetic.main.activity_login.*
import pl.michalboryczko.quickmaths.app.BaseActivity
import pl.michalboryczko.quickmaths.model.LoginInput
import pl.michalboryczko.quickmaths.model.Status

class LoginActivity : BaseActivity<LoginViewModel>() {

	companion object {
		fun prepareIntent(activity: Activity) = Intent(activity, LoginActivity::class.java)
	}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        viewModel.loginStatus.observe(this, Observer{
            it?.let {
                when(it.status){
                    Status.LOADING -> {
						showToastMessage("loading")
                    }

                    Status.SUCCESS -> {

						showToastMessage("success")
                    }

                    Status.ERROR -> {
						showToastMessage(it.message!!)
                    }

                }
            }
        })

        loginButton.setOnClickListener {
            viewModel.loginClicked(LoginInput(usernameEditText.text.toString(), passwordEditText.text.toString()))
        }

    }


    override fun initViewModel() {
        viewModel = getGenericViewModel()
    }
}
