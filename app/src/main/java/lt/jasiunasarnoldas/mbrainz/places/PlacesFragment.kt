package lt.jasiunasarnoldas.mbrainz.places

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import lt.jasiunasarnoldas.mbrainz.R
import lt.jasiunasarnoldas.mbrainz.databinding.FragmentPlacesBinding

@AndroidEntryPoint
class PlacesFragment : Fragment(R.layout.fragment_places), OnMapReadyCallback {
    private var _binding: FragmentPlacesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PlacesViewModel by viewModels()

    private var _googleMap: GoogleMap? = null
    private val googleMap get() = _googleMap!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentPlacesBinding.bind(view)

        val mapFragment = childFragmentManager
            .findFragmentById(R.id.places_map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.searchPlaces.setOnQueryTextSubmitListener { viewModel.searchPlaces(it) }

        collectUiState()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _googleMap = null
    }

    private fun collectUiState() = launchOn(Lifecycle.State.STARTED) {
        viewModel.uiState.collect { state ->
            binding.searchLoading.isVisible = state.loading
            binding.searchPlaces.setQuery(state.query, false)
            state.searchState?.let(::handleSearch)
            state.places?.let(::displayPlaces)
        }
    }

    private fun handleSearch(searchUiState: PlacesViewModel.SearchUiState) {
        if (searchUiState.show) showSearch()
        if (searchUiState.hide) hideSearch()
        searchUiState.onAnimated()
    }

    private fun showSearch() {
        val animation = SpringAnimation(binding.searchContainer, SpringAnimation.TRANSLATION_Y)

        val spring = SpringForce()
        spring.stiffness = SpringForce.STIFFNESS_MEDIUM
        spring.dampingRatio = SpringForce.DAMPING_RATIO_HIGH_BOUNCY

        animation.spring = spring

        animation.animateToFinalPosition(0f)
    }

    private fun hideSearch() {
        ObjectAnimator.ofFloat(binding.searchContainer, "translationY", -200f).apply {
            duration = 200
            start()
        }
        binding.searchContainer.clearFocus()
    }

    private fun displayPlaces(places: List<PlacesViewModel.UiPlace>) {
        places.forEach { place ->
            viewLifecycleOwner.lifecycleScope.launch {
                val marker = googleMap.addMarker(place.toMarkerOptions())
                delay(place.lifespan * 1000L)
                marker?.remove()
            }
        }
        viewModel.placesConsumed()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        _googleMap = googleMap
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(-34.0, 151.0)))
    }
}

private fun PlacesViewModel.UiPlace.toMarkerOptions() =
    MarkerOptions().position(LatLng(latitude, longitude))

private fun SearchView.setOnQueryTextSubmitListener(block: (String) -> Unit) {
    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String): Boolean {
            block(query)
            return true
        }

        override fun onQueryTextChange(newText: String?) = true
    })
}

private fun Fragment.launchOn(
    state: Lifecycle.State,
    block: suspend CoroutineScope.() -> Unit
) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(state) { block() }
    }
}
