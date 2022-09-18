package lt.jasiunasarnoldas.mbrainz.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import lt.jasiunasarnoldas.mbrainz.data.PlacesDataSource
import lt.jasiunasarnoldas.mbrainz.data.PlacesRepository
import lt.jasiunasarnoldas.mbrainz.data.remote.PlacesRemoteDataSource
import lt.jasiunasarnoldas.mbrainz.data.remote.PlacesService
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class RemotePlacesDataSource

    @Singleton
    @Provides
    fun providePlacesRepository(@RemotePlacesDataSource placesDataSource: PlacesDataSource): PlacesRepository {
        return PlacesRepository(placesDataSource)
    }

    @Singleton
    @RemotePlacesDataSource
    @Provides
    fun providePlacesRemoteDataSource(placesService: PlacesService): PlacesDataSource {
        return PlacesRemoteDataSource(placesService)
    }
}