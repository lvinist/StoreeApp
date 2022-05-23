package com.dicoding.storeeapp.ui.maps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import com.dicoding.storeeapp.R
import com.dicoding.storeeapp.databinding.ActivityMapsBinding

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private val mapsViewModel: MapsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.maps_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.normal_type -> {
                mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
                true
            }
            R.id.hybrid_type -> {
                mMap.mapType = GoogleMap.MAP_TYPE_HYBRID
                true
            }
            R.id.terrain_type -> {
                mMap.mapType = GoogleMap.MAP_TYPE_TERRAIN
                true
            }
            R.id.satellite_type -> {
                mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true

        mapsViewModel.stories.observe(this) {stories ->
            val storiesLatLng = stories?.map { LatLng((it.lat ?: 0) as Double, (it.lon ?: 0) as Double) }
            if (storiesLatLng!!.isNotEmpty()) {
                storiesLatLng.forEachIndexed { index, latlng ->
                    mMap.addMarker(MarkerOptions().position(latlng).title(stories[index].name))
                }
                mMap.moveCamera(CameraUpdateFactory.newLatLng(storiesLatLng[1]))
            }
        }
    }
}