package com.globalhiddenodds.mobilenews.injection

import android.content.Context
import androidx.room.Room
import com.globalhiddenodds.mobilenews.datasource.database.AppRoomDatabase
import com.globalhiddenodds.mobilenews.repository.HitDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Provides
    fun provideHitDao(database: AppRoomDatabase): HitDao {
        return database.hitDao()
    }
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context):
            AppRoomDatabase {
        return Room.databaseBuilder(
            appContext,
            AppRoomDatabase::class.java,
            "local_hits_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}