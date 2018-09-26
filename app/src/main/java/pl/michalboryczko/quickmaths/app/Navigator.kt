package pl.michalboryczko.quickmaths.app

import android.app.Activity
import pl.michalboryczko.quickmaths.ui.game.GameActivity
import javax.inject.Singleton

@Singleton
class Navigator {
    fun navigateToGameActivity(activity: Activity, level: Int) = activity.apply { startActivity(GameActivity.prepareIntent(activity, level)) }
}