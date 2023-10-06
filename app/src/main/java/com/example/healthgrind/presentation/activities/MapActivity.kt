package com.example.healthgrind.presentation.activities

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.healthgrind.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class MapActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mapFragment: SupportMapFragment
    private var map: GoogleMap? = null

    override fun onCreate(savedState: Bundle?) {
        super.onCreate(savedState)

        // HIERMIT XML LAYOUT FÜR EINE ACTIVITY LADEN
        // Hält SupportMapFragment und DismissOverlay.
        setContentView(R.layout.activity_map)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        // Obtain the MapFragment and set the async listener to be notified when the map is ready.
        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.map = googleMap

        val haw = LatLng(53.55709154941227, 10.023530906938696);

        googleMap.addMarker(
            MarkerOptions().position(haw)
                .title("HAW Hamburg")
        )

        // Move the camera to show the marker.
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(haw, 10f))

    }
}
