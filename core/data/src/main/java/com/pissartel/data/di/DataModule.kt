package com.pissartel.data.di

import com.pissartel.data.repoimpl.MapPictureRepoImpl
import com.pissartel.data.repoimpl.ProfileRepoImpl
import com.pissartel.domain.repository.MapPictureRepository
import com.pissartel.domain.repository.ProfileRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Singleton
    @Provides
    fun provideMapPictureRepository(): MapPictureRepository = MapPictureRepoImpl()

    @Singleton
    @Provides
    fun provideProfileRepository(): ProfileRepository = ProfileRepoImpl()
}