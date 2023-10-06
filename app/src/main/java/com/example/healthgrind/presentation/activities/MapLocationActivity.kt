package com.example.healthgrind.presentation.activities

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.healthgrind.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient

/**
 * An activity that displays a map showing the place at the device's current location.
 */
class MapsLocationActivity : AppCompatActivity(), OnMapReadyCallback {
    private var map: GoogleMap? = null
    private var cameraPosition: CameraPosition? = null
    private val defaultLocation = LatLng(53.55709154941227, 10.023530906938696)
    private var locationPermissionGranted = true

    private lateinit var placesClient: PlacesClient
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var lastKnownLocation: Location? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        setContentView(R.layout.activity_map)

        // Construct a PlacesClient and FusedLocationProviderClient.
        Places.initialize(applicationContext, "AIzaSyB8mg93KKnH1Oz-z9M11OCnzY1FnJ0OSvk")
        placesClient = Places.createClient(this)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        // Build the map.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)


    }

    override fun onSaveInstanceState(outState: Bundle) {
        map?.let { map ->
            outState.putParcelable(KEY_CAMERA_POSITION, map.cameraPosition)
            outState.putParcelable(KEY_LOCATION, lastKnownLocation)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onMapReady(map: GoogleMap) {
        this.map = map
        getLocationPermission()
        updateLocationUI()
        getDeviceLocation()

        outdoorChallengeExample()
    }

    private fun outdoorChallengeExample() {
        // EXAMPLE ROUTE WITH MARKERS AND POLYLINE
        // TODO Location kriegen und von dort aus Start
        val start: LatLng = defaultLocation
        val goal = LatLng(53.55854487302414, 10.01502991437278)

        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(goal, 6f))

        val markerOptions = MarkerOptions()
            .title("START")
            .position(start)
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.start))

        val markerOptionsDestination = MarkerOptions()
            .title("GOAL")
            .position(goal)
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.goal))

        val polyline = PolylineOptions().add(start, goal).color(0xff504c83.toInt())

        map?.addMarker(markerOptions)
        map?.addMarker(markerOptionsDestination)
        map?.addPolyline(polyline)
    }

    // ---- PERMISSION STUFF ----
    @SuppressLint("MissingPermission")
    private fun getDeviceLocation() {
        try {
            if (locationPermissionGranted) {
                val locationResult = fusedLocationProviderClient.lastLocation
                locationResult.addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        lastKnownLocation = task.result
                        if (lastKnownLocation != null) {
                            map?.moveCamera(
                                CameraUpdateFactory.newLatLngZoom(
                                    LatLng(
                                        lastKnownLocation!!.latitude,
                                        lastKnownLocation!!.longitude
                                    ), DEFAULT_ZOOM.toFloat()
                                )
                            )
                        }
                    } else {
                        Log.d(TAG, "Current location is null. Using defaults.")
                        Log.e(TAG, "Exception: %s", task.exception)
                        map?.moveCamera(
                            CameraUpdateFactory
                                .newLatLngZoom(defaultLocation, DEFAULT_ZOOM.toFloat())
                        )
                        map?.uiSettings?.isMyLocationButtonEnabled = false
                    }
                }
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }

    private fun getLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            locationPermissionGranted = true
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        locationPermissionGranted = true
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    locationPermissionGranted = true
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
        updateLocationUI()
    }

    @SuppressLint("MissingPermission")
    private fun updateLocationUI() {
        if (map == null) {
            return
        }
        try {
            if (locationPermissionGranted) {
                map?.isMyLocationEnabled = true
                map?.uiSettings?.isMyLocationButtonEnabled = true
            } else {
                map?.isMyLocationEnabled = false
                map?.uiSettings?.isMyLocationButtonEnabled = false
                //lastKnownLocation = null
                getLocationPermission()
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }

    companion object {
        private val TAG = MapsLocationActivity::class.java.simpleName
        private const val DEFAULT_ZOOM = 15
        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1
        private const val KEY_CAMERA_POSITION = "camera_position"
        private const val KEY_LOCATION = "location"
    }
}