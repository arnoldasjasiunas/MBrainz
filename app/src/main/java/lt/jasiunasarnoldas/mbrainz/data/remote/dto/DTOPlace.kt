package lt.jasiunasarnoldas.mbrainz.data.remote.dto

import com.google.gson.annotations.SerializedName

data class DTOPlace(
    @SerializedName("address")
    val address: String?,
    @SerializedName("coordinates")
    val coordinates: DTOCoordinates?,
    @SerializedName("life-span")
    val lifeSpan: DTOLifeSpan?,
    @SerializedName("name")
    val name: String,
)