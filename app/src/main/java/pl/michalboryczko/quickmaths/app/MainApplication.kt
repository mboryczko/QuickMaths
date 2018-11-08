package pl.michalboryczko.quickmaths.app

import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication
import com.facebook.stetho.Stetho
import com.google.firebase.FirebaseApp
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import pl.michalboryczko.quickmaths.di.DaggerAppComponent
import timber.log.Timber

/**
 * Created by ${michal_boryczko} on 11.06.2018.
 */
class MainApplication: DaggerApplication() {


    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        FirebaseApp.initializeApp(this)
        Stetho.initializeWithDefaults(this)
        Timber.plant(Timber.DebugTree())
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().create(this)
    }
}