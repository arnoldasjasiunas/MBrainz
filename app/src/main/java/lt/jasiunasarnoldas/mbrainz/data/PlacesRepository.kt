package lt.jasiunasarnoldas.mbrainz.data

import kotlinx.coroutines.delay
import lt.jasiunasarnoldas.mbrainz.data.Place
import lt.jasiunasarnoldas.mbrainz.data.PlacesDataSource
import kotlin.random.Random

class PlacesRepository constructor(
    private val placesDataSource: PlacesDataSource
) {

    suspend fun getPlaces(query: String): List<Place> {
        return placesDataSource.search(query)
    }

}