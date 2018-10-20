package pl.michalboryczko.quickmaths.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import pl.michalboryczko.quickmaths.source.api.FirebaseApiService
import pl.michalboryczko.quickmaths.source.database.AppDatabase
import pl.michalboryczko.quickmaths.source.database.dao.QuestionDAO
import pl.michalboryczko.quickmaths.source.repository.UserRepository
import pl.michalboryczko.quickmaths.source.repository.UserRepositoryImpl

/**
 * Created by ${michal_boryczko} on 13.06.2018.
 */
@Module
class InteractorModule {

    @Provides
    fun provideUserRepository(firebaseApiService: FirebaseApiService): UserRepository{
        return UserRepositoryImpl(firebaseApiService)
    }

}