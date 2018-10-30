package pl.michalboryczko.quickmaths.ui.login

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*
import pl.michalboryczko.quickmaths.R
import pl.michalboryczko.quickmaths.app.BaseActivity
import pl.michalboryczko.quickmaths.app.BaseActivity_MembersInjector

class LoginActivity : BaseActivity<LoginViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        loginButton.setOnClickListener {
            viewModel.loginClicked()
        }

    }


    override fun initViewModel() {
        viewModel = getGenericViewModel()
    }
}
