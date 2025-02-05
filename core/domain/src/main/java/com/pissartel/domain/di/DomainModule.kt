package com.pissartel.domain.di

import com.pissartel.domain.repository.MapPictureRepository
import com.pissartel.domain.repository.ProfileRepository
import com.pissartel.domain.usecase.AddLikeUseCase
import com.pissartel.domain.usecase.CreateNewMapItemUseCase
import com.pissartel.domain.usecase.RemoveLikeUseCase
import com.pissartel.domain.usecase.GetCloseMapPicturesUseCase
import com.pissartel.domain.usecase.GetFeedUseCase
import com.pissartel.domain.usecase.GetLikesUseCase
import com.pissartel.domain.usecase.GetMapPictureUseCase
import com.pissartel.domain.usecase.GetProfileMapPicturesUseCase
import com.pissartel.domain.usecase.GetProfilePictureUseCase
import com.pissartel.domain.usecase.GetProfileUseCase
import com.pissartel.domain.usecase.impl.GetMapPictureUseCaseImpl
import com.pissartel.domain.usecase.impl.GetProfilePictureUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Singleton
    @Provides
    fun provideGetCloseMapPicturesUseCase(mapPictureRepository: MapPictureRepository): GetCloseMapPicturesUseCase =
        GetCloseMapPicturesUseCase(mapPictureRepository::getCloseMapPictures)

    @Singleton
    @Provides
    fun provideGetFeedUseCase(mapPictureRepository: MapPictureRepository): GetFeedUseCase =
        GetFeedUseCase(mapPictureRepository::getFeed)

    @Singleton
    @Provides
    fun provideGetProfileUseCase(profileRepository: ProfileRepository): GetProfileUseCase =
        GetProfileUseCase(profileRepository::getProfile)


    @Singleton
    @Provides
    fun provideGetProfilePictureUseCase(profileRepository: ProfileRepository): GetProfilePictureUseCase =
        GetProfilePictureUseCaseImpl(profileRepository)

    @Singleton
    @Provides
    fun provideAddLikeUseCaseUseCase(profileRepository: ProfileRepository): AddLikeUseCase =
        AddLikeUseCase(profileRepository::addLike)


    @Singleton
    @Provides
    fun provideGetLikesUseCase(profileRepository: ProfileRepository): GetLikesUseCase =
        GetLikesUseCase(profileRepository::getLikes)

    @Singleton
    @Provides
    fun provideDeleteLikeUseCase(profileRepository: ProfileRepository): RemoveLikeUseCase =
        RemoveLikeUseCase(profileRepository::removeLike)

    @Singleton
    @Provides
    fun provideGetMapPictureUseCase(
        mapPictureRepository: MapPictureRepository,
        profileRepository: ProfileRepository
    ): GetMapPictureUseCase =
        GetMapPictureUseCaseImpl(mapPictureRepository, profileRepository)

    @Singleton
    @Provides
    fun provideCreateNewMapItemUseCase(profileRepository: ProfileRepository): CreateNewMapItemUseCase =
        CreateNewMapItemUseCase(profileRepository::createMapPictureItem)

    @Singleton
    @Provides
    fun provideGetProfileMapPicturesUse(profileRepository: ProfileRepository): GetProfileMapPicturesUseCase =
        GetProfileMapPicturesUseCase(profileRepository::getMapPictureItems)
}