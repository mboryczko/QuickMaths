package pl.michalboryczko.quickmaths.extensions

import android.annotation.SuppressLint
import android.app.Activity
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import pl.michalboryczko.quickmaths.R

/**
 * Created by ${michal_boryczko} on 13.06.2018.
 */
@SuppressLint("InflateParams")
fun Activity.makeToastFromText(text: String, gravity: Int = Gravity.CENTER) {
    val toast = Toast(this)
    toast.duration = Toast.LENGTH_LONG
    val view = this.layoutInflater.inflate(R.layout.toast, null)
    val textView: TextView = view.findViewById(R.id.toastText)
    textView.text = text
    toast.view = view
    toast.setGravity(gravity, 0, 0)
    toast.show()
}