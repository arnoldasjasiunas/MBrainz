package lt.jasiunasarnoldas.mbrainz.places

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import lt.jasiunasarnoldas.mbrainz.data.Place
import lt.jasiunasarnoldas.mbrainz.data.PlacesRepository
import javax.inject.Inject

@HiltViewModel
class PlacesViewModel @Inject constructor(
    private val placesRepository: PlacesRepository
) : ViewModel() {
    data class UiState(
        val loading: Boolean = false,
        val searchState: SearchUiState? = null,
        val query: String = "",
        val places: List<UiPlace>? = null
    )

    inner class SearchUiState(
        val show: Boolean = false,
        val hide: Boolean = false,
        val onAnimated: () -> Unit = ::searchAnimated
    )

    data class UiPlace(
        val name: String = "",
        val latitude: Double,
        val longitude: Double,
        val lifespan: Int
    )

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun searchPlaces(query: String) {
        _uiState.update { it.copy(loading = true, searchState = SearchUiState(hide = true), query = query)  }
        viewModelScope.launch {
            val places = withContext(Dispatchers.IO) {
                placesRepository.getPlaces(query).map(Place::toUi)
            }
            _uiState.update { it.copy(loading = false, places = places) }
        }
    }

    fun placesConsumed() {
        _uiState.update { it.copy(searchState = SearchUiState(show = true), places = null) }
    }

    private fun searchAnimated() {
        _uiState.update { it.copy(searchState = null) }
    }
}

private fun Place.toUi() = PlacesViewModel.UiPlace(
    latitude = latitude,
    longitude = longitude,
    lifespan = year - 1990
)