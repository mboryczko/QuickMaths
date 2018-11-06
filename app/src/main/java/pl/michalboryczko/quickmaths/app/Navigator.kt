package pl.michalboryczko.quickmaths.app

import android.app.Activity
import pl.michalboryczko.quickmaths.ui.game.GameActivity
import pl.michalboryczko.quickmaths.ui.login.LoginActivity
import pl.michalboryczko.quickmaths.ui.register.RegisterActivity
import javax.inject.Singleton

@Singleton
class Navigator {
    fun navigateToGameActivity(activity: Activity, level: Int) = activity.apply { startActivity(GameActivity.prepareIntent(activity, level)) }
    fun navigateToRegisterActivity(activity: Activity) = activity.apply { startActivity(RegisterActivity.prepareIntent(activity))}
    fun navigateToLoginActivity(activity: Activity) = activity.apply { startActivity(LoginActivity.prepareIntent(activity))}
}