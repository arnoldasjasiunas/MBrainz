package lt.jasiunasarnoldas.mbrainz.data.remote

import android.util.Log
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import lt.jasiunasarnoldas.mbrainz.data.Place
import lt.jasiunasarnoldas.mbrainz.data.PlacesDataSource
import lt.jasiunasarnoldas.mbrainz.data.remote.dto.DTOPlace
import lt.jasiunasarnoldas.mbrainz.data.remote.dto.ResponsePlaces
import retrofit2.HttpException

class PlacesRemoteDataSource(private val placesService: PlacesService) : PlacesDataSource {

    override suspend fun search(query: String): List<Place> {
        val response = placesService.search(query = query)
        val responsePlaces = response.body()
        return if (response.isSuccessful && responsePlaces != null) {
            var dtos = responsePlaces.places

            if (responsePlaces.places.size == PLACES_SEARCH_LIMIT) {
                dtos += continueSearch(responsePlaces, query)
            }

            dtos.mapNotNull(DTOPlace::toDomain)
        } else {
            Log.e("PlacesRemoteDataSource", "search: \n" + response.message())
            emptyList()
        }
    }

    private suspend fun continueSearch(
        response: ResponsePlaces,
        query: String
    ): List<DTOPlace> = coroutineScope {
        val count = response.count
        val times = count / PLACES_SEARCH_LIMIT
        val deffereds = (1..times)
            .map { it * PLACES_SEARCH_LIMIT }
            .map { offset ->
                async {
                    placesService.search(query, offset)
                }
            }
        deffereds.awaitAll().mapNotNull { it.body()?.places }.flatten()
    }
}

fun DTOPlace.toDomain(): Place? = if (coordinates != null && lifeSpan?.begin != null) {
    Place(
        lifeSpan.begin.take(4).toInt(),
        coordinates.latitude.toDouble(),
        coordinates.longitude.toDouble()
    )
} else null


