package lt.jasiunasarnoldas.mbrainz.data.remote.dto

import com.google.gson.annotations.SerializedName

data class DTOLifeSpan(
    @SerializedName("begin")
    val begin: String?
)