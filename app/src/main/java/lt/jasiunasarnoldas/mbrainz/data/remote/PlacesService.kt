package lt.jasiunasarnoldas.mbrainz.data.remote

import lt.jasiunasarnoldas.mbrainz.data.remote.dto.ResponsePlaces
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


private const val PLACES_PATH = "place"
const val PLACES_SEARCH_LIMIT = 100

private const val QUERY_FORMAT_KEY = "fmt"
private const val QUERY_FORMAT_VALUE = "json"
private const val QUERY_LIMIT_KEY = "limit"
private const val QUERY_LIMIT_VALUE = PLACES_SEARCH_LIMIT
private const val QUERY_SEARCH = "query"
private const val QUERY_OFFSET = "offset"

interface PlacesService {

    @GET(PLACES_PATH)
    suspend fun search(
        @Query(QUERY_SEARCH) query: String,
        @Query(QUERY_OFFSET) offset: Int = 0,
        @Query(QUERY_FORMAT_KEY) format: String = QUERY_FORMAT_VALUE,
        @Query(QUERY_LIMIT_KEY) limit: Int = QUERY_LIMIT_VALUE
    ): Response<ResponsePlaces>
}