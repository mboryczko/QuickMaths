package pl.michalboryczko.quickmaths.interactor

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class InternetConnectivityChecker
@Inject constructor(val context: Context) {


    fun isInternetAvailable(): Boolean{
        return true
    }

    fun isInternetAvailableObservable(): Observable<Boolean> {
        return Observable.interval(0, 3, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map {
                    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                    val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
                    Timber.d("isInternet: ${activeNetwork?.isConnectedOrConnecting == true }")
                    activeNetwork?.isConnectedOrConnecting == true
                }
    }
}