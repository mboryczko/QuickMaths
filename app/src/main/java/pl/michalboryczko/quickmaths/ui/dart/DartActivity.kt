package pl.michalboryczko.quickmaths.ui.dart

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_dart.*
import pl.michalboryczko.quickmaths.R
import pl.michalboryczko.quickmaths.app.BaseActivity

class DartActivity : BaseActivity<DartViewModel>() {


    companion object {
        fun prepareIntent(activity: Activity) = Intent()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dart)

        dartboardImageView.setOnClickListener{ viewModel.dartClicked() }
    }

}
