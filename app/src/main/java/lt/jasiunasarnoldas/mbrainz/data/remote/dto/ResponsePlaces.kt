package lt.jasiunasarnoldas.mbrainz.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ResponsePlaces(
    @SerializedName("count")
    val count: Int,
    @SerializedName("offset")
    val offset: Int,
    @SerializedName("places")
    val places: List<DTOPlace>
)