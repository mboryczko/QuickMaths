package pl.michalboryczko.quickmaths.di.modules

import android.arch.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import io.reactivex.schedulers.Schedulers
import pl.michalboryczko.quickmaths.di.ViewModelKey
import pl.michalboryczko.quickmaths.interactor.TimerUseCase
import pl.michalboryczko.quickmaths.ui.game.GameActivity
import pl.michalboryczko.quickmaths.ui.game.GameViewModel
import pl.michalboryczko.quickmaths.ui.main.MainActivity
import pl.michalboryczko.quickmaths.ui.main.MainViewModel
import pl.michalboryczko.quickmaths.ui.register.RegisterActivity
import pl.michalboryczko.quickmaths.ui.register.RegisterViewModel

/**
 * Created by ${michal_boryczko} on 11.06.2018.
 */
@Module
internal abstract class MainModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(GameViewModel::class)
    abstract fun bindGameViewModel(viewModel: GameViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RegisterViewModel::class)
    abstract fun bindRegisterViewModel(viewModel: RegisterViewModel): ViewModel


    @ContributesAndroidInjector
    internal abstract fun mainActivity(): MainActivity


    @ContributesAndroidInjector
    internal abstract fun gameActivity(): GameActivity

    @ContributesAndroidInjector
    internal abstract fun registerActivity(): RegisterActivity







}