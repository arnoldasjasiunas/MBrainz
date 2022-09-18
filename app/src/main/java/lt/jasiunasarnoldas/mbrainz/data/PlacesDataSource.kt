package lt.jasiunasarnoldas.mbrainz.data

interface PlacesDataSource {
    suspend fun search(query: String): List<Place>
}