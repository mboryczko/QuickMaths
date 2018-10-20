package pl.michalboryczko.quickmaths.ui.register

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_register.*
import pl.michalboryczko.quickmaths.R
import pl.michalboryczko.quickmaths.app.BaseActivity

class RegisterActivity : BaseActivity<RegisterViewModel>() {

    companion object {
        fun prepareIntent(activity: Activity) = Intent(activity, RegisterActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        registerButton.setOnClickListener { viewModel.registerClicked() }
    }

    override fun initViewModel() {
        viewModel = getGenericViewModel()
    }
}
