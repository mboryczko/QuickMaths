package pl.michalboryczko.quickmaths.di

import android.content.Context
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import pl.michalboryczko.quickmaths.app.MainApplication
import pl.michalboryczko.quickmaths.interactor.TimerUseCase
import javax.inject.Named

/**
 * Created by ${michal_boryczko} on 11.06.2018.
 */
@Module
class AppModule{

    @Provides
    fun providesContext(application: MainApplication): Context {
        return application.applicationContext
    }

    @Provides
    fun provideTimerUseCase(@Named("SubscribeScheduler") subscribeScheduler: Scheduler,
                            @Named("ObserveOnScheduler") observeOnScheduler: Scheduler ): TimerUseCase {
        return TimerUseCase(subscribeScheduler, observeOnScheduler)
    }

    @Provides
    @Named("SubscribeScheduler")
    fun provideSubscribeScheduler(): Scheduler{
        return Schedulers.computation()
    }

    @Provides
    @Named("ObserveOnScheduler")
    fun provideObserveOnScheduler(): Scheduler{
        return AndroidSchedulers.mainThread()
    }

}
