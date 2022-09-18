package lt.jasiunasarnoldas.mbrainz

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import dagger.hilt.android.AndroidEntryPoint
import lt.jasiunasarnoldas.mbrainz.databinding.ActivityMainBinding
import lt.jasiunasarnoldas.mbrainz.places.PlacesFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                add<PlacesFragment>(R.id.fragment_container_view)
            }
        }
    }
}