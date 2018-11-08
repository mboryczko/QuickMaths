package pl.michalboryczko.quickmaths.ui.find

import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_find_friend.*
import pl.michalboryczko.quickmaths.app.BaseActivity
import pl.michalboryczko.quickmaths.R

class FindFriendActivity : BaseActivity<FindFriendViewModel>() {
    companion object {
        fun prepareIntent(activity: Activity) = Intent(activity, FindFriendActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_friend)

        viewModel.userObservable.observe(this, Observer{
            it?.let {
                usernameTextView.text = it.username
                emailTextView.text = it.email
            }
        })
        friendSearchButton.setOnClickListener { viewModel.searchFriendClicked(friendEmailEditText.text.toString()) }
    }

    override fun initViewModel() {
        viewModel = getGenericViewModel()
    }
}
